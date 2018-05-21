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
import com.platform.modules.quality.entity.QualitySupervisonControlTable;
import com.platform.modules.quality.service.QualitySupervisonControlTableService;
import com.platform.modules.quality.service.QualitySupervisonInfoService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.DictUtils;
import com.platform.modules.sys.utils.UserUtils;

/**
 * 监理单位进场人员信息Controller
 * @author Mr.Jia
 * @version 2017-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/quality/qualitySupervisonControlTable")
public class QualitySupervisonControlTableController extends BaseController {

	@Autowired
	private QualitySupervisonControlTableService qualitySupervisonControlTableService;
	
	@Autowired
	private QualitySupervisonInfoService qualitySupervisonInfoService;
	
	@ModelAttribute
	public QualitySupervisonControlTable get(@RequestParam(required=false) String id) {
		QualitySupervisonControlTable entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qualitySupervisonControlTableService.get(id);
		}
		if (entity == null){
			entity = new QualitySupervisonControlTable();
		}
		return entity;
	}
	
	@RequiresPermissions("quality:qualitySupervisonControlTable:view")
	@RequestMapping(value = {"list", ""})
	public String list(QualitySupervisonControlTable qualitySupervisonControlTable, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QualitySupervisonControlTable> page = qualitySupervisonControlTableService.findPage(new Page<QualitySupervisonControlTable>(request, response), qualitySupervisonControlTable); 
		List<String> consList = qualitySupervisonInfoService.findAllCons();
		model.addAttribute("supList", consList);
		model.addAttribute("page", page);
		return "modules/quality/qualitySupervisonControlTableList";
	}

	@RequiresPermissions("quality:qualitySupervisonControlTable:view")
	@RequestMapping(value = "form")
	public String form(QualitySupervisonControlTable qualitySupervisonControlTable, Model model) {
		List<String> consList = qualitySupervisonInfoService.findAllCons();
		model.addAttribute("supList", consList);
		model.addAttribute("qualitySupervisonControlTable", qualitySupervisonControlTable);
		return "modules/quality/qualitySupervisonControlTableForm";
	}

	@RequiresPermissions("quality:qualitySupervisonControlTable:edit")
	@RequestMapping(value = "save")
	public String save(QualitySupervisonControlTable qualitySupervisonControlTable, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, qualitySupervisonControlTable)){
			return form(qualitySupervisonControlTable, model);
		}
		qualitySupervisonControlTableService.save(qualitySupervisonControlTable);
		addMessage(redirectAttributes, "保存监理单位进场人员信息成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonControlTable/?repage";
	}
	
	@RequiresPermissions("quality:qualitySupervisonControlTable:edit")
	@RequestMapping(value = "delete")
	public String delete(QualitySupervisonControlTable qualitySupervisonControlTable, RedirectAttributes redirectAttributes) {
		qualitySupervisonControlTableService.delete(qualitySupervisonControlTable);
		addMessage(redirectAttributes, "删除监理单位进场人员信息成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonControlTable/?repage";
	}

	

	@RequiresPermissions("quality:qualitySupervisonControlTable:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String export(HttpServletRequest request, HttpServletResponse response, 
			QualitySupervisonControlTable qualitySupervisonControlTable,
			RedirectAttributes redirectAttributes) {
		
		try {
			String filename = qualitySupervisonControlTable.getSupervisonName() + "_监理合同人员与进场人员对照表_" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			
			String fujian = "附表   5-2：";
			String title = "高速公路监理合同人员与进场人员对照一览表";
			String projectName = "监理单位：" + qualitySupervisonControlTable.getSupervisonName();
			String[][] tableTitle = { {"序号", "合同人员", "", "", "实际进场人员", "", "", "", "", "", "", "备注"},
									{"", "姓名", "岗位", "职称", "姓名", "岗位","职称", "身份证号", "监理资格证书编号", "注册情况", "进场时间", ""} };	
			int[] colLength = {36, 53, 53, 53, 53, 53, 53, 144, 128, 80, 100, 52}; 		// 每一列所占宽度
			
			// 获取Data
			List<QualitySupervisonControlTable> list = qualitySupervisonControlTableService.findList(qualitySupervisonControlTable);
			
			String[][] data = null;
			if(list != null && list.size() > 0){
				data = new String[list.size()][colLength.length];
				for (int i = 0; i < list.size(); i++) {
					qualitySupervisonControlTable = list.get(i);
					String[] str = new String[12];
					str[0] = String.valueOf(i + 1);
					str[1] = qualitySupervisonControlTable.getContractUsername() == null ? "" : qualitySupervisonControlTable.getContractUsername();
					str[2] = qualitySupervisonControlTable.getContractPost() == null ? "" : qualitySupervisonControlTable.getContractPost();
					str[3] = qualitySupervisonControlTable.getContractJobTitle() == null ? "" : qualitySupervisonControlTable.getContractJobTitle();
					str[4] = qualitySupervisonControlTable.getIncomingUsername();
					str[5] = qualitySupervisonControlTable.getIncomingPost();
					str[6] = qualitySupervisonControlTable.getIncomingJobTitle();
					str[7] = qualitySupervisonControlTable.getIncomingCid();
					str[8] = qualitySupervisonControlTable.getIncomingCertificateNo();
					str[9] = DictUtils.getDictLabel(qualitySupervisonControlTable.getIsRegister(), "yes_no", "");
					str[10] = DateUtils.formatDate(qualitySupervisonControlTable.getIncomingApproachTime(), "yyyy-MM-dd");
					str[11] = qualitySupervisonControlTable.getRemarks();
					data[i] = str;
				}
			} else {
				addMessage(redirectAttributes, "没有数据");
    			return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonControlTable/?repage";
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
		
		return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonControlTable/?repage";
	}
	
	
	/**
	 * 导入监理单位人员对照信息
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("quality:qualitySupervisonControlTable:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QualitySupervisonControlTable> list = ei.getDataList(QualitySupervisonControlTable.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			QualitySupervisonControlTable qualitySupervisonControlTable = new QualitySupervisonControlTable();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						qualitySupervisonControlTable = list.get(i);
						qualitySupervisonControlTable.setCreateBy(user);
						qualitySupervisonControlTable.setUpdateBy(user);
						qualitySupervisonControlTable.setCreateDate(now);
						qualitySupervisonControlTable.setUpdateDate(now);
						qualitySupervisonControlTable.setIsRegister(qualitySupervisonControlTable.getIsRegister().equals("是")?"1":"0" );
						qualitySupervisonControlTableService.save(qualitySupervisonControlTable);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>监理单位人员对照信息导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>监理单位人员对照信息导入失败：" + ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条监理单位人员对照信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条监理单位人员对照信信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonControlTable/?repage";
    }
	
	
	
	/**
	 * 下载导入监理单位人员对照信息模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("quality:qualitySupervisonControlTable:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "监理单位人员对照信息数据导入模板.xlsx";
    		List<QualitySupervisonControlTable> list = Lists.newArrayList(); 
    		list.add(new QualitySupervisonControlTable());
    		new ExportExcel("监理单位人员对照信息", QualitySupervisonControlTable.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonControlTable/?repage";
    }
	
	
}