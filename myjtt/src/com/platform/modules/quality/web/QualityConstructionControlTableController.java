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
import com.platform.modules.quality.entity.QualityConstructionControlTable;
import com.platform.modules.quality.service.QualityConstructionControlTableService;
import com.platform.modules.quality.service.QualityConstructionInfoService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.UserUtils;

/**
 * 施工单位履约对照Controller
 * @author Mr.Jia
 * @version 2017-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/quality/qualityConstructionControlTable")
public class QualityConstructionControlTableController extends BaseController {

	@Autowired
	private QualityConstructionControlTableService qualityConstructionControlTableService;
	
	@Autowired
	private QualityConstructionInfoService qualityConstructionInfoService;
	
	@ModelAttribute
	public QualityConstructionControlTable get(@RequestParam(required=false) String id) {
		QualityConstructionControlTable entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qualityConstructionControlTableService.get(id);
		}
		if (entity == null){
			entity = new QualityConstructionControlTable();
		}
		return entity;
	}
	
	@RequiresPermissions("quality:qualityConstructionControlTable:view")
	@RequestMapping(value = {"list", ""})
	public String list(QualityConstructionControlTable qualityConstructionControlTable, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QualityConstructionControlTable> page = qualityConstructionControlTableService.findPage(new Page<QualityConstructionControlTable>(request, response), qualityConstructionControlTable); 
		List<String> consList = qualityConstructionInfoService.findAllCons();
		model.addAttribute("consList", consList);
		model.addAttribute("page", page);
		return "modules/quality/qualityConstructionControlTableList";
	}

	@RequiresPermissions("quality:qualityConstructionControlTable:view")
	@RequestMapping(value = "form")
	public String form(QualityConstructionControlTable qualityConstructionControlTable, Model model) {
		List<String> consList = qualityConstructionInfoService.findAllCons();
		model.addAttribute("consList", consList);
		model.addAttribute("qualityConstructionControlTable", qualityConstructionControlTable);
		return "modules/quality/qualityConstructionControlTableForm";
	}

	@RequiresPermissions("quality:qualityConstructionControlTable:edit")
	@RequestMapping(value = "save")
	public String save(QualityConstructionControlTable qualityConstructionControlTable, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, qualityConstructionControlTable)){
			return form(qualityConstructionControlTable, model);
		}
		qualityConstructionControlTableService.save(qualityConstructionControlTable);
		addMessage(redirectAttributes, "保存施工单位履约对照成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualityConstructionControlTable/?repage";
	}
	
	@RequiresPermissions("quality:qualityConstructionControlTable:edit")
	@RequestMapping(value = "delete")
	public String delete(QualityConstructionControlTable qualityConstructionControlTable, RedirectAttributes redirectAttributes) {
		qualityConstructionControlTableService.delete(qualityConstructionControlTable);
		addMessage(redirectAttributes, "删除施工单位履约对照成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualityConstructionControlTable/?repage";
	}

	
	
	@RequiresPermissions("quality:qualityConstructionControlTable:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String export(HttpServletRequest request, HttpServletResponse response, 
			QualityConstructionControlTable qualityConstructionControlTable,
			RedirectAttributes redirectAttributes) {
		
		try {
			String filename = qualityConstructionControlTable.getConstructionName() + "_施工单位履约对照表_" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			
			String fujian = "附表   4-2：";
			String title = "高速公路施工单位履约对照表";
			String projectName = "施工单位：" + qualityConstructionControlTable.getConstructionName()
							   + "              七名主要人员更换率："
							   + "              全部管理人员更换率：";
			String[][] tableTitle = { {"序号", "合同人员", "", "", "实际进场人员", "", "", "", "", "", "备注"},
									{"", "姓名", "岗位", "职称", "姓名", "岗位","职称", "身份证号码", "证书编号", "进场时间", ""} };	
			int[] colLength = {36, 53, 53, 53, 53, 53, 53, 144, 128, 100, 132}; 		// 每一列所占宽度
			
			// 获取Data
			List<QualityConstructionControlTable> list = qualityConstructionControlTableService.findList(qualityConstructionControlTable);
			
			String[][] data = null;
			if(list != null && list.size() > 0){
				data = new String[list.size()][colLength.length];
				for (int i = 0; i < list.size(); i++) {
					qualityConstructionControlTable = list.get(i);
					String[] str = new String[11];
					str[0] = String.valueOf(i + 1);
					str[1] = qualityConstructionControlTable.getContractUsername() == null ? "" : qualityConstructionControlTable.getContractUsername();
					str[2] = qualityConstructionControlTable.getContractPost() == null ? "" : qualityConstructionControlTable.getContractPost();
					str[3] = qualityConstructionControlTable.getContractJobTitle() == null ? "" : qualityConstructionControlTable.getContractJobTitle();
					str[4] = qualityConstructionControlTable.getIncomingUsername();
					str[5] = qualityConstructionControlTable.getIncomingPost();
					str[6] = qualityConstructionControlTable.getIncomingJobTitle();
					str[7] = qualityConstructionControlTable.getIncomingCid();
					str[8] = qualityConstructionControlTable.getIncomingCertificateNo();
					str[9] = DateUtils.formatDate(qualityConstructionControlTable.getIncomingApproachTime(), "yyyy-MM-dd");
					str[10] = qualityConstructionControlTable.getRemarks();
					data[i] = str;
				}
			} else {
				addMessage(redirectAttributes, "没有数据");
    			return "redirect:"+Global.getAdminPath()+"/quality/qualityConstructionControlTable/?repage";
			}
			
			new ExportExcel(fujian, title, projectName, colLength.length, true)
					.setTableTitleForConstructionControlTable(tableTitle)
					.setDataArray(data)
					.setColLength(colLength)
					.write(response, filename)
					.dispose();

			logger.debug("Export success.");
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		
		return "redirect:"+Global.getAdminPath()+"/quality/qualityConstructionControlTable/?repage";
	}
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("quality:qualityConstructionControlTable:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QualityConstructionControlTable> list = ei.getDataList(QualityConstructionControlTable.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			QualityConstructionControlTable qualityConstructionControlTable = new QualityConstructionControlTable();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						qualityConstructionControlTable = list.get(i);
						qualityConstructionControlTable.setCreateBy(user);
						qualityConstructionControlTable.setUpdateBy(user);
						qualityConstructionControlTable.setCreateDate(now);
						qualityConstructionControlTable.setUpdateDate(now);
						qualityConstructionControlTableService.save(qualityConstructionControlTable);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>施工单位履约对照信息导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>施工单位履约对照信息导入失败：" + ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条施工单位履约对照信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条施工单位履约对照信信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualityConstructionControlTable/?repage";
    }
	
	
	
	/**
	 * 下载导入建设单位用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("quality:qualityConstructionControlTable:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "施工单位履约对照信息数据导入模板.xlsx";
    		List<QualityConstructionControlTable> list = Lists.newArrayList(); 
    		list.add(new QualityConstructionControlTable());
    		new ExportExcel("施工单位履约对照信息", QualityConstructionControlTable.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualityConstructionControlTable/?repage";
    }
}