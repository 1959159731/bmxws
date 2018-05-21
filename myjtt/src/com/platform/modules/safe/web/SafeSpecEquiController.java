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
import com.platform.modules.safe.entity.SafeSpecEqui;
import com.platform.modules.safe.service.SafeOpertionUserinfoService;
import com.platform.modules.safe.service.SafeSpecEquiService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.DictUtils;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.sys.utils.WordUtils;

/**
 * 特种设备Controller
 * @author Mr.Jia
 * @version 2017-12-04
 */
@Controller
@RequestMapping(value = "${adminPath}/safe/safeSpecEqui")
public class SafeSpecEquiController extends BaseController {

	@Autowired
	private SafeSpecEquiService safeSpecEquiService;
	@Autowired
	private SafeOpertionUserinfoService safeOpertionUserinfoService;
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public SafeSpecEqui get(@RequestParam(required=false) String id) {
		SafeSpecEqui entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = safeSpecEquiService.get(id);
		}
		if (entity == null){
			entity = new SafeSpecEqui();
		}
		return entity;
	}
	
	@RequiresPermissions("safe:safeSpecEqui:view")
	@RequestMapping(value = {"list", ""})
	public String list(SafeSpecEqui safeSpecEqui, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SafeSpecEqui> page = safeSpecEquiService.findPage(new Page<SafeSpecEqui>(request, response), safeSpecEqui); 
		
		List<SafeSpecEqui> specEquiList = page.getList();
		if(specEquiList != null && specEquiList.size() > 0){
			for (int i = 0; i < specEquiList.size(); i++) {
				safeSpecEqui  = specEquiList.get(i);
				SafeOpertionUserinfo operatorUser = safeSpecEqui.getOperatorUser();
				safeSpecEqui.setOperatorUser(safeOpertionUserinfoService.get(operatorUser));
				if(safeSpecEqui.getOperatorUserOther() != null){
					safeSpecEqui.setOperatorUserOther(safeOpertionUserinfoService.get(safeSpecEqui.getOperatorUserOther()));
				}
			}
		}
		
		// 获取项目名称list集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		
		// 获取合同段编号集合（list页面查找）
		List<String> contractLabels = new ArrayList<String>();
		if(safeSpecEqui.getProSimpleInfo() != null 
				&& safeSpecEqui.getProSimpleInfo().getId() != null 
				&& !"".equals(safeSpecEqui.getProSimpleInfo().getId()) ){
			contractLabels = safeSpecEquiService.findContractLabel(safeSpecEqui.getProSimpleInfo().getId());
		}
		model.addAttribute("contractlabels", contractLabels);
		model.addAttribute("projectNameList", list);
		
		page.setList(specEquiList);
		model.addAttribute("page", page);
		return "modules/safe/safeSpecEquiList";
	}

	@RequiresPermissions("safe:safeSpecEqui:view")
	@RequestMapping(value = "form")
	public String form(SafeSpecEqui safeSpecEqui, Model model) {
		if(safeSpecEqui.getId() != null && !"".equals(safeSpecEqui.getId())){
			String constractSectionType = safeSpecEqui.getContractSectionType();
			if(Constant.getContractSectionType(constractSectionType).length() > 0){
				safeSpecEqui.setContractSectionLabel(
						safeSpecEqui.getContractSectionLabel().substring(3).toString());
			}
		}
		
		// 回显人员信息
		String operatorUserStr = "";
		if(safeSpecEqui.getOperatorUser() != null 
				&& safeSpecEqui.getOperatorUser().getId() != null 
				&& !"".equals(safeSpecEqui.getOperatorUser().getId())){
			SafeOpertionUserinfo operatorUser = safeSpecEqui.getOperatorUser();
			operatorUser = safeOpertionUserinfoService.get(operatorUser);
			safeSpecEqui.setOperatorUser(operatorUser);
			operatorUserStr += operatorUser.getName();
			if(safeSpecEqui.getOperatorUserOther() != null 
				&& safeSpecEqui.getOperatorUserOther().getId() != null 
				&& !"".equals(safeSpecEqui.getOperatorUserOther().getId())){
				SafeOpertionUserinfo operatorOtherUser = safeOpertionUserinfoService.get(safeSpecEqui.getOperatorUserOther());
				safeSpecEqui.setOperatorUserOther(operatorOtherUser);
				operatorUserStr += ", " + operatorOtherUser.getName();
			}
		}
		
		// 获取项目名称集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("operatorUser", operatorUserStr);
		model.addAttribute("safeSpecEqui", safeSpecEqui);
		return "modules/safe/safeSpecEquiForm";
	}

	@RequiresPermissions("safe:safeSpecEqui:edit")
	@RequestMapping(value = "save")
	public String save(SafeSpecEqui safeSpecEqui, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, safeSpecEqui)){
			return form(safeSpecEqui, model);
		}
		String contractSectionType = Constant.getContractSectionType(
				safeSpecEqui.getContractSectionType());
		safeSpecEqui.setContractSectionLabel(contractSectionType + safeSpecEqui.getContractSectionLabel());
		safeSpecEquiService.save(safeSpecEqui);
		addMessage(redirectAttributes, "保存特种设备成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecEqui/?repage";
	}
	
	@RequiresPermissions("safe:safeSpecEqui:exit")
	@RequestMapping(value = "exit")
	public String exit(SafeSpecEqui safeSpecEqui, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, safeSpecEqui)){
			return form(safeSpecEqui, model);
		}
		String contractSectionType = Constant.getContractSectionType(
				safeSpecEqui.getContractSectionType());
		safeSpecEqui.setContractSectionLabel(contractSectionType + safeSpecEqui.getContractSectionLabel());
		safeSpecEqui.setExitTime(DateUtils.parseDate(DateUtils.formatDate(new Date(), "yyyy-MM-dd")));
		safeSpecEquiService.save(safeSpecEqui);
		addMessage(redirectAttributes, "特种设备退场成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecEqui/?repage";
	}
	
	@RequiresPermissions("safe:safeSpecEqui:edit")
	@RequestMapping(value = "delete")
	public String delete(SafeSpecEqui safeSpecEqui, RedirectAttributes redirectAttributes) {
		safeSpecEquiService.delete(safeSpecEqui);
		addMessage(redirectAttributes, "删除特种设备成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecEqui/?repage";
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
		return safeSpecEquiService.findContractLabel(projectId);
	}
	
	
	@RequestMapping(value="export")
	public String export(SafeSpecEqui safeSpecEqui, 
			HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes redirectAttributes){
		try {
			if(StringUtils.isBlank(safeSpecEqui.getProSimpleInfo().getId()) 
					|| StringUtils.isBlank(safeSpecEqui.getContractSectionLabel()) ){
				addMessage(redirectAttributes, "参数不全");
    			return "redirect:"+Global.getAdminPath()+"/safe/safeSpecEqui/?repage";
			}
			
			Page<SafeSpecEqui> page = safeSpecEquiService.findPage(
            		new Page<SafeSpecEqui>(request, response), safeSpecEqui);
            List<SafeSpecEqui> data = page.getList();
            List<String[][]> dataList = new ArrayList<String[][]>();
            if(data.size() > 0){
            	for (int i = 0; i < data.size(); i++) {
            		safeSpecEqui = data.get(i);
            		
            		String[][] strArr;
            		if(safeSpecEqui.getOperatorUserOther().getId() != null && !"".equals(safeSpecEqui.getOperatorUserOther().getId())){
            			strArr= new String[2][14];
            		} else {
            			strArr= new String[1][14];
            		}
                	strArr[0][0] = String.valueOf(i+1);
                	strArr[0][1] = safeSpecEqui.getManagementNumber();
                	strArr[0][2] = safeSpecEqui.getSpecification();
                	strArr[0][3] = safeSpecEqui.getManufacturer();
                	strArr[0][4] = DateUtils.formatDate(safeSpecEqui.getProductionDate(), "yyyy-MM");
                	strArr[0][5] = DictUtils.getDictLabel(safeSpecEqui.getEquipmentSource(), "safe_equipment_source", "");
        			strArr[0][6] = DateUtils.formatDate(safeSpecEqui.getApproachTime(), "yyyy-MM");
        			strArr[0][7] = safeSpecEqui.getExitTime() == null ? "" : DateUtils.formatDate(safeSpecEqui.getExitTime(), "yyyy-MM");
        			strArr[0][8] = safeSpecEqui.getInspectionCertnum();
        			
        			SafeOpertionUserinfo operatorUser = safeOpertionUserinfoService.get(safeSpecEqui.getOperatorUser());
        			strArr[0][9] = operatorUser.getName();
        			strArr[0][10] = DictUtils.getDictLabel(operatorUser.getGender(), "sex", ""); 
        			strArr[0][11] = operatorUser.getCertificateNo();
        			strArr[0][12] = DateUtils.formatDate(operatorUser.getCertificateValid(), "yyyy-MM-dd");
        			strArr[0][13] = operatorUser.getWorkSite();
        			
            		if(safeSpecEqui.getOperatorUserOther().getId() != null && !"".equals(safeSpecEqui.getOperatorUserOther().getId())){
            			SafeOpertionUserinfo operatorUserOther = safeOpertionUserinfoService.get(safeSpecEqui.getOperatorUserOther());
            			strArr[1][9] = operatorUserOther.getName();
            			strArr[1][10] = DictUtils.getDictLabel(operatorUser.getGender(), "sex", "");
            			strArr[1][11] = operatorUserOther.getCertificateNo();
            			strArr[1][12] = DateUtils.formatDate(operatorUserOther.getCertificateValid(), "yyyy-MM-dd");
            			strArr[1][13] = operatorUserOther.getWorkSite();
            		} 
            		
            		dataList.add(strArr);
    			}
            } else {
            	addMessage(redirectAttributes, "没有数据");
    			return "redirect:" + adminPath + "/safe/safeSpecEqui/list?repage";
            }
            
			String fileName = "特种设备台账"+DateUtils.getDate("yyyyMMddHHmmss")+".doc";
			String title = "特种设备台账";
            String[] fuTitle = { safeSpecEqui.getProSimpleInfo().getProjectSimpleName(),
            		safeSpecEqui.getContractSectionLabel(),
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
            head.add("检验合格证书编号");
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
		return "redirect:" + adminPath + "/safe/safeSpecEqui/list?repage";
	}
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeSpecEqui:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SafeSpecEqui> list = ei.getDataList(SafeSpecEqui.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			SafeSpecEqui safeSpecEqui = new SafeSpecEqui();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						safeSpecEqui = list.get(i);
						if (safeSpecEqui.getProjectName() != null && !safeSpecEqui.getProjectName().equals("")){
							ProSimpleInfo projectInfo = new ProSimpleInfo();
							projectInfo.setProjectSimpleName(safeSpecEqui.getProjectName());
							List<ProSimpleInfo> proList = proSimpleInfoService.findAllProjectNames(projectInfo);
							if(proList != null && proList.size() > 0){
								safeSpecEqui.setProSimpleInfo(proList.get(0));
								safeSpecEqui.setCreateBy(user);
								safeSpecEqui.setUpdateBy(user);
								safeSpecEqui.setCreateDate(now);
								safeSpecEqui.setUpdateDate(now);
								safeSpecEqui.setContractSectionType(Constant.getContractSectionType(
										safeSpecEqui.getContractSectionLabel().substring(0, 3)));
								safeSpecEqui.setEquipmentSource(DictUtils.getDictValue(safeSpecEqui.getEquipmentSource(), 
										"safe_equipment_source", "1"));
								safeSpecEquiService.save(safeSpecEqui);
								successNum++;
							} else {
								failureMsg.append("<br/>项目" + safeSpecEqui.getProjectName() + " 不存在;");
							}
						}
						
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>项目 " + safeSpecEqui.getProjectName() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>项目 " + safeSpecEqui.getProjectName() + " 导入失败："+ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条特种设备信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条特种设备信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecEqui/?repage";
    }
	
	
	
	/**
	 * 下载导入建设单位用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeSpecEqui:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "特种设备导入模板.xlsx";
    		List<SafeSpecEqui> list = Lists.newArrayList(); 
    		list.add(new SafeSpecEqui());
    		new ExportExcel("特种设备信息", SafeSpecEqui.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecEqui/?repage";
    }
}