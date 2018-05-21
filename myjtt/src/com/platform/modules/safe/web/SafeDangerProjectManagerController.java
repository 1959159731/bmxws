package com.platform.modules.safe.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.platform.common.beanvalidator.BeanValidators;
import com.platform.common.config.Constant;
import com.platform.common.config.Global;
import com.platform.common.persistence.Page;
import com.platform.common.web.BaseController;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.FileUtils;
import com.platform.common.utils.StringUtils;
import com.platform.common.utils.excel.ExportExcel;
import com.platform.common.utils.excel.ImportExcel;
import com.platform.modules.project.entity.ProSimpleInfo;
import com.platform.modules.project.service.ProSimpleInfoService;
import com.platform.modules.safe.entity.SafeDangerProjectManager;
import com.platform.modules.safe.service.SafeDangerProjectManagerService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.sys.utils.WordUtils;

/**
 * 危险性较大工程管理Controller
 * @author Mr.Jia
 * @version 2017-11-27
 */
@Controller
@RequestMapping(value = "${adminPath}/safe/safeDangerProjectManager")
public class SafeDangerProjectManagerController extends BaseController {

	@Autowired
	private SafeDangerProjectManagerService safeDangerProjectManagerService;
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public SafeDangerProjectManager get(@RequestParam(required=false) String id) {
		SafeDangerProjectManager entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = safeDangerProjectManagerService.get(id);
		}
		if (entity == null){
			entity = new SafeDangerProjectManager();
		}
		return entity;
	}
	
	@RequiresPermissions("safe:safeDangerProjectManager:view")
	@RequestMapping(value = {"list", ""})
	public String list(SafeDangerProjectManager safeDangerProjectManager, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SafeDangerProjectManager> page = safeDangerProjectManagerService.findPage(new Page<SafeDangerProjectManager>(request, response), safeDangerProjectManager);
		
		// 获取项目名称list集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		
		// 获取合同段编号集合（list页面查找）
		List<String> contractLabels = new ArrayList<String>();
		if(safeDangerProjectManager.getProSimpleInfo() != null 
				&& safeDangerProjectManager.getProSimpleInfo().getId() != null 
				&& !"".equals(safeDangerProjectManager.getProSimpleInfo().getId()) ){
			contractLabels = safeDangerProjectManagerService.findContractLabel(safeDangerProjectManager.getProSimpleInfo().getId());
		}
		model.addAttribute("contractlabels", contractLabels);
		model.addAttribute("projectNameList", list);
				
		model.addAttribute("page", page);
		return "modules/safe/safeDangerProjectManagerList";
	}

	@RequiresPermissions("safe:safeDangerProjectManager:view")
	@RequestMapping(value = "form")
	public String form(SafeDangerProjectManager safeDangerProjectManager, Model model) {
		if(safeDangerProjectManager.getId() != null && !"".equals(safeDangerProjectManager.getId())){
			String constractSectionType = safeDangerProjectManager.getContractSectionType();
			if(Constant.getContractSectionType(constractSectionType).length() > 0){
				safeDangerProjectManager.setContractSectionLabel(
						safeDangerProjectManager.getContractSectionLabel().substring(3).toString());
			}
		}
		
		// 获取项目名称集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
				
		model.addAttribute("safeDangerProjectManager", safeDangerProjectManager);
		return "modules/safe/safeDangerProjectManagerForm";
	}

	@RequiresPermissions("safe:safeDangerProjectManager:edit")
	@RequestMapping(value = "save")
	public String save(SafeDangerProjectManager safeDangerProjectManager, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, safeDangerProjectManager)){
			return form(safeDangerProjectManager, model);
		}
		String contractSectionType = Constant.getContractSectionType(
				safeDangerProjectManager.getContractSectionType());
		safeDangerProjectManager.setContractSectionLabel(contractSectionType + safeDangerProjectManager.getContractSectionLabel());
		safeDangerProjectManagerService.save(safeDangerProjectManager);
		addMessage(redirectAttributes, "保存危险性较大工程管理成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeDangerProjectManager/?repage";
	}
	
	@RequiresPermissions("safe:safeDangerProjectManager:edit")
	@RequestMapping(value = "delete")
	public String delete(SafeDangerProjectManager safeDangerProjectManager, RedirectAttributes redirectAttributes) {
		safeDangerProjectManagerService.delete(safeDangerProjectManager);
		addMessage(redirectAttributes, "删除危险性较大工程管理成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeDangerProjectManager/?repage";
	}

	
	/**
	 * 根据项目名称获取合同编号
	 * @param projectName
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value="getContractLabel", method = RequestMethod.POST)
	public List<String> getContractLabel(HttpServletRequest request, String projectId) {
		return safeDangerProjectManagerService.findContractLabel(projectId);
	}
	
	
	@RequestMapping(value="export")
	public String export(SafeDangerProjectManager safeDangerProjectManager, 
			HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes redirectAttributes){
		try {
			if(StringUtils.isBlank(safeDangerProjectManager.getProSimpleInfo().getId()) 
					|| StringUtils.isBlank(safeDangerProjectManager.getContractSectionLabel()) ){
				addMessage(redirectAttributes, "参数不全");
    			return "redirect:"+Global.getAdminPath()+"/safe/safeDangerProjectManager/?repage";
			}
			
			Page<SafeDangerProjectManager> page = safeDangerProjectManagerService.findPage(
            		new Page<SafeDangerProjectManager>(request, response), safeDangerProjectManager);
            List<SafeDangerProjectManager> data = page.getList();
            List<String[]> dataList = new ArrayList<String[]>();
            if(data.size() > 0){
            	for (int i = 0; i < data.size(); i++) {
            		safeDangerProjectManager = data.get(i);
                	String[] strArr = new String[6];
                	strArr[0] = String.valueOf(i+1);
                	strArr[1] = safeDangerProjectManager.getDangerousProjectNameAndSite();
                	strArr[2] = safeDangerProjectManager.getAccidentType();
                	strArr[3] = safeDangerProjectManager.getSpecialProgramsName();
                	strArr[4] = safeDangerProjectManager.getSecurityOfficerName();
                	strArr[5] = DateUtils.formatDate(safeDangerProjectManager.getSpecialProgramsApprovalTime(), "yyyy-MM-dd");
                	dataList.add(strArr);
    			}
            } else {
            	addMessage(redirectAttributes, "没有数据");
    			return "redirect:" + adminPath + "/safe/safeDangerProjectManager/list?repage";
            }
            
			String fileName = "危险性较大工程管理"+DateUtils.getDate("yyyyMMddHHmmss")+".doc";
			String title = "危险性较大工程管理";
            String[] fuTitle = { safeDangerProjectManager.getProSimpleInfo().getProjectSimpleName(),
            		safeDangerProjectManager.getContractSectionLabel(),
            		data.get(0).getConstructionName() };
            int[] widths = {8, 25, 22, 25, 10, 10};
            List<String> head = new ArrayList<String>();
            head.add("序号");
            head.add("危险性较大的工程名称及部位");
            head.add("容易引发的事故类型");
            head.add("对应的专项施工方案名称");
            head.add("对应的专职安全员");
            head.add("专项施工方案监理审批日期");
            
            new WordUtils(WordUtils.RECTANGLE_2).exportSafeDangerProjectManager(title, fuTitle, widths, head, dataList, response).write(fileName);
			addMessage(redirectAttributes, "下载成功");
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/safe/safeDangerProjectManager/list?repage";
	}
	
	/**
	 * download file
	 * @param request
	 * @param response
	 * @param dangeProId
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="download")
	public String downLoad(HttpServletRequest request, HttpServletResponse response, 
			String dangeProId, RedirectAttributes redirectAttributes) {
		try {
			SafeDangerProjectManager entity = null;
			if (StringUtils.isNotBlank(dangeProId)){
				entity = safeDangerProjectManagerService.get(dangeProId);
			}
			String filePath = Global.getConfig("userfiles.basedir") + entity.getFilePath().replace("/platform", "");
			
			
			String filename = URLDecoder.decode(filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length()), "UTF-8");
			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1) + filename;
			File file = new File(filePath);
			return FileUtils.downFile(file, request, response, filename);
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/safe/safeDangerProjectManager/list?repage";
	}
	
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeDangerProjectManager:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SafeDangerProjectManager> list = ei.getDataList(SafeDangerProjectManager.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			SafeDangerProjectManager safeDangerProjectManager = new SafeDangerProjectManager();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						safeDangerProjectManager = list.get(i);
						if (safeDangerProjectManager.getProjectName() != null && !safeDangerProjectManager.getProjectName().equals("")){
							ProSimpleInfo projectInfo = new ProSimpleInfo();
							projectInfo.setProjectSimpleName(safeDangerProjectManager.getProjectName());
							List<ProSimpleInfo> proList = proSimpleInfoService.findAllProjectNames(projectInfo);
							if(proList != null && proList.size() > 0){
								safeDangerProjectManager.setProSimpleInfo(proList.get(0));
								safeDangerProjectManager.setCreateBy(user);
								safeDangerProjectManager.setUpdateBy(user);
								safeDangerProjectManager.setCreateDate(now);
								safeDangerProjectManager.setUpdateDate(now);
								safeDangerProjectManager.setContractSectionType(Constant.getContractSectionType(
										safeDangerProjectManager.getContractSectionLabel().substring(0, 3)));
								safeDangerProjectManagerService.save(safeDangerProjectManager);
								successNum++;
							} else {
								failureMsg.append("<br/>项目" + safeDangerProjectManager.getProjectName() + " 不存在;");
							}
						}
						
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>项目 " + safeDangerProjectManager.getProjectName() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>项目 " + safeDangerProjectManager.getProjectName() + " 导入失败："+ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条危险性较大工程管理信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条危险性较大工程管理信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeDangerProjectManager/?repage";
    }
	
	
	
	/**
	 * 下载导入建设单位用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeDangerProjectManager:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "危险性较大工程管理导入模板.xlsx";
    		List<SafeDangerProjectManager> list = Lists.newArrayList(); 
    		list.add(new SafeDangerProjectManager());
    		new ExportExcel("危险性较大工程管理", SafeDangerProjectManager.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeDangerProjectManager/?repage";
    }
	
}