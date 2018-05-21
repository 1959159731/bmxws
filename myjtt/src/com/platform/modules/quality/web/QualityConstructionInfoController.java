package com.platform.modules.quality.web;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.platform.common.beanvalidator.BeanValidators;
import com.platform.common.config.Global;
import com.platform.common.persistence.Page;
import com.platform.common.web.BaseController;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.StringUtils;
import com.platform.common.utils.excel.ExportExcel;
import com.platform.common.utils.excel.ImportExcel;
import com.platform.modules.project.entity.ProSimpleInfo;
import com.platform.modules.project.service.ProSimpleInfoService;
import com.platform.modules.quality.entity.QualityConstructionInfo;
import com.platform.modules.quality.service.QualityConstructionInfoService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.UserUtils;

/**
 * 施工单位信息Controller
 * @author Mr.Jia
 * @version 2017-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/quality/qualityConstructionInfo")
public class QualityConstructionInfoController extends BaseController {

	@Autowired
	private QualityConstructionInfoService qualityConstructionInfoService;
	
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public QualityConstructionInfo get(@RequestParam(required=false) String id) {
		QualityConstructionInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qualityConstructionInfoService.get(id);
		}
		if (entity == null){
			entity = new QualityConstructionInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("quality:qualityConstructionInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(QualityConstructionInfo qualityConstructionInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QualityConstructionInfo> page = qualityConstructionInfoService.findPage(new Page<QualityConstructionInfo>(request, response), qualityConstructionInfo); 
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("page", page);
		return "modules/quality/qualityConstructionInfoList";
	}

	@RequiresPermissions("quality:qualityConstructionInfo:view")
	@RequestMapping(value = "form")
	public String form(QualityConstructionInfo qualityConstructionInfo, Model model) {
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("qualityConstructionInfo", qualityConstructionInfo);
		return "modules/quality/qualityConstructionInfoForm";
	}

	@RequiresPermissions("quality:qualityConstructionInfo:edit")
	@RequestMapping(value = "save")
	public String save(QualityConstructionInfo qualityConstructionInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, qualityConstructionInfo)){
			return form(qualityConstructionInfo, model);
		}
		qualityConstructionInfoService.save(qualityConstructionInfo);
		addMessage(redirectAttributes, "保存施工单位信息成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualityConstructionInfo/?repage";
	}
	
	@RequiresPermissions("quality:qualityConstructionInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(QualityConstructionInfo qualityConstructionInfo, RedirectAttributes redirectAttributes) {
		qualityConstructionInfoService.delete(qualityConstructionInfo);
		addMessage(redirectAttributes, "删除施工单位信息成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualityConstructionInfo/?repage";
	}

	
	
	@RequiresPermissions("quality:qualityConstructionInfo:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String export(HttpServletRequest request, HttpServletResponse response, 
			QualityConstructionInfo qualityConstructionInfo,
			RedirectAttributes redirectAttributes) {
		
		try {
			ProSimpleInfo proSimpleInfo = proSimpleInfoService.get(qualityConstructionInfo.getProSimpleInfo().getId());
			String filename = proSimpleInfo.getProjectSimpleName() + "_施工单位信息_" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			
			String fujian = "附表   4-1：";
			String title = "高速公路施工单位信息表";
			String projectName = "项目名称：" 
						+ proSimpleInfo.getProjectSimpleName();
			
			String[][] tableTitle = { {"合同段编号", "施工单位名称（全称）", "起讫桩号", "合同价（万元）", "主要工程数量", "主要人员姓名", "", "", "", "", "", ""},
									{"", "", "", "", "", "项目经理","项目总工", "工程部长", "质检部长", "质量总监", "安全总监", "试验室主任"} };	
			                         
			int[] colLength = {100, 160, 150, 70, 50, 48, 48, 48, 48, 48, 48, 48}; 		// 每一列所占宽度
			
			// 获取Data
			qualityConstructionInfo.setProSimpleInfo(proSimpleInfo);
			List<QualityConstructionInfo> list = qualityConstructionInfoService.findList(qualityConstructionInfo);
			
			String[][] data = null;
			if(list != null && list.size() > 0){
				data = new String[list.size()][colLength.length];
				for (int i = 0; i < list.size(); i++) {
					qualityConstructionInfo = list.get(i);
					String[] str = new String[12];
					str[0] = qualityConstructionInfo.getContractSectionLabel();
					str[1] = qualityConstructionInfo.getConstructionName();
					str[2] = qualityConstructionInfo.getStartingStation() + " - " + qualityConstructionInfo.getEndingStation();
					str[3] = String.valueOf(qualityConstructionInfo.getContractPrice());
					str[4] = String.valueOf(qualityConstructionInfo.getMajorProNum());
					str[5] = qualityConstructionInfo.getProjectManager();
					str[6] = qualityConstructionInfo.getProjectChiefEngineer();
					str[7] = qualityConstructionInfo.getEngineeringMinister();
					str[8] = qualityConstructionInfo.getQc();
					str[9] = qualityConstructionInfo.getQualitySupervisor();
					str[10] = qualityConstructionInfo.getSafetyDirector();
					str[11] = qualityConstructionInfo.getTestDirector();
					data[i] = str;
				}
			} else {
				addMessage(redirectAttributes, "没有数据");
    			return "redirect:"+Global.getAdminPath()+"/quality/qualityConstructionInfo/?repage";
			}
			
			new ExportExcel(fujian, title, projectName, colLength.length, true)
					.setTableTitleForConstruction(tableTitle)
					.setDataArray(data)
					.setColLength(colLength)
					.write(response, filename)
					.dispose();

			logger.debug("Export success.");
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		
		return "redirect:"+Global.getAdminPath()+"/quality/qualityConstructionInfo/?repage";
	}
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("quality:qualityConstructionInfo:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QualityConstructionInfo> list = ei.getDataList(QualityConstructionInfo.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			QualityConstructionInfo qualityConstructionInfo = new QualityConstructionInfo();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						qualityConstructionInfo = list.get(i);
						if (qualityConstructionInfo.getProjectId() != null && !qualityConstructionInfo.getProjectId().equals("")){
							ProSimpleInfo projectInfo = new ProSimpleInfo();
							projectInfo.setProjectSimpleName(qualityConstructionInfo.getProjectId());
							List<ProSimpleInfo> proList = proSimpleInfoService.findAllProjectNames(projectInfo);
							if(proList != null && proList.size() > 0){
								qualityConstructionInfo.setProSimpleInfo(proList.get(0));
								qualityConstructionInfo.setCreateBy(user);
								qualityConstructionInfo.setUpdateBy(user);
								qualityConstructionInfo.setCreateDate(now);
								qualityConstructionInfo.setUpdateDate(now);
								qualityConstructionInfoService.save(qualityConstructionInfo);
								successNum++;
							} else {
								failureMsg.append("<br/>项目" + qualityConstructionInfo.getProjectId() + " 不存在;");
							}
						}
						
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>项目 " + qualityConstructionInfo.getProjectId() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>项目 " + qualityConstructionInfo.getProjectId() + " 导入失败："+ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条施工单位信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条施工单位信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualityConstructionInfo/?repage";
    }
	
	
	
	/**
	 * 下载导入建设单位用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("quality:qualityConstructionInfo:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "施工单位信息数据导入模板.xlsx";
    		List<QualityConstructionInfo> list = Lists.newArrayList(); 
    		list.add(new QualityConstructionInfo());
    		new ExportExcel("施工单位信息", QualityConstructionInfo.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualityConstructionInfo/?repage";
    }
	
}