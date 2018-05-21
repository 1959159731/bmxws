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
import com.platform.modules.safe.entity.SafeEmgcResPlanManager;
import com.platform.modules.safe.service.SafeEmgcResPlanManagerService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.DictUtils;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.sys.utils.WordUtils;

/**
 * 应急救援预案管理Controller
 * @author Mr.Jia
 * @version 2017-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/safe/safeEmgcResPlanManager")
public class SafeEmgcResPlanManagerController extends BaseController {

	@Autowired
	private SafeEmgcResPlanManagerService safeEmgcResPlanManagerService;
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public SafeEmgcResPlanManager get(@RequestParam(required=false) String id) {
		SafeEmgcResPlanManager entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = safeEmgcResPlanManagerService.get(id);
		}
		if (entity == null){
			entity = new SafeEmgcResPlanManager();
		}
		return entity;
	}
	
	@RequiresPermissions("safe:safeEmgcResPlanManager:view")
	@RequestMapping(value = {"list", ""})
	public String list(SafeEmgcResPlanManager safeEmgcResPlanManager, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SafeEmgcResPlanManager> page = safeEmgcResPlanManagerService.findPage(new Page<SafeEmgcResPlanManager>(request, response), safeEmgcResPlanManager); 
		
		// 获取项目名称list集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		
		// 获取合同段编号集合（list页面查找）
		List<String> contractLabels = new ArrayList<String>();
		if(safeEmgcResPlanManager.getProSimpleInfo() != null 
				&& safeEmgcResPlanManager.getProSimpleInfo().getId() != null 
				&& !"".equals(safeEmgcResPlanManager.getProSimpleInfo().getId()) ){
			contractLabels = safeEmgcResPlanManagerService.findContractLabel(safeEmgcResPlanManager.getProSimpleInfo().getId());
		}
		model.addAttribute("contractlabels", contractLabels);
		model.addAttribute("projectNameList", list);

		model.addAttribute("page", page);
		return "modules/safe/safeEmgcResPlanManagerList";
	}

	@RequiresPermissions("safe:safeEmgcResPlanManager:view")
	@RequestMapping(value = "form")
	public String form(SafeEmgcResPlanManager safeEmgcResPlanManager, Model model) {
		if(safeEmgcResPlanManager.getId() != null && !"".equals(safeEmgcResPlanManager.getId())){
			String constractSectionType = safeEmgcResPlanManager.getContractSectionType();
			if(Constant.getContractSectionType(constractSectionType).length() > 0){
				safeEmgcResPlanManager.setContractSectionLabel(
						safeEmgcResPlanManager.getContractSectionLabel().substring(3).toString());
			}
		}
		
		// 获取项目名称集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
				
				
		model.addAttribute("safeEmgcResPlanManager", safeEmgcResPlanManager);
		return "modules/safe/safeEmgcResPlanManagerForm";
	}

	@RequiresPermissions("safe:safeEmgcResPlanManager:edit")
	@RequestMapping(value = "save")
	public String save(SafeEmgcResPlanManager safeEmgcResPlanManager, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, safeEmgcResPlanManager)){
			return form(safeEmgcResPlanManager, model);
		}
		String contractSectionType = Constant.getContractSectionType(
				safeEmgcResPlanManager.getContractSectionType());
		safeEmgcResPlanManager.setContractSectionLabel(contractSectionType + safeEmgcResPlanManager.getContractSectionLabel());
		
		safeEmgcResPlanManagerService.save(safeEmgcResPlanManager);
		addMessage(redirectAttributes, "保存应急救援预案管理成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeEmgcResPlanManager/?repage";
	}
	
	@RequiresPermissions("safe:safeEmgcResPlanManager:edit")
	@RequestMapping(value = "delete")
	public String delete(SafeEmgcResPlanManager safeEmgcResPlanManager, RedirectAttributes redirectAttributes) {
		safeEmgcResPlanManagerService.delete(safeEmgcResPlanManager);
		addMessage(redirectAttributes, "删除应急救援预案管理成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeEmgcResPlanManager/?repage";
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
		return safeEmgcResPlanManagerService.findContractLabel(projectId);
	}
	
	
	@RequestMapping(value="export")
	public String export(SafeEmgcResPlanManager safeEmgcResPlanManager, 
			HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes redirectAttributes){
		try {
			if(StringUtils.isBlank(safeEmgcResPlanManager.getProSimpleInfo().getId()) 
					|| StringUtils.isBlank(safeEmgcResPlanManager.getContractSectionLabel()) ){
				addMessage(redirectAttributes, "参数不全");
    			return "redirect:"+Global.getAdminPath()+"/safe/safeEmgcResPlanManager/?repage";
			}
			
			Page<SafeEmgcResPlanManager> page = safeEmgcResPlanManagerService.findPage(new Page<SafeEmgcResPlanManager>(request, response), safeEmgcResPlanManager);
            List<SafeEmgcResPlanManager> data = page.getList();
            List<String[]> dataList = new ArrayList<String[]>();
            if(data.size() > 0){
            	for (int i = 0; i < data.size(); i++) {
                	safeEmgcResPlanManager = data.get(i);
                	String[] strArr = new String[8];
                	strArr[0] = String.valueOf(i+1);
                	strArr[1] = DictUtils.getDictLabel(safeEmgcResPlanManager.getPlanType(), "safe_emgc_res_plan", "");
                	strArr[2] = safeEmgcResPlanManager.getEmergencyRescueName();
                	strArr[3] = safeEmgcResPlanManager.getSignSituation();
                	strArr[4] = DateUtils.formatDate(safeEmgcResPlanManager.getImplementationDate(), "yyyy-MM-dd");
                	strArr[5] = Constant.getYesNo(safeEmgcResPlanManager.getReportDepartmentRecord());
                	strArr[6] = safeEmgcResPlanManager.getEmergencyDrillTime() == null ? Constant.NONE
                			:DateUtils.formatDate(safeEmgcResPlanManager.getEmergencyDrillTime(), "yyyy-MM-dd");
                	strArr[7] = safeEmgcResPlanManager.getRemarks();
                	dataList.add(strArr);
    			}
            } else {
            	addMessage(redirectAttributes, "没有数据");
    			return "redirect:" + adminPath + "/safe/safeEmgcResPlanManager/list?repage";
            }
            
            
			String fileName = "应急救援预案管理"+DateUtils.getDate("yyyyMMddHHmmss")+".doc";
			String title = "应急救援预案管理";
            String[] fuTitle = { safeEmgcResPlanManager.getProSimpleInfo().getProjectSimpleName(),
                                 safeEmgcResPlanManager.getContractSectionLabel(),
                                 data.get(0).getConstructionName() };
            int[] widths = {10, 10, 30, 10, 10, 10, 10, 10};
            List<String> head = new ArrayList<String>();
            head.add("序号");
            head.add("预案类型");
            head.add("应急救援预案名称");
            head.add("单位负责人签批情况");
            head.add("实施日期");
            head.add("是否上报相关部门备案");
            head.add("应急演练时间");
            head.add("备注");
            
            new WordUtils(WordUtils.RECTANGLE_2).exportSafeEmgcResPlanManager(title, fuTitle, widths, head, dataList, response).write(fileName);
			addMessage(redirectAttributes, "下载成功");
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/safe/safeEmgcResPlanManager/list?repage";
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
			SafeEmgcResPlanManager entity = null;
			if (StringUtils.isNotBlank(dangeProId)){
				entity = safeEmgcResPlanManagerService.get(dangeProId);
			}
			String filePath = Global.getConfig("userfiles.basedir") + entity.getFilePath().replace("/platform", "");
			String filename = URLDecoder.decode(filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length()), "UTF-8");
			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1) + filename;
			File file = new File(filePath);
			return FileUtils.downFile(file, request, response, filename);
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/safe/safeEmgcResPlanManager/list?repage";
	}
	
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeEmgcResPlanManager:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SafeEmgcResPlanManager> list = ei.getDataList(SafeEmgcResPlanManager.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			SafeEmgcResPlanManager safeEmgcResPlanManager = new SafeEmgcResPlanManager();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						safeEmgcResPlanManager = list.get(i);
						if (safeEmgcResPlanManager.getProjectName() != null && !safeEmgcResPlanManager.getProjectName().equals("")){
							ProSimpleInfo projectInfo = new ProSimpleInfo();
							projectInfo.setProjectSimpleName(safeEmgcResPlanManager.getProjectName());
							List<ProSimpleInfo> proList = proSimpleInfoService.findAllProjectNames(projectInfo);
							if(proList != null && proList.size() > 0){
								safeEmgcResPlanManager.setProSimpleInfo(proList.get(0));
								safeEmgcResPlanManager.setCreateBy(user);
								safeEmgcResPlanManager.setUpdateBy(user);
								safeEmgcResPlanManager.setCreateDate(now);
								safeEmgcResPlanManager.setUpdateDate(now);
								safeEmgcResPlanManager.setContractSectionType(Constant.getContractSectionType(
										safeEmgcResPlanManager.getContractSectionLabel().substring(0, 3)));
								safeEmgcResPlanManager.setPlanType(DictUtils.getDictValue(safeEmgcResPlanManager.getPlanType(), 
										"safe_emgc_res_plan", "1"));
								safeEmgcResPlanManager.setReportDepartmentRecord(DictUtils.getDictValue(safeEmgcResPlanManager.getReportDepartmentRecord(), 
										"yes_no", "1"));
								safeEmgcResPlanManagerService.save(safeEmgcResPlanManager);
								successNum++;
							} else {
								failureMsg.append("<br/>项目" + safeEmgcResPlanManager.getProjectName() + " 不存在;");
							}
						}
						
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>项目 " + safeEmgcResPlanManager.getProjectName() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>项目 " + safeEmgcResPlanManager.getProjectName() + " 导入失败："+ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条应急救援预案信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条应急救援预案信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeEmgcResPlanManager/?repage";
    }
	
	
	
	/**
	 * 下载导入应急救援预案信息模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeEmgcResPlanManager:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "应急救援预案信息导入模板.xlsx";
    		List<SafeEmgcResPlanManager> list = Lists.newArrayList(); 
    		list.add(new SafeEmgcResPlanManager());
    		new ExportExcel("应急救援预案信息", SafeEmgcResPlanManager.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeEmgcResPlanManager/?repage";
    }
	
	
}