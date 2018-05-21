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
import com.platform.modules.safe.entity.SafeProManagerPerson;
import com.platform.modules.safe.service.SafeProManagerPersonService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.DictUtils;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.sys.utils.WordUtils;

/**
 * 安全生产管理人员 Controller
 * @author Mr.Jia
 * @version 2017-11-26
 */
@Controller
@RequestMapping(value = "${adminPath}/safe/safeProManagerPerson")
public class SafeProManagerPersonController extends BaseController {

	@Autowired
	private SafeProManagerPersonService safeProManagerPersonService;
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	
	@ModelAttribute
	public SafeProManagerPerson get(@RequestParam(required=false) String id) {
		SafeProManagerPerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = safeProManagerPersonService.get(id);
		}
		if (entity == null){
			entity = new SafeProManagerPerson();
		}
		return entity;
	}
	
	@RequiresPermissions("safe:safeProManagerPerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(SafeProManagerPerson safeProManagerPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SafeProManagerPerson> page = safeProManagerPersonService.findPage(new Page<SafeProManagerPerson>(request, response), safeProManagerPerson); 
		
		// 获取项目名称list集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		
		// 获取合同段编号集合（list页面查找）
		List<String> contractLabels = new ArrayList<String>();
		if(safeProManagerPerson.getProSimpleInfo() != null 
				&& safeProManagerPerson.getProSimpleInfo().getId() != null 
				&& !"".equals(safeProManagerPerson.getProSimpleInfo().getId()) ){
			contractLabels = safeProManagerPersonService.findContractLabel(safeProManagerPerson.getProSimpleInfo().getId());
		}
		model.addAttribute("contractlabels", contractLabels);
		model.addAttribute("projectNameList", list);
		model.addAttribute("page", page);
		return "modules/safe/safeProManagerPersonList";
	}

	@RequiresPermissions("safe:safeProManagerPerson:view")
	@RequestMapping(value = "form")
	public String form(SafeProManagerPerson safeProManagerPerson, Model model) {
		if(safeProManagerPerson.getId() != null && !"".equals(safeProManagerPerson.getId())){
			String constractSectionType = safeProManagerPerson.getContractSectionType();
			if(Constant.getContractSectionType(constractSectionType).length() > 0){
				safeProManagerPerson.setContractSectionLabel(
						safeProManagerPerson.getContractSectionLabel().substring(3).toString());
			}
		}
		
		// 获取项目名称集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		
		model.addAttribute("safeProManagerPerson", safeProManagerPerson);
		return "modules/safe/safeProManagerPersonForm";
	}

	@RequiresPermissions("safe:safeProManagerPerson:edit")
	@RequestMapping(value = "save")
	public String save(SafeProManagerPerson safeProManagerPerson, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, safeProManagerPerson)){
			return form(safeProManagerPerson, model);
		}
		String contractSectionType = Constant.getContractSectionType(
				safeProManagerPerson.getContractSectionType());
		safeProManagerPerson.setContractSectionLabel(contractSectionType + safeProManagerPerson.getContractSectionLabel());
		safeProManagerPersonService.save(safeProManagerPerson);
		addMessage(redirectAttributes, "保存安全生产管理人员成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeProManagerPerson/?repage";
	}
	
	@RequiresPermissions("safe:safeProManagerPerson:edit")
	@RequestMapping(value = "delete")
	public String delete(SafeProManagerPerson safeProManagerPerson, RedirectAttributes redirectAttributes) {
		safeProManagerPersonService.delete(safeProManagerPerson);
		addMessage(redirectAttributes, "删除安全生产管理人员成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeProManagerPerson/?repage";
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
		return safeProManagerPersonService.findContractLabel(projectId); 
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
	public String export(SafeProManagerPerson safeProManagerPerson, 
			HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes redirectAttributes){
			
		try {
			if(StringUtils.isBlank(safeProManagerPerson.getProSimpleInfo().getId()) 
					|| StringUtils.isBlank(safeProManagerPerson.getContractSectionLabel()) ){
				addMessage(redirectAttributes, "参数不全");
    			return "redirect:"+Global.getAdminPath()+"/safe/safeProManagerPerson/?repage";
			}
			
			Page<SafeProManagerPerson> page = safeProManagerPersonService.findPage(new Page<SafeProManagerPerson>(request, response), safeProManagerPerson);
            List<SafeProManagerPerson> data = page.getList();
            List<String[]> dataList = new ArrayList<String[]>();
            List<String> imgPath = new ArrayList<String>();
            if(data.size() > 0){
            	for (int i = 0; i < data.size(); i++) {
            		safeProManagerPerson = data.get(i);
            		imgPath.add(safeProManagerPerson.getCertificatePath());
                	String[] strArr = new String[7];
                	strArr[0] = String.valueOf(i+1);
                	strArr[1] = safeProManagerPerson.getName();
                	strArr[2] = safeProManagerPerson.getSafetyProductionCertnumber();
                	strArr[3] = DateUtils.formatDate(safeProManagerPerson.getCertificateValid(), "yyyy-MM-dd");
                	strArr[4] = safeProManagerPerson.getJobTitle();
                	strArr[5] = DictUtils.getDictLabel(safeProManagerPerson.getPositon(), "opertion_user_type", "");
                	strArr[6] = safeProManagerPerson.getRemarks();
                	dataList.add(strArr);
    			}
            } else {
            	addMessage(redirectAttributes, "没有数据");
    			return "redirect:"+Global.getAdminPath()+"/safe/safeProManagerPerson/?repage";
            }
            
            
            
			String fileName = "安全生产管理人员"+DateUtils.getDate("yyyyMMddHHmmss")+".doc";
			String title = "安全生产管理人员";
            String[] fuTitle = { safeProManagerPerson.getProSimpleInfo().getProjectSimpleName(),
            		safeProManagerPerson.getContractSectionLabel(),
            		data.get(0).getConstructionName() };
            int[] widths = {10, 10, 30, 15, 15, 10, 10};
            List<String> head = new ArrayList<String>();
            head.add("序号");
            head.add("姓名");
            head.add("安全生产考核合格证书编号");
            head.add("证书有效期");
            head.add("专业及职称");
            head.add("职务/职责");
            head.add("备注");
            
            new WordUtils(WordUtils.RECTANGLE_1).exportSafeProManagerPerson(title, fuTitle, widths, head, dataList, imgPath, response).write(fileName);
			addMessage(redirectAttributes, "下载成功");
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeProManagerPerson/?repage";
	}
	
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeProManagerPerson:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SafeProManagerPerson> list = ei.getDataList(SafeProManagerPerson.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			SafeProManagerPerson safeProManagerPerson = new SafeProManagerPerson();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						safeProManagerPerson = list.get(i);
						if (safeProManagerPerson.getProjectName() != null && !safeProManagerPerson.getProjectName().equals("")){
							ProSimpleInfo projectInfo = new ProSimpleInfo();
							projectInfo.setProjectSimpleName(safeProManagerPerson.getProjectName());
							List<ProSimpleInfo> proList = proSimpleInfoService.findAllProjectNames(projectInfo);
							if(proList != null && proList.size() > 0){
								safeProManagerPerson.setProSimpleInfo(proList.get(0));
								safeProManagerPerson.setCreateBy(user);
								safeProManagerPerson.setUpdateBy(user);
								safeProManagerPerson.setCreateDate(now);
								safeProManagerPerson.setUpdateDate(now);
								safeProManagerPerson.setContractSectionType(Constant.getContractSectionType(
										safeProManagerPerson.getContractSectionLabel().substring(0, 3)));
								safeProManagerPerson.setPositon(DictUtils.getDictValue(safeProManagerPerson.getPositon(), 
										"safe_prodect_manger_position", "项目负责人"));
								safeProManagerPersonService.save(safeProManagerPerson);
								successNum++;
							} else {
								failureMsg.append("<br/>项目" + safeProManagerPerson.getProjectName() + " 不存在;");
							}
						}
						
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>项目 " + safeProManagerPerson.getProjectName() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>项目 " + safeProManagerPerson.getProjectName() + " 导入失败："+ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条安全生产管理人员信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条安全生产管理人员信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeProManagerPerson/?repage";
    }
	
	
	
	/**
	 * 下载导入建设单位用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeProManagerPerson:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "安全生产管理人员导入模板.xlsx";
    		List<SafeProManagerPerson> list = Lists.newArrayList(); 
    		list.add(new SafeProManagerPerson());
    		new ExportExcel("安全生产管理人员信息", SafeProManagerPerson.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeProManagerPerson/?repage";
    }
	
	
}