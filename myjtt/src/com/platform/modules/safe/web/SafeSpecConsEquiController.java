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
import com.platform.modules.safe.entity.SafeOpertionUserinfo;
import com.platform.modules.safe.entity.SafeSpecConsEqui;
import com.platform.modules.safe.service.SafeOpertionUserinfoService;
import com.platform.modules.safe.service.SafeSpecConsEquiService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.DictUtils;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.sys.utils.WordUtils;

/**
 * 专用设备Controller
 * @author Mr.Jia
 * @version 2017-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/safe/safeSpecConsEqui")
public class SafeSpecConsEquiController extends BaseController {

	@Autowired
	private SafeSpecConsEquiService safeSpecConsEquiService;
	@Autowired
	private SafeOpertionUserinfoService safeOpertionUserinfoService;
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public SafeSpecConsEqui get(@RequestParam(required=false) String id) {
		SafeSpecConsEqui entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = safeSpecConsEquiService.get(id);
		}
		if (entity == null){
			entity = new SafeSpecConsEqui();
		}
		return entity;
	}
	
	@RequiresPermissions("safe:safeSpecConsEqui:view")
	@RequestMapping(value = {"list", ""})
	public String list(SafeSpecConsEqui safeSpecConsEqui, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SafeSpecConsEqui> page = safeSpecConsEquiService.findPage(new Page<SafeSpecConsEqui>(request, response), safeSpecConsEqui); 
		
		List<SafeSpecConsEqui> specConsEquiList = page.getList();
		if(specConsEquiList != null && specConsEquiList.size() > 0){
			for (int i = 0; i < specConsEquiList.size(); i++) {
				safeSpecConsEqui  = specConsEquiList.get(i);
				SafeOpertionUserinfo operatorUser = safeSpecConsEqui.getOperatorUser();
				safeSpecConsEqui.setOperatorUser(safeOpertionUserinfoService.get(operatorUser));
				if(safeSpecConsEqui.getOperatorUserOther() != null){
					safeSpecConsEqui.setOperatorUserOther(safeOpertionUserinfoService.get(safeSpecConsEqui.getOperatorUserOther()));
				}
			}
		}
		
		// 获取项目名称list集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		
		// 获取合同段编号集合（list页面查找）
		List<String> contractLabels = new ArrayList<String>();
		if(safeSpecConsEqui.getProSimpleInfo() != null 
				&& safeSpecConsEqui.getProSimpleInfo().getId() != null 
				&& !"".equals(safeSpecConsEqui.getProSimpleInfo().getId()) ){
			contractLabels = safeSpecConsEquiService.findContractLabel(safeSpecConsEqui.getProSimpleInfo().getId());
		}
		model.addAttribute("contractlabels", contractLabels);
		model.addAttribute("projectNameList", list);
		
		page.setList(specConsEquiList);
		model.addAttribute("page", page);
		return "modules/safe/safeSpecConsEquiList";
	}

	@RequiresPermissions("safe:safeSpecConsEqui:view")
	@RequestMapping(value = "form")
	public String form(SafeSpecConsEqui safeSpecConsEqui, Model model) {
		if(safeSpecConsEqui.getId() != null && !"".equals(safeSpecConsEqui.getId())){
			String constractSectionType = safeSpecConsEqui.getContractSectionType();
			if(Constant.getContractSectionType(constractSectionType).length() > 0){
				safeSpecConsEqui.setContractSectionLabel(
						safeSpecConsEqui.getContractSectionLabel().substring(3).toString());
			}
		}
		
		// 回显人员信息
		String operatorUserStr = "";
		if(safeSpecConsEqui.getOperatorUser() != null 
				&& safeSpecConsEqui.getOperatorUser().getId() != null 
				&& !"".equals(safeSpecConsEqui.getOperatorUser().getId())){
			SafeOpertionUserinfo operatorUser = safeSpecConsEqui.getOperatorUser();
			operatorUser = safeOpertionUserinfoService.get(operatorUser);
			safeSpecConsEqui.setOperatorUser(operatorUser);
			operatorUserStr += operatorUser.getName();
			if(safeSpecConsEqui.getOperatorUserOther() != null 
					&& safeSpecConsEqui.getOperatorUserOther().getId() != null 
					&& !"".equals(safeSpecConsEqui.getOperatorUserOther().getId())){
				SafeOpertionUserinfo operatorOtherUser = safeOpertionUserinfoService.get(safeSpecConsEqui.getOperatorUserOther());
				safeSpecConsEqui.setOperatorUserOther(operatorOtherUser);
				operatorUserStr += ", " + operatorOtherUser.getName();
			}
		}
		
		// 获取项目名称集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
				
				
		model.addAttribute("operatorUser", operatorUserStr);
		model.addAttribute("safeSpecConsEqui", safeSpecConsEqui);
		return "modules/safe/safeSpecConsEquiForm";
	}

	@RequiresPermissions("safe:safeSpecConsEqui:edit")
	@RequestMapping(value = "save")
	public String save(SafeSpecConsEqui safeSpecConsEqui, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, safeSpecConsEqui)){
			return form(safeSpecConsEqui, model);
		}
		String contractSectionType = Constant.getContractSectionType(
				safeSpecConsEqui.getContractSectionType());
		safeSpecConsEqui.setContractSectionLabel(contractSectionType + safeSpecConsEqui.getContractSectionLabel());
		safeSpecConsEquiService.save(safeSpecConsEqui);
		addMessage(redirectAttributes, "保存专用设备成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecConsEqui/?repage";
	}
	
	// 退场
	@RequiresPermissions("safe:safeSpecConsEqui:exit")
	@RequestMapping(value = "exit")
	public String exit(SafeSpecConsEqui safeSpecConsEqui, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, safeSpecConsEqui)){
			return form(safeSpecConsEqui, model);
		}
		String contractSectionType = Constant.getContractSectionType(
				safeSpecConsEqui.getContractSectionType());
		safeSpecConsEqui.setContractSectionLabel(contractSectionType + safeSpecConsEqui.getContractSectionLabel());
		safeSpecConsEqui.setExitTime(DateUtils.parseDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd")));
		safeSpecConsEquiService.save(safeSpecConsEqui);
		addMessage(redirectAttributes, "专用设备退场成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecConsEqui/?repage";
	}
	
	@RequiresPermissions("safe:safeSpecConsEqui:edit")
	@RequestMapping(value = "delete")
	public String delete(SafeSpecConsEqui safeSpecConsEqui, RedirectAttributes redirectAttributes) {
		safeSpecConsEquiService.delete(safeSpecConsEqui);
		addMessage(redirectAttributes, "删除专用设备成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecConsEqui/?repage";
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
		return safeSpecConsEquiService.findContractLabel(projectId);
	}
	
	
	@RequestMapping(value="export")
	public String export(SafeSpecConsEqui safeSpecConsEqui, 
			HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes redirectAttributes){
		try {
			if(StringUtils.isBlank(safeSpecConsEqui.getProSimpleInfo().getId()) 
					|| StringUtils.isBlank(safeSpecConsEqui.getContractSectionLabel()) ){
				addMessage(redirectAttributes, "参数不全");
    			return "redirect:"+Global.getAdminPath()+"/safe/safeSpecConsEqui/?repage";
			}
			
			Page<SafeSpecConsEqui> page = safeSpecConsEquiService.findPage(
            		new Page<SafeSpecConsEqui>(request, response), safeSpecConsEqui);
            List<SafeSpecConsEqui> data = page.getList();
            List<String[][]> dataList = new ArrayList<String[][]>();
            if(data.size() > 0){
            	for (int i = 0; i < data.size(); i++) {
            		safeSpecConsEqui = data.get(i);
            		
            		String[][] strArr;
            		if(safeSpecConsEqui.getOperatorUserOther().getId() != null && !"".equals(safeSpecConsEqui.getOperatorUserOther().getId())){
            			strArr= new String[2][14];
            		} else {
            			strArr= new String[1][14];
            		}
                	strArr[0][0] = String.valueOf(i+1);
                	strArr[0][1] = safeSpecConsEqui.getManagementNumber();
                	strArr[0][2] = safeSpecConsEqui.getSpecification();
                	strArr[0][3] = safeSpecConsEqui.getManufacturer();
                	strArr[0][4] = DateUtils.formatDate(safeSpecConsEqui.getProductionDate(), "yyyy-MM-dd");
                	strArr[0][5] = safeSpecConsEqui.getEquipmentSource();
        			strArr[0][6] = DateUtils.formatDate(safeSpecConsEqui.getApproachTime(), "yyyy-MM-dd");
        			strArr[0][7] = safeSpecConsEqui.getExitTime() == null ? "" : DateUtils.formatDate(safeSpecConsEqui.getExitTime(), "yyyy-MM-dd");
        			strArr[0][8] = safeSpecConsEqui.getReportCompany() 
        						 + " " 
        						 + DateUtils.formatDate(safeSpecConsEqui.getReportTime(), "yyyy-MM-dd");
        			
        			SafeOpertionUserinfo operatorUser = safeOpertionUserinfoService.get(safeSpecConsEqui.getOperatorUser());
        			strArr[0][9] = operatorUser.getName();
        			strArr[0][10] = operatorUser.getGender();
        			strArr[0][11] = operatorUser.getCertificateNo();
        			strArr[0][12] = DateUtils.formatDate(operatorUser.getCertificateValid(), "yyyy-MM-dd");
        			strArr[0][13] = operatorUser.getWorkSite();
        			
            		if(safeSpecConsEqui.getOperatorUserOther().getId() != null && !"".equals(safeSpecConsEqui.getOperatorUserOther().getId())){
            			SafeOpertionUserinfo operatorUserOther = safeOpertionUserinfoService.get(safeSpecConsEqui.getOperatorUserOther());
            			strArr[1][9] = operatorUserOther.getName();
            			strArr[1][10] = operatorUserOther.getGender();
            			strArr[1][11] = operatorUserOther.getCertificateNo();
            			strArr[1][12] = DateUtils.formatDate(operatorUserOther.getCertificateValid(), "yyyy-MM-dd");
            			strArr[1][13] = operatorUserOther.getWorkSite();
            		} 
            		
            		dataList.add(strArr);
    			}
            } else {
            	addMessage(redirectAttributes, "没有数据");
    			return "redirect:" + adminPath + "/safe/safeSpecConsEqui/list?repage";
            }
            
			String fileName = "专用设备台账"+DateUtils.getDate("yyyyMMddHHmmss")+".doc";
			String title = "专用设备台账";
            String[] fuTitle = { safeSpecConsEqui.getProSimpleInfo().getProjectSimpleName(),
            		safeSpecConsEqui.getContractSectionLabel(),
            		data.get(0).getConstructionName() };
            int[] widths = {3, 8, 8, 8, 8, 6, 8, 8, 8, 8, 3, 8, 8, 8};
            List<String> head = new ArrayList<String>();
            head.add("序号");
            head.add("管理编号");
            head.add("规格型号");
            head.add("生产厂家");
            head.add("生产日期");
            head.add("设备来源（自有、租赁）");
            head.add("进场时间");
            head.add("退场时间");
            head.add("计算书符合报告出具单位及出具时间");
            head.add("操作人员姓名");
            head.add("性别");
            head.add("执业证书编号");
            head.add("有效期");
            head.add("作业工点或者位置");
            
            new WordUtils(WordUtils.RECTANGLE_2).exportSafeSpecEqui(title, fuTitle, widths, head, dataList, response).write(fileName);
			addMessage(redirectAttributes, "下载成功");
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/safe/safeSpecConsEqui/list?repage";
	}
	
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeSpecConsEqui:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SafeSpecConsEqui> list = ei.getDataList(SafeSpecConsEqui.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			SafeSpecConsEqui safeSpecConsEqui = new SafeSpecConsEqui();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						safeSpecConsEqui = list.get(i);
						if (safeSpecConsEqui.getProjectName() != null && !safeSpecConsEqui.getProjectName().equals("")){
							ProSimpleInfo projectInfo = new ProSimpleInfo();
							projectInfo.setProjectSimpleName(safeSpecConsEqui.getProjectName());
							List<ProSimpleInfo> proList = proSimpleInfoService.findAllProjectNames(projectInfo);
							if(proList != null && proList.size() > 0){
								safeSpecConsEqui.setProSimpleInfo(proList.get(0));
								safeSpecConsEqui.setCreateBy(user);
								safeSpecConsEqui.setUpdateBy(user);
								safeSpecConsEqui.setCreateDate(now);
								safeSpecConsEqui.setUpdateDate(now);
								safeSpecConsEqui.setContractSectionType(Constant.getContractSectionType(
										safeSpecConsEqui.getContractSectionLabel().substring(0, 3)));
								safeSpecConsEqui.setEquipmentSource(DictUtils.getDictValue(safeSpecConsEqui.getEquipmentSource(), 
										"safe_equipment_source", "1"));
								safeSpecConsEquiService.save(safeSpecConsEqui);
								successNum++;
							} else {
								failureMsg.append("<br/>项目" + safeSpecConsEqui.getProjectName() + " 不存在;");
							}
						}
						
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>项目 " + safeSpecConsEqui.getProjectName() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>项目 " + safeSpecConsEqui.getProjectName() + " 导入失败："+ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条专用设备信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条专用设备信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecConsEqui/?repage";
    }
	
	
	
	/**
	 * 下载导入建设单位用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeSpecConsEqui:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "专用设备导入模板.xlsx";
    		List<SafeSpecConsEqui> list = Lists.newArrayList(); 
    		list.add(new SafeSpecConsEqui());
    		new ExportExcel("专用设备信息", SafeSpecConsEqui.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecConsEqui/?repage";
    }
	
	
}