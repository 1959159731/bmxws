package com.platform.modules.quality.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.platform.common.config.Global;
import com.platform.common.persistence.Page;
import com.platform.common.web.BaseController;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.StringUtils;
import com.platform.common.utils.excel.ExportExcel;
import com.platform.modules.project.entity.ProSimpleInfo;
import com.platform.modules.project.service.ProSimpleInfoService;
import com.platform.modules.quality.entity.QualitySupervisonContractTable;
import com.platform.modules.quality.service.QualitySupervisonContractTableService;
import com.platform.modules.sys.utils.DictUtils;

/**
 * 监理单位履约对照Controller
 * @author Mr.Jia
 * @version 2017-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/quality/qualitySupervisonContractTable")
public class QualitySupervisonContractTableController extends BaseController {

	@Autowired
	private QualitySupervisonContractTableService qualitySupervisonContractTableService;
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public QualitySupervisonContractTable get(@RequestParam(required=false) String id) {
		QualitySupervisonContractTable entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qualitySupervisonContractTableService.get(id);
		}
		if (entity == null){
			entity = new QualitySupervisonContractTable();
		}
		return entity;
	}
	
	@RequiresPermissions("quality:qualitySupervisonContractTable:view")
	@RequestMapping(value = {"list", ""})
	public String list(QualitySupervisonContractTable qualitySupervisonContractTable, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QualitySupervisonContractTable> page = qualitySupervisonContractTableService.findPage(new Page<QualitySupervisonContractTable>(request, response), qualitySupervisonContractTable); 
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("page", page);
		return "modules/quality/qualitySupervisonContractTableList";
	}

	@RequiresPermissions("quality:qualitySupervisonContractTable:view")
	@RequestMapping(value = "form")
	public String form(QualitySupervisonContractTable qualitySupervisonContractTable, Model model) {
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("qualitySupervisonContractTable", qualitySupervisonContractTable);
		return "modules/quality/qualitySupervisonContractTableForm";
	}

	@RequiresPermissions("quality:qualitySupervisonContractTable:edit")
	@RequestMapping(value = "save")
	public String save(QualitySupervisonContractTable qualitySupervisonContractTable, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, qualitySupervisonContractTable)){
			return form(qualitySupervisonContractTable, model);
		}
		qualitySupervisonContractTableService.save(qualitySupervisonContractTable);
		addMessage(redirectAttributes, "保存监理单位履约对照成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonContractTable/?repage";
	}
	
	@RequiresPermissions("quality:qualitySupervisonContractTable:edit")
	@RequestMapping(value = "delete")
	public String delete(QualitySupervisonContractTable qualitySupervisonContractTable, RedirectAttributes redirectAttributes) {
		qualitySupervisonContractTableService.delete(qualitySupervisonContractTable);
		addMessage(redirectAttributes, "删除监理单位履约对照成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonContractTable/?repage";
	}
	
	
	
	@RequiresPermissions("quality:qualitySupervisonContractTable:edit")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String export(HttpServletRequest request, HttpServletResponse response, 
			QualitySupervisonContractTable qualitySupervisonContractTable,
			RedirectAttributes redirectAttributes) {
		try {
			String projectName = proSimpleInfoService.get(qualitySupervisonContractTable.getProSimpleInfo().getId()).getProjectSimpleName();
			String filename = projectName + "_监理单位合同履约汇总表_" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			String fujian = "附表   5-3：";
			String title = "高速公路监理单位合同履约汇总表";
			projectName = "项目名称：" + projectName;
			String[][] tableTitle = { {"监理单位", "合同人员", "", "", "实际进场人员", "", "", "", "部证更换人数", "培训证更换人数", "高驻是否更换", "更换率（%）", "备注"},
									{"", "合同总人数", "持部证人数", "持培训证人数", "实际总人数", "持部证人数","持培训证人数", "持证率（%）", "", "", "", "", ""} };	
			int[] colLength = {180, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 100 }; 		// 每一列所占宽度
			
			// 获取Data
			List<QualitySupervisonContractTable> list = qualitySupervisonContractTableService.findList(qualitySupervisonContractTable);
			String[][] data = null;
			if(list != null && list.size() > 0){
				data = new String[list.size()][colLength.length];
				for (int i = 0; i < list.size(); i++) {
					qualitySupervisonContractTable = list.get(i);
					String[] str = new String[13];
					str[0] = String.valueOf(qualitySupervisonContractTable.getSupervisionName());
					str[1] = String.valueOf(qualitySupervisonContractTable.getContractTotalNum());
					str[2] = String.valueOf(qualitySupervisonContractTable.getContractDepartNum());
					str[3] = String.valueOf(qualitySupervisonContractTable.getContractTrainingNum());
					str[4] = String.valueOf(qualitySupervisonContractTable.getIncomingTotalNum());
					str[5] = String.valueOf(qualitySupervisonContractTable.getIncomingDepartNum());
					str[6] = String.valueOf(qualitySupervisonContractTable.getIncomingTrainingNum());
					str[7] = String.valueOf(qualitySupervisonContractTable.getIncomingCertificateRate());
					str[8] = String.valueOf(qualitySupervisonContractTable.getDepartReplaceNum());
					str[9] = String.valueOf(qualitySupervisonContractTable.getTrainingReplaceNum());
					str[10] = DictUtils.getDictLabel(qualitySupervisonContractTable.getInhighIsReplaced(), "yes_no", ""); 
					str[11] = String.valueOf(qualitySupervisonContractTable.getReplcaedRate());
					str[12] = String.valueOf(qualitySupervisonContractTable.getRemarks());
					data[i] = str;
				}
			} else {
				addMessage(redirectAttributes, "没有数据");
    			return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonContractTable/?repage";
			}
			
			new ExportExcel(fujian, title, projectName, colLength.length, true)
					.setTableTitleForSupervisonContractTable(tableTitle)
					.setDataArray(data)
					.setColLength(colLength)
					.write(response, filename)
					.dispose();
			
			logger.debug("Export success.");
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualitySupervisonContractTable/?repage";
	}
	
	
	
}