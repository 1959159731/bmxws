package com.platform.modules.safe.web;

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
import com.platform.common.utils.StringUtils;
import com.platform.common.utils.excel.ExportExcel;
import com.platform.common.utils.excel.ImportExcel;
import com.platform.modules.project.entity.ProSimpleInfo;
import com.platform.modules.project.service.ProSimpleInfoService;
import com.platform.modules.safe.entity.SafeSpecEquOperPerson;
import com.platform.modules.safe.service.SafeSpecEquOperPersonService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.DictUtils;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.sys.utils.WordUtils;

/**
 * 特种设备操作人员Controller
 * @author Me.Jia
 * @version 2017-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/safe/safeSpecEquOperPerson")
public class SafeSpecEquOperPersonController extends BaseController {

	@Autowired
	private SafeSpecEquOperPersonService safeSpecEquOperPersonService;
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public SafeSpecEquOperPerson get(@RequestParam(required=false) String id) {
		SafeSpecEquOperPerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = safeSpecEquOperPersonService.get(id);
		}
		if (entity == null){
			entity = new SafeSpecEquOperPerson();
		}
		return entity;
	}
	
	@RequiresPermissions("safe:safeSpecEquOperPerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(SafeSpecEquOperPerson safeSpecEquOperPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SafeSpecEquOperPerson> page = safeSpecEquOperPersonService.findPage(new Page<SafeSpecEquOperPerson>(request, response), safeSpecEquOperPerson);
		
		// 获取项目名称list集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		
		// 获取合同段编号集合（list页面查找）
		List<String> contractLabels = new ArrayList<String>();
		if(safeSpecEquOperPerson.getProSimpleInfo() != null 
				&& safeSpecEquOperPerson.getProSimpleInfo().getId() != null 
				&& !"".equals(safeSpecEquOperPerson.getProSimpleInfo().getId()) ){
			contractLabels = safeSpecEquOperPersonService.findContractLabel(safeSpecEquOperPerson.getProSimpleInfo().getId());
		}
		model.addAttribute("contractlabels", contractLabels);
		model.addAttribute("projectNameList", list);
		model.addAttribute("page", page);
		return "modules/safe/safeSpecEquOperPersonList";
	}

	@RequiresPermissions("safe:safeSpecEquOperPerson:view")
	@RequestMapping(value = "form")
	public String form(SafeSpecEquOperPerson safeSpecEquOperPerson, Model model) {
		if(safeSpecEquOperPerson.getId() != null && !"".equals(safeSpecEquOperPerson.getId())){
			String constractSectionType = safeSpecEquOperPerson.getContractSectionType();
			if(Constant.getContractSectionType(constractSectionType).length() > 0){
				safeSpecEquOperPerson.setContractSectionLabel(
						safeSpecEquOperPerson.getContractSectionLabel().substring(3).toString());
			}
		}
		
		// 获取项目名称集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		
		model.addAttribute("safeSpecEquOperPerson", safeSpecEquOperPerson);
		return "modules/safe/safeSpecEquOperPersonForm";
	}

	@RequiresPermissions("safe:safeSpecEquOperPerson:edit")
	@RequestMapping(value = "save")
	public String save(SafeSpecEquOperPerson safeSpecEquOperPerson, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, safeSpecEquOperPerson)){
			return form(safeSpecEquOperPerson, model);
		}
		String contractSectionType = Constant.getContractSectionType(
				safeSpecEquOperPerson.getContractSectionType());
		safeSpecEquOperPerson.setContractSectionLabel(contractSectionType + safeSpecEquOperPerson.getContractSectionLabel());
		safeSpecEquOperPersonService.save(safeSpecEquOperPerson);
		addMessage(redirectAttributes, "保存特种设备操作人员成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecEquOperPerson/?repage";
	}
	
	@RequiresPermissions("safe:safeSpecEquOperPerson:exit")
	@RequestMapping(value = "exit")
	public String exit(SafeSpecEquOperPerson safeSpecEquOperPerson, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, safeSpecEquOperPerson)){
			return form(safeSpecEquOperPerson, model);
		}
		String contractSectionType = Constant.getContractSectionType(
				safeSpecEquOperPerson.getContractSectionType());
		safeSpecEquOperPerson.setContractSectionLabel(contractSectionType + safeSpecEquOperPerson.getContractSectionLabel());
		safeSpecEquOperPerson.setExitTime(DateUtils.parseDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd")));
		safeSpecEquOperPersonService.save(safeSpecEquOperPerson);
		addMessage(redirectAttributes, "特种设备操作人员退场成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecEquOperPerson/?repage";
	}
	
	@RequiresPermissions("safe:safeSpecEquOperPerson:edit")
	@RequestMapping(value = "delete")
	public String delete(SafeSpecEquOperPerson safeSpecEquOperPerson, RedirectAttributes redirectAttributes) {
		safeSpecEquOperPersonService.delete(safeSpecEquOperPerson);
		addMessage(redirectAttributes, "删除特种设备操作人员成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecEquOperPerson/?repage";
	}
	
	
	/**
	 * 根据项目名称获取合同编号
	 * @param projectName
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value="getContractLabel", method = RequestMethod.POST)
	public List<String> getContractLabel(HttpServletRequest request, String projectId){
		return safeSpecEquOperPersonService.findContractLabel(projectId);
	}
	
	/**
	 * 导出
	 * @param safeEmgcResPlanManager
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="export")
	public String export(SafeSpecEquOperPerson safeSpecEquOperPerson, 
			HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes redirectAttributes){
		try {
			if(StringUtils.isBlank(safeSpecEquOperPerson.getProSimpleInfo().getId()) 
					|| StringUtils.isBlank(safeSpecEquOperPerson.getContractSectionLabel()) ){
				addMessage(redirectAttributes, "参数不全");
    			return "redirect:"+Global.getAdminPath()+"/safe/safeSpecEquOperPerson/?repage";
			}
			
			Page<SafeSpecEquOperPerson> page = safeSpecEquOperPersonService.findPage(
            		new Page<SafeSpecEquOperPerson>(request, response), safeSpecEquOperPerson);
            List<SafeSpecEquOperPerson> data = page.getList();
            List<String[]> dataList = new ArrayList<String[]>();
            List<String> imgPath = new ArrayList<String>();
            if(data.size() > 0){
            	for (int i = 0; i < data.size(); i++) {
            		safeSpecEquOperPerson = data.get(i);
            		imgPath.add(safeSpecEquOperPerson.getCertificatePath());
                	String[] strArr = new String[8];
                	strArr[0] = String.valueOf(i+1);
                	strArr[1] = safeSpecEquOperPerson.getName();
                	strArr[2] = safeSpecEquOperPerson.getGender().equals(Constant.SEX_MAN_NUM) ? Constant.SEX_MAN : Constant.SEX_WOMAN;
                	strArr[3] = safeSpecEquOperPerson.getOperateItems();
                	strArr[4] = safeSpecEquOperPerson.getPracticeCertnumber();
                	strArr[5] = DateUtils.formatDate(safeSpecEquOperPerson.getCertificateValid(), "yyyy-MM-dd");
                	strArr[6] = DateUtils.formatDate(safeSpecEquOperPerson.getApproachTime(), "yyyy-MM-dd");
                	strArr[7] = safeSpecEquOperPerson.getExitTime() == null ? "" : DateUtils.formatDate(safeSpecEquOperPerson.getExitTime(), "yyyy-MM-dd");
                	dataList.add(strArr);
    			}
            } else {
            	addMessage(redirectAttributes, "没有数据");
    			return "redirect:"+Global.getAdminPath()+"/safe/safeSpecEquOperPerson/?repage";
            }
            
			String fileName = "特种设备操作人员"+DateUtils.getDate("yyyyMMddHHmmss")+".doc";
			String title = "特种设备操作人员";
            String[] fuTitle = { safeSpecEquOperPerson.getProSimpleInfo().getProjectSimpleName(),
            		safeSpecEquOperPerson.getContractSectionLabel(),
            		data.get(0).getConstructionName() };
            int[] widths = {4, 8, 8, 20, 15, 13, 16, 16};
            List<String> head = new ArrayList<String>();
            head.add("序号");
            head.add("姓名");
            head.add("姓别");
            head.add("操作项目");
            head.add("执业证件编号");
            head.add("有效期");
            head.add("进场时间");
            head.add("退场时间");
            
            new WordUtils(WordUtils.RECTANGLE_1).exportSafeSpecEquOperPerson(title, fuTitle, widths, head, dataList, imgPath, response).write(fileName);
			addMessage(redirectAttributes, "下载成功");
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecEquOperPerson/?repage";
	}
	
	
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeSpecEquOperPerson:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SafeSpecEquOperPerson> list = ei.getDataList(SafeSpecEquOperPerson.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			SafeSpecEquOperPerson safeSpecEquOperPerson = new SafeSpecEquOperPerson();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						safeSpecEquOperPerson = list.get(i);
						if (safeSpecEquOperPerson.getProjectName() != null && !safeSpecEquOperPerson.getProjectName().equals("")){
							ProSimpleInfo projectInfo = new ProSimpleInfo();
							projectInfo.setProjectSimpleName(safeSpecEquOperPerson.getProjectName());
							List<ProSimpleInfo> proList = proSimpleInfoService.findAllProjectNames(projectInfo);
							if(proList != null && proList.size() > 0){
								safeSpecEquOperPerson.setProSimpleInfo(proList.get(0));
								safeSpecEquOperPerson.setCreateBy(user);
								safeSpecEquOperPerson.setUpdateBy(user);
								safeSpecEquOperPerson.setCreateDate(now);
								safeSpecEquOperPerson.setUpdateDate(now);
								safeSpecEquOperPerson.setContractSectionType(Constant.getContractSectionType(
										safeSpecEquOperPerson.getContractSectionLabel().substring(0, 3)));
								safeSpecEquOperPerson.setGender(DictUtils.getDictValue(safeSpecEquOperPerson.getGender(), 
										"sex", "1"));
								safeSpecEquOperPersonService.save(safeSpecEquOperPerson);
								successNum++;
							} else {
								failureMsg.append("<br/>项目" + safeSpecEquOperPerson.getProjectName() + " 不存在;");
							}
						}
						
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>项目 " + safeSpecEquOperPerson.getProjectName() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>项目 " + safeSpecEquOperPerson.getProjectName() + " 导入失败："+ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条特种设备操作人员信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条特种设备操作人员信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecEquOperPerson/?repage";
    }
	
	
	
	/**
	 * 下载导入建设单位用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeSpecEquOperPerson:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "特种设备操作人员导入模板.xlsx";
    		List<SafeSpecEquOperPerson> list = Lists.newArrayList(); 
    		list.add(new SafeSpecEquOperPerson());
    		new ExportExcel("特种设备操作人员信息", SafeSpecEquOperPerson.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecEquOperPerson/?repage";
    }
	
}