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
import com.platform.modules.safe.entity.SafeSpecOperPerson;
import com.platform.modules.safe.service.SafeSpecOperPersonService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.DictUtils;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.sys.utils.WordUtils;

/**
 * 特种作业人员台帐Controller
 * @author Mr.Jia
 * @version 2017-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/safe/safeSpecOperPerson")
public class SafeSpecOperPersonController extends BaseController {

	@Autowired
	private SafeSpecOperPersonService safeSpecOperPersonService;
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public SafeSpecOperPerson get(@RequestParam(required=false) String id) {
		SafeSpecOperPerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = safeSpecOperPersonService.get(id);
		}
		if (entity == null){
			entity = new SafeSpecOperPerson();
		}
		return entity;
	}
	
	@RequiresPermissions("safe:safeSpecOperPerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(SafeSpecOperPerson safeSpecOperPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SafeSpecOperPerson> page = safeSpecOperPersonService.findPage(new Page<SafeSpecOperPerson>(request, response), safeSpecOperPerson); 
		// 获取项目名称list集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		
		// 获取合同段编号集合（list页面查找）
		List<String> contractLabels = new ArrayList<String>();
		if(safeSpecOperPerson.getProSimpleInfo() != null 
				&& safeSpecOperPerson.getProSimpleInfo().getId() != null 
				&& !"".equals(safeSpecOperPerson.getProSimpleInfo().getId()) ){
			contractLabels = safeSpecOperPersonService.findContractLabel(safeSpecOperPerson.getProSimpleInfo().getId());
		}
		model.addAttribute("contractlabels", contractLabels);
		model.addAttribute("projectNameList", list);
		model.addAttribute("page", page);
		return "modules/safe/safeSpecOperPersonList";
	}

	@RequiresPermissions("safe:safeSpecOperPerson:view")
	@RequestMapping(value = "form")
	public String form(SafeSpecOperPerson safeSpecOperPerson, Model model) {
		if(safeSpecOperPerson.getId() != null && !"".equals(safeSpecOperPerson.getId())){
			String constractSectionType = safeSpecOperPerson.getContractSectionType();
			if(Constant.getContractSectionType(constractSectionType).length() > 0){
				safeSpecOperPerson.setContractSectionLabel(
						safeSpecOperPerson.getContractSectionLabel().substring(3).toString());
			}
		}

		// 获取项目名称集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
				
		model.addAttribute("safeSpecOperPerson", safeSpecOperPerson);
		return "modules/safe/safeSpecOperPersonForm";
	}

	@RequiresPermissions("safe:safeSpecOperPerson:edit")
	@RequestMapping(value = "save")
	public String save(SafeSpecOperPerson safeSpecOperPerson, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, safeSpecOperPerson)){
			return form(safeSpecOperPerson, model);
		}
		String contractSectionType = Constant.getContractSectionType(
				safeSpecOperPerson.getContractSectionType());
		safeSpecOperPerson.setContractSectionLabel(contractSectionType + safeSpecOperPerson.getContractSectionLabel());
		
		safeSpecOperPersonService.save(safeSpecOperPerson);
		addMessage(redirectAttributes, "保存特种作业人员台帐成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecOperPerson/?repage";
	}
	
	@RequiresPermissions("safe:safeSpecOperPerson:edit")
	@RequestMapping(value = "delete")
	public String delete(SafeSpecOperPerson safeSpecOperPerson, RedirectAttributes redirectAttributes) {
		safeSpecOperPersonService.delete(safeSpecOperPerson);
		addMessage(redirectAttributes, "删除特种作业人员台帐成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecOperPerson/?repage";
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
		return safeSpecOperPersonService.findContractLabel(projectId);
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
	public String export(SafeSpecOperPerson safeSpecOperPerson, 
			HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes redirectAttributes){
		try {
			if(StringUtils.isBlank(safeSpecOperPerson.getProSimpleInfo().getId()) 
					|| StringUtils.isBlank(safeSpecOperPerson.getContractSectionLabel()) ){
				addMessage(redirectAttributes, "参数不全");
    			return "redirect:"+Global.getAdminPath()+"/safe/safeSpecOperPerson/?repage";
			}
			
			
			Page<SafeSpecOperPerson> page = safeSpecOperPersonService.findPage(
            		new Page<SafeSpecOperPerson>(request, response), safeSpecOperPerson);
            List<SafeSpecOperPerson> data = page.getList();
            List<String[]> dataList = new ArrayList<String[]>();
            List<String> imgPath = new ArrayList<String>();
            if(data.size() > 0){
            	for (int i = 0; i < data.size(); i++) {
            		safeSpecOperPerson = data.get(i);
            		imgPath.add(safeSpecOperPerson.getCertificatePath());
                	String[] strArr = new String[7];
                	strArr[0] = String.valueOf(i+1);
                	strArr[1] = safeSpecOperPerson.getName();
                	strArr[2] = safeSpecOperPerson.getGender().equals(Constant.SEX_MAN_NUM) ? Constant.SEX_MAN : Constant.SEX_WOMAN;
                	strArr[3] = safeSpecOperPerson.getOperateItems();
                	strArr[4] = safeSpecOperPerson.getPracticeCertnumber();
                	strArr[5] = DateUtils.formatDate(safeSpecOperPerson.getCertificateValid(), "yyyy-MM-dd");
                	strArr[6] = safeSpecOperPerson.getWorkSite();
                	dataList.add(strArr);
    			}
            } else {
            	addMessage(redirectAttributes, "没有数据");
    			return "redirect:"+Global.getAdminPath()+"/safe/safeSpecOperPerson/?repage";
            }
            
			String fileName = "特种作业人员"+DateUtils.getDate("yyyyMMddHHmmss")+".doc";
			String title = "特种作业人员";
            String[] fuTitle = { safeSpecOperPerson.getProSimpleInfo().getProjectSimpleName(),
            		safeSpecOperPerson.getContractSectionLabel(),
            		data.get(0).getConstructionName() };
            int[] widths = {8, 12, 8, 20, 20, 14, 18};
            List<String> head = new ArrayList<String>();
            head.add("序号");
            head.add("姓名");
            head.add("姓别");
            head.add("操作项目");
            head.add("执业证件编号");
            head.add("有效期");
            head.add("作业工点或者位置");
            
            new WordUtils(WordUtils.RECTANGLE_1).exportSafeSpecOperPerson(title, fuTitle, widths, head, dataList, imgPath, response).write(fileName);
			addMessage(redirectAttributes, "下载成功");
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecOperPerson/?repage";
	}
	
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeSpecOperPerson:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SafeSpecOperPerson> list = ei.getDataList(SafeSpecOperPerson.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			SafeSpecOperPerson safeSpecOperPerson = new SafeSpecOperPerson();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						safeSpecOperPerson = list.get(i);
						if (safeSpecOperPerson.getProjectName() != null && !safeSpecOperPerson.getProjectName().equals("")){
							ProSimpleInfo projectInfo = new ProSimpleInfo();
							projectInfo.setProjectSimpleName(safeSpecOperPerson.getProjectName());
							List<ProSimpleInfo> proList = proSimpleInfoService.findAllProjectNames(projectInfo);
							if(proList != null && proList.size() > 0){
								safeSpecOperPerson.setProSimpleInfo(proList.get(0));
								safeSpecOperPerson.setCreateBy(user);
								safeSpecOperPerson.setUpdateBy(user);
								safeSpecOperPerson.setCreateDate(now);
								safeSpecOperPerson.setUpdateDate(now);
								safeSpecOperPerson.setContractSectionType(Constant.getContractSectionType(
										safeSpecOperPerson.getContractSectionLabel().substring(0, 3)));
								safeSpecOperPerson.setGender(DictUtils.getDictValue(safeSpecOperPerson.getGender(), 
										"sex", "1"));
								safeSpecOperPersonService.save(safeSpecOperPerson);
								successNum++;
							} else {
								failureMsg.append("<br/>项目" + safeSpecOperPerson.getProjectName() + " 不存在;");
							}
						}
						
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>项目 " + safeSpecOperPerson.getProjectName() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>项目 " + safeSpecOperPerson.getProjectName() + " 导入失败："+ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条特种作业人员信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条特种作业人员信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecOperPerson/?repage";
    }
	
	
	
	/**
	 * 下载导入建设单位用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeSpecOperPerson:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "特种作业人员导入模板.xlsx";
    		List<SafeSpecOperPerson> list = Lists.newArrayList(); 
    		list.add(new SafeSpecOperPerson());
    		new ExportExcel("特种作业人员信息", SafeSpecOperPerson.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeSpecOperPerson/?repage";
    }
	
}