package com.platform.modules.safe.web;

import java.io.UnsupportedEncodingException;
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
import com.platform.modules.safe.entity.SafeConsPersonStation;
import com.platform.modules.safe.service.SafeConsPersonStationService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.DictUtils;
import com.platform.modules.sys.utils.UserUtils;
import com.platform.modules.sys.utils.WordUtils;

/**
 * 施工人员驻地管理Controller
 * @author Mr.Jia
 * @version 2017-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/safe/safeConsPersonStation")
public class SafeConsPersonStationController extends BaseController {

	@Autowired
	private SafeConsPersonStationService safeConsPersonStationService;
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public SafeConsPersonStation get(@RequestParam(required=false) String id) {
		SafeConsPersonStation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = safeConsPersonStationService.get(id);
		}
		if (entity == null){
			entity = new SafeConsPersonStation();
		}
		return entity;
	}
	
	@RequiresPermissions("safe:safeConsPersonStation:view")
	@RequestMapping(value = {"list", ""})
	public String list(SafeConsPersonStation safeConsPersonStation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SafeConsPersonStation> page = safeConsPersonStationService.findPage(new Page<SafeConsPersonStation>(request, response), safeConsPersonStation);
		
		// 获取项目名称list集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		
		// 获取合同段编号集合（list页面查找）
		List<String> contractLabels = new ArrayList<String>();
		if(safeConsPersonStation.getProSimpleInfo() != null 
				&& safeConsPersonStation.getProSimpleInfo().getId() != null 
				&& !"".equals(safeConsPersonStation.getProSimpleInfo().getId()) ){
			contractLabels = safeConsPersonStationService.findContractLabel(safeConsPersonStation.getProSimpleInfo().getId());
		}
		model.addAttribute("contractlabels", contractLabels);
		model.addAttribute("projectNameList", list);
				
		model.addAttribute("page", page);
		return "modules/safe/safeConsPersonStationList";
	}

	@RequiresPermissions("safe:safeConsPersonStation:view")
	@RequestMapping(value = "form")
	public String form(SafeConsPersonStation safeConsPersonStation, Model model) {
		if(safeConsPersonStation.getId() != null && !"".equals(safeConsPersonStation.getId())){
			String constractSectionType = safeConsPersonStation.getContractSectionType();
			if(Constant.getContractSectionType(constractSectionType).length() > 0){
				safeConsPersonStation.setContractSectionLabel(
						safeConsPersonStation.getContractSectionLabel().substring(3).toString());
			}
		}
		
		// 获取项目名称集合
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
				
		model.addAttribute("safeConsPersonStation", safeConsPersonStation);
		return "modules/safe/safeConsPersonStationForm";
	}

	@RequiresPermissions("safe:safeConsPersonStation:edit")
	@RequestMapping(value = "save")
	public String save(SafeConsPersonStation safeConsPersonStation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, safeConsPersonStation)){
			return form(safeConsPersonStation, model);
		}
		String contractSectionType = Constant.getContractSectionType(
				safeConsPersonStation.getContractSectionType());
		safeConsPersonStation.setContractSectionLabel(contractSectionType + safeConsPersonStation.getContractSectionLabel());
		
		safeConsPersonStationService.save(safeConsPersonStation);
		addMessage(redirectAttributes, "保存施工人员驻地管理成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeConsPersonStation/?repage";
	}
	
	@RequiresPermissions("safe:safeConsPersonStation:edit")
	@RequestMapping(value = "delete")
	public String delete(SafeConsPersonStation safeConsPersonStation, RedirectAttributes redirectAttributes) {
		safeConsPersonStationService.delete(safeConsPersonStation);
		addMessage(redirectAttributes, "删除施工人员驻地管理成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeConsPersonStation/?repage";
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
		return safeConsPersonStationService.findContractLabel(projectId);
	}
	
	@RequestMapping(value="export")
	public String export(SafeConsPersonStation safeConsPersonStation, 
			HttpServletRequest request, HttpServletResponse response, 
			RedirectAttributes redirectAttributes){
		try {
			if(StringUtils.isBlank(safeConsPersonStation.getProSimpleInfo().getId()) 
					|| StringUtils.isBlank(safeConsPersonStation.getContractSectionLabel()) ){
				addMessage(redirectAttributes, "参数不全");
    			return "redirect:"+Global.getAdminPath()+"/safe/safeConsPersonStation/?repage";
			}
			
			Page<SafeConsPersonStation> page = safeConsPersonStationService.findPage(
            		new Page<SafeConsPersonStation>(request, response), safeConsPersonStation);
            List<SafeConsPersonStation> data = page.getList();
            List<String[]> dataList = new ArrayList<String[]>();
            if(data.size() > 0){
            	for (int i = 0; i < data.size(); i++) {
            		safeConsPersonStation = data.get(i);
                	String[] strArr = new String[7];
                	strArr[0] = String.valueOf(i+1);
                	strArr[1] = safeConsPersonStation.getResidentSources().equals("1") ? "自建" : "租赁";
                	strArr[2] = safeConsPersonStation.getResidentType().equals("1")?"活动板房":
                			safeConsPersonStation.getResidentType().equals("2")?"商品房":"居民自建房";
                	strArr[3] = safeConsPersonStation.getResidentSite();
                	strArr[4] = safeConsPersonStation.getTeamName();
                	strArr[5] = safeConsPersonStation.getLiveNum();
                	strArr[6] = safeConsPersonStation.getRemarks();
                	dataList.add(strArr);
    			}
            } else {
            	addMessage(redirectAttributes, "没有数据");
    			return "redirect:" + adminPath + "/safe/safeConsPersonStation/list?repage";
            }
            
			String fileName = "施工人员驻地管理"+DateUtils.getDate("yyyyMMddHHmmss")+".doc";
			String title = "施工人员驻地管理台账";
            String[] fuTitle = { safeConsPersonStation.getProSimpleInfo().getProjectSimpleName(),
            		safeConsPersonStation.getContractSectionLabel(),
            		data.get(0).getConstructionName() };
            int[] widths = {5, 10, 15, 25, 15, 10, 20};
            List<String> head = new ArrayList<String>();
            head.add("序号");
            head.add("驻地来源");
            head.add("驻地类型");
            head.add("驻地位置");
            head.add("居住的班组和工队名称");
            head.add("居住人数");
            head.add("消防器材配备情况");
            
            new WordUtils(WordUtils.RECTANGLE_2).exportSafeConsPersonStation(title, fuTitle, widths, head, dataList, response).write(fileName);
			addMessage(redirectAttributes, "下载成功");
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/safe/safeConsPersonStation/list?repage";
	}

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeConsPersonStation:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SafeConsPersonStation> list = ei.getDataList(SafeConsPersonStation.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			SafeConsPersonStation safeConsPersonStation = new SafeConsPersonStation();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						safeConsPersonStation = list.get(i);
						if (safeConsPersonStation.getProjectName() != null && !safeConsPersonStation.getProjectName().equals("")){
							ProSimpleInfo projectInfo = new ProSimpleInfo();
							projectInfo.setProjectSimpleName(safeConsPersonStation.getProjectName());
							List<ProSimpleInfo> proList = proSimpleInfoService.findAllProjectNames(projectInfo);
							if(proList != null && proList.size() > 0){
								safeConsPersonStation.setProSimpleInfo(proList.get(0));
								safeConsPersonStation.setCreateBy(user);
								safeConsPersonStation.setUpdateBy(user);
								safeConsPersonStation.setCreateDate(now);
								safeConsPersonStation.setUpdateDate(now);
								safeConsPersonStation.setContractSectionType(Constant.getContractSectionType(
										safeConsPersonStation.getContractSectionLabel().substring(0, 3)));
								safeConsPersonStation.setResidentSources(DictUtils.getDictValue(safeConsPersonStation.getResidentSources(), 
										"safe_resident_sources", "1"));
								safeConsPersonStation.setResidentType(DictUtils.getDictValue(safeConsPersonStation.getResidentType(), 
										"safe_resident_type", "1"));
								safeConsPersonStation.setRemarks(safeConsPersonStation.getFireEquipment());
								safeConsPersonStationService.save(safeConsPersonStation);
								successNum++;
							} else {
								failureMsg.append("<br/>项目" + safeConsPersonStation.getProjectName() + " 不存在;");
							}
						}
						
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>项目 " + safeConsPersonStation.getProjectName() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>项目 " + safeConsPersonStation.getProjectName() + " 导入失败："+ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条施工人员驻地信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条施工人员驻地信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeConsPersonStation/?repage";
    }
	
	
	
	/**
	 * 下载导入建设单位用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeConsPersonStation:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "施工人员驻地信息导入模板.xlsx";
    		List<SafeConsPersonStation> list = Lists.newArrayList(); 
    		list.add(new SafeConsPersonStation());
    		new ExportExcel("施工人员驻地信息", SafeConsPersonStation.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeConsPersonStation/?repage";
    }
	
}