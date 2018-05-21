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
import com.platform.modules.quality.entity.QualityThirdCheckInfo;
import com.platform.modules.quality.service.QualityThirdCheckInfoService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.UserUtils;

/**
 * 第三方检测单位信息Controller
 * @author Mr.Jia
 * @version 2017-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/quality/qualityThirdCheckInfo")
public class QualityThirdCheckInfoController extends BaseController {

	@Autowired
	private QualityThirdCheckInfoService qualityThirdCheckInfoService;
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public QualityThirdCheckInfo get(@RequestParam(required=false) String id) {
		QualityThirdCheckInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qualityThirdCheckInfoService.get(id);
		}
		if (entity == null){
			entity = new QualityThirdCheckInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("quality:qualityThirdCheckInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(QualityThirdCheckInfo qualityThirdCheckInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QualityThirdCheckInfo> page = qualityThirdCheckInfoService.findPage(new Page<QualityThirdCheckInfo>(request, response), qualityThirdCheckInfo); 
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("page", page);
		return "modules/quality/qualityThirdCheckInfoList";
	}

	@RequiresPermissions("quality:qualityThirdCheckInfo:view")
	@RequestMapping(value = "form")
	public String form(QualityThirdCheckInfo qualityThirdCheckInfo, Model model) {
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("qualityThirdCheckInfo", qualityThirdCheckInfo);
		return "modules/quality/qualityThirdCheckInfoForm";
	}

	@RequiresPermissions("quality:qualityThirdCheckInfo:edit")
	@RequestMapping(value = "save")
	public String save(QualityThirdCheckInfo qualityThirdCheckInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, qualityThirdCheckInfo)){
			return form(qualityThirdCheckInfo, model);
		}
		qualityThirdCheckInfoService.save(qualityThirdCheckInfo);
		addMessage(redirectAttributes, "保存第三方检测单位信息成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualityThirdCheckInfo/?repage";
	}
	
	@RequiresPermissions("quality:qualityThirdCheckInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(QualityThirdCheckInfo qualityThirdCheckInfo, RedirectAttributes redirectAttributes) {
		qualityThirdCheckInfoService.delete(qualityThirdCheckInfo);
		addMessage(redirectAttributes, "删除第三方检测单位信息成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualityThirdCheckInfo/?repage";
	}

	
	@RequiresPermissions("quality:qualityThirdCheckInfo:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String export(HttpServletRequest request, HttpServletResponse response, 
			QualityThirdCheckInfo qualityThirdCheckInfo,
			RedirectAttributes redirectAttributes) {
		
		try {
			ProSimpleInfo proSimpleInfo = proSimpleInfoService.get(qualityThirdCheckInfo.getProSimpleInfo().getId());
			String filename = proSimpleInfo.getProjectSimpleName() + "_第三方检测单位信息_" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			
			String fujian = "附表   6-1：";
			String title = "高速公路第三方检测单位信息表";
			String projectName = "项目名称：" 
						+ proSimpleInfo.getProjectSimpleName();
			
			String[] tableTitle = {"序号", "检测内容", "检测单位", "所检测合同段", "检测单位负责人（联系电话）", "现场检测负责人（联系电话）"};
			int[] colLength = {36, 284, 150, 100, 150, 150}; 		// 每一列所占宽度
			
			// 获取Data
			qualityThirdCheckInfo.setProSimpleInfo(proSimpleInfo);
			List<QualityThirdCheckInfo> list = qualityThirdCheckInfoService.findList(qualityThirdCheckInfo);
			
			String[][] data = null;
			if(list != null && list.size() > 0){
				data = new String[list.size()][colLength.length];
				for (int i = 0; i < list.size(); i++) {
					qualityThirdCheckInfo = list.get(i);
					String[] str = new String[6];
					str[0] = String.valueOf(i+1);
					str[1] = qualityThirdCheckInfo.getContractSectionLabel();
					str[2] = qualityThirdCheckInfo.getDetectionUnit();
					str[3] = qualityThirdCheckInfo.getIndetectionContractSectionLabel();
					str[4] = qualityThirdCheckInfo.getInspectionLeader() + " - " + qualityThirdCheckInfo.getInspectionLeaderPhonenum();
					str[5] = qualityThirdCheckInfo.getOnsiteInspectionLeader() + " - " + qualityThirdCheckInfo.getOnsiteInspectionLeaderPhonenum();
					data[i] = str;
				}
			} else {
				addMessage(redirectAttributes, "没有数据");
    			return "redirect:"+Global.getAdminPath()+"/quality/qualityThirdCheckInfo/?repage";
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
		
		return "redirect:"+Global.getAdminPath()+"/quality/qualityThirdCheckInfo/?repage";
	}
	

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("quality:qualityThirdCheckInfo:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QualityThirdCheckInfo> list = ei.getDataList(QualityThirdCheckInfo.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			QualityThirdCheckInfo qualityThirdCheckInfo = new QualityThirdCheckInfo();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						qualityThirdCheckInfo = list.get(i);
						if (qualityThirdCheckInfo.getProjectId() != null && !qualityThirdCheckInfo.getProjectId().equals("")){
							ProSimpleInfo projectInfo = new ProSimpleInfo();
							projectInfo.setProjectSimpleName(qualityThirdCheckInfo.getProjectId());
							List<ProSimpleInfo> proList = proSimpleInfoService.findAllProjectNames(projectInfo);
							if(proList != null && proList.size() > 0){
								qualityThirdCheckInfo.setProSimpleInfo(proList.get(0));
								qualityThirdCheckInfo.setCreateBy(user);
								qualityThirdCheckInfo.setUpdateBy(user);
								qualityThirdCheckInfo.setCreateDate(now);
								qualityThirdCheckInfo.setUpdateDate(now);
								qualityThirdCheckInfoService.save(qualityThirdCheckInfo);
								successNum++;
							} else {
								failureMsg.append("<br/>项目" + qualityThirdCheckInfo.getProjectId() + " 不存在;");
							}
						}
						
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>项目 " + qualityThirdCheckInfo.getProjectId() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>项目 " + qualityThirdCheckInfo.getProjectId() + " 导入失败："+ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条第三方检测单位信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条第三方检测单位信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualityThirdCheckInfo/?repage";
    }
	
	
	
	/**
	 * 下载导入建设单位用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("quality:qualityThirdCheckInfo:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "第三方检测单位信息导入模板.xlsx";
    		List<QualityThirdCheckInfo> list = Lists.newArrayList(); 
    		list.add(new QualityThirdCheckInfo());
    		new ExportExcel("第三方检测单位信息", QualityThirdCheckInfo.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualityThirdCheckInfo/?repage";
    }
	
	
}