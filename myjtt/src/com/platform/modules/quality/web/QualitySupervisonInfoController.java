package com.platform.modules.quality.web;

import java.io.UnsupportedEncodingException;
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
import com.platform.modules.quality.entity.QualitySupervisonInfo;
import com.platform.modules.quality.service.QualitySupervisonInfoService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.UserUtils;

/**
 * 监理单位信息Controller
 * @author Mr.Jia
 * @version 2017-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/quality/qualitySupervisonInfo")
public class QualitySupervisonInfoController extends BaseController {

	@Autowired
	private QualitySupervisonInfoService qualitySupervisonInfoService;
	
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public QualitySupervisonInfo get(@RequestParam(required=false) String id) {
		QualitySupervisonInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qualitySupervisonInfoService.get(id);
		}
		if (entity == null){
			entity = new QualitySupervisonInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("quality:qualitySupervisonInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(QualitySupervisonInfo qualitySupervisonInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QualitySupervisonInfo> page = qualitySupervisonInfoService.findPage(new Page<QualitySupervisonInfo>(request, response), qualitySupervisonInfo); 
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("page", page);
		return "modules/quality/qualitySupervisonInfoList";
	}

	@RequiresPermissions("quality:qualitySupervisonInfo:view")
	@RequestMapping(value = "form")
	public String form(QualitySupervisonInfo qualitySupervisonInfo, Model model) {
		if(qualitySupervisonInfo.getId() != null && !"".equals(qualitySupervisonInfo.getId())){
			String constractSectionType = qualitySupervisonInfo.getContractSectionType();
			if(Constant.getContractSectionType(constractSectionType).length() > 0){
				qualitySupervisonInfo.setContractSectionLabel(
						qualitySupervisonInfo.getContractSectionLabel().substring(3).toString());
			}
		}
		
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("qualitySupervisonInfo", qualitySupervisonInfo);
		return "modules/quality/qualitySupervisonInfoForm";
	}

	@RequiresPermissions("quality:qualitySupervisonInfo:edit")
	@RequestMapping(value = "save")
	public String save(QualitySupervisonInfo qualitySupervisonInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, qualitySupervisonInfo)){
			return form(qualitySupervisonInfo, model);
		}
		qualitySupervisonInfo.setContractSectionLabel((qualitySupervisonInfo.getContractSectionType().equals("1")?"JL-":"")
					+ qualitySupervisonInfo.getContractSectionLabel());
		qualitySupervisonInfoService.save(qualitySupervisonInfo);
		addMessage(redirectAttributes, "保存监理单位信息成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonInfo/?repage";
	}
	
	@RequiresPermissions("quality:qualitySupervisonInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(QualitySupervisonInfo qualitySupervisonInfo, RedirectAttributes redirectAttributes) {
		qualitySupervisonInfoService.delete(qualitySupervisonInfo);
		addMessage(redirectAttributes, "删除监理单位信息成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonInfo/?repage";
	}

	
	/**
	 * 根据项目Id获取监理单位名称
	 * @param projectName
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value="getSupervisonCompanyName", method = RequestMethod.POST)
	public List<String> getSupervisonCompanyName(HttpServletRequest request, String projectId) {
		return qualitySupervisonInfoService.findSupervisonCompanyName(projectId);
	}
	
	
	@RequiresPermissions("quality:qualitySupervisonInfo:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String export(HttpServletRequest request, HttpServletResponse response, 
			QualitySupervisonInfo qualitySupervisonInfo,
			RedirectAttributes redirectAttributes) {
		
		try {
			ProSimpleInfo proSimpleInfo = proSimpleInfoService.get(qualitySupervisonInfo.getProSimpleInfo().getId());
			String filename = proSimpleInfo.getProjectSimpleName() + "_监理单位信息_" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			
			String fujian = "附表   5-1：";
			String title = "高速公路监理单位信息表";
			String projectName = "项目名称：" 
						+ proSimpleInfo.getProjectSimpleName();
			
			String[] tableTitle = {"监理合同段编号", "监理单位名称", "所管辖合同段", "起讫桩号", "监理服务费（万元）", "监理工程造价（万元）", "监理合同的开始时间、结束时间", "负责人", "备注"};
			                         
			int[] colLength = {100, 180, 106, 106, 72, 72, 106, 48, 80}; 		// 每一列所占宽度
			
			// 获取Data
			qualitySupervisonInfo.setProSimpleInfo(proSimpleInfo);
			List<QualitySupervisonInfo> list = qualitySupervisonInfoService.findList(qualitySupervisonInfo);
			
			String[][] data = null;
			if(list != null && list.size() > 0){
				data = new String[list.size()][colLength.length];
				for (int i = 0; i < list.size(); i++) {
					qualitySupervisonInfo = list.get(i);
					String[] str = new String[9];
					str[0] = qualitySupervisonInfo.getContractSectionLabel();
					str[1] = qualitySupervisonInfo.getSupervisionName();
					str[2] = qualitySupervisonInfo.getHaveContractSectionLabel();
					str[3] = qualitySupervisonInfo.getStartingStation() + " - " + qualitySupervisonInfo.getEndingStation();
					str[4] = String.valueOf(qualitySupervisonInfo.getSupervisionServiceFee());
					str[5] = String.valueOf(qualitySupervisonInfo.getSupervisionProjectCosts());
					
					if(qualitySupervisonInfo.getSupervisionContractEnd() == null){
						str[6] = DateUtils.formatDate(qualitySupervisonInfo.getSupervisionContractStart(), "yyyy-MM-dd"); 
					} else {
						str[6] = DateUtils.formatDate(qualitySupervisonInfo.getSupervisionContractStart(), "yyyy-MM-dd") 
								   + " - " + DateUtils.formatDate(qualitySupervisonInfo.getSupervisionContractEnd(), "yyyy-MM-dd");
					}
					
					str[7] = qualitySupervisonInfo.getPrincipal();
					str[8] = qualitySupervisonInfo.getRemarks() == null ? " " : qualitySupervisonInfo.getRemarks();
					data[i] = str;
				}
			} else {
				addMessage(redirectAttributes, "没有数据");
    			return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonInfo/?repage";
			}
			
			new ExportExcel(fujian, title, projectName, colLength.length, true)
					.setTableTitle(tableTitle)
					.setDataArray(data)
					.setColLength(colLength)
					.write(response, filename)
					.dispose();

			logger.debug("Export success.");
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		
		return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonInfo/?repage";
	}
	
	
	/**
	 * 导入监理单位数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("quality:qualitySupervisonInfo:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QualitySupervisonInfo> list = ei.getDataList(QualitySupervisonInfo.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			QualitySupervisonInfo qualitySupervisonInfo = new QualitySupervisonInfo();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						qualitySupervisonInfo = list.get(i);
						if (qualitySupervisonInfo.getProjectId() != null && !qualitySupervisonInfo.getProjectId().equals("")){
							ProSimpleInfo projectInfo = new ProSimpleInfo();
							projectInfo.setProjectSimpleName(qualitySupervisonInfo.getProjectId());
							List<ProSimpleInfo> proList = proSimpleInfoService.findAllProjectNames(projectInfo);
							if(proList != null && proList.size() > 0){
								qualitySupervisonInfo.setProSimpleInfo(proList.get(0));
								qualitySupervisonInfo.setContractSectionType("1");
								qualitySupervisonInfo.setCreateBy(user);
								qualitySupervisonInfo.setUpdateBy(user);
								qualitySupervisonInfo.setCreateDate(now);
								qualitySupervisonInfo.setUpdateDate(now);
								qualitySupervisonInfoService.save(qualitySupervisonInfo);
								successNum++;
							} else {
								failureMsg.append("<br/>项目" + qualitySupervisonInfo.getProjectId() + " 不存在;");
							}
						}
						
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>监理单位 " + qualitySupervisonInfo.getProjectId() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>项目 " + qualitySupervisonInfo.getProjectId() + " 导入失败："+ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条监理单位信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条监理单位信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonInfo/?repage";
    }
	
	
	
	/**
	 * 下载导入建设单位用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("quality:qualitySupervisonInfo:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "监理单位信息导入模板.xlsx";
    		List<QualitySupervisonInfo> list = Lists.newArrayList(); 
    		list.add(new QualitySupervisonInfo());
    		new ExportExcel("监理单位信息", QualitySupervisonInfo.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonInfo/?repage";
    }
	
	
}