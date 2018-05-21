package com.platform.modules.project.web;

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

import com.platform.common.config.Constant;
import com.platform.common.config.Global;
import com.platform.common.persistence.Page;
import com.platform.common.web.BaseController;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.StringUtils;
import com.platform.common.utils.excel.ExportExcel;
import com.platform.modules.project.entity.ProSimpleInfo;
import com.platform.modules.project.entity.ProSimpleSpecialSubgrade;
import com.platform.modules.project.service.ProSimpleInfoService;
import com.platform.modules.project.service.ProSimpleSpecialSubgradeService;

/**
 * 项目特殊路基工程Controller
 * @author Mr.Jiar
 * @version 2017-12-08
 */
@Controller
@RequestMapping(value = "${adminPath}/project/proSimpleSpecialSubgrade")
public class ProSimpleSpecialSubgradeController extends BaseController {

	@Autowired
	private ProSimpleSpecialSubgradeService proSimpleSpecialSubgradeService;
	
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public ProSimpleSpecialSubgrade get(@RequestParam(required=false) String id) {
		ProSimpleSpecialSubgrade entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = proSimpleSpecialSubgradeService.get(id);
		}
		if (entity == null){
			entity = new ProSimpleSpecialSubgrade();
		}
		return entity;
	}
	
	@RequiresPermissions("project:proSimpleSpecialSubgrade:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProSimpleSpecialSubgrade proSimpleSpecialSubgrade, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProSimpleSpecialSubgrade> page = proSimpleSpecialSubgradeService.findPage(new Page<ProSimpleSpecialSubgrade>(request, response), proSimpleSpecialSubgrade); 
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("page", page);
		return "modules/project/proSimpleSpecialSubgradeList";
	}

	@RequiresPermissions("project:proSimpleSpecialSubgrade:view")
	@RequestMapping(value = "form")
	public String form(ProSimpleSpecialSubgrade proSimpleSpecialSubgrade, Model model) {
		if(proSimpleSpecialSubgrade.getId() != null && !"".equals(proSimpleSpecialSubgrade.getId())){
			String constractSectionType = proSimpleSpecialSubgrade.getContractSectionType();
			if(Constant.getContractSectionType(constractSectionType).length() > 0){
				proSimpleSpecialSubgrade.setContractSectionLabel(
						proSimpleSpecialSubgrade.getContractSectionLabel().substring(3).toString());
			}
		}
		
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("proSimpleSpecialSubgrade", proSimpleSpecialSubgrade);
		return "modules/project/proSimpleSpecialSubgradeForm";
	}

	@RequiresPermissions("project:proSimpleSpecialSubgrade:edit")
	@RequestMapping(value = "save")
	public String save(ProSimpleSpecialSubgrade proSimpleSpecialSubgrade, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, proSimpleSpecialSubgrade)){
			return form(proSimpleSpecialSubgrade, model);
		}
		String contractSectionType = Constant.getContractSectionType(
				proSimpleSpecialSubgrade.getContractSectionType());
		proSimpleSpecialSubgrade.setContractSectionLabel(contractSectionType + proSimpleSpecialSubgrade.getContractSectionLabel());
		proSimpleSpecialSubgradeService.save(proSimpleSpecialSubgrade);
		addMessage(redirectAttributes, "保存项目特殊路基工程成功");
		return "redirect:"+Global.getAdminPath()+"/project/proSimpleSpecialSubgrade/?repage";
	}
	
	@RequiresPermissions("project:proSimpleSpecialSubgrade:edit")
	@RequestMapping(value = "delete")
	public String delete(ProSimpleSpecialSubgrade proSimpleSpecialSubgrade, RedirectAttributes redirectAttributes) {
		proSimpleSpecialSubgradeService.delete(proSimpleSpecialSubgrade);
		addMessage(redirectAttributes, "删除项目特殊路基工程成功");
		return "redirect:"+Global.getAdminPath()+"/project/proSimpleSpecialSubgrade/?repage";
	}

	
	@RequiresPermissions("project:proSimpleSpecialSubgrade:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String export(HttpServletRequest request, HttpServletResponse response, ProSimpleSpecialSubgrade proSimpleSpecialSubgrade,
			RedirectAttributes redirectAttributes) {
		
		try {
			ProSimpleInfo proSimpleInfo = proSimpleInfoService.get(proSimpleSpecialSubgrade.getProSimpleInfo().getId());
			String filename = proSimpleInfo.getProjectSimpleName() + "_特殊路基工程信息_" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			
			String fujian = "附表   2-1：";
			String title = "高速公路项目特殊路基工程信息表";
			String projectName = "项目名称：" + proSimpleInfo.getProjectSimpleName();
			String footer = "注：特殊路基指V型沟、不良路段、高填方等。";
			
			String[] tableTitle = {"序号", "合同段编号", "特殊路基名称", "段落桩号", "长度（m）", "处理方案", "备注"};
			int[] colLength = {36, 144, 144, 144, 72, 230, 100}; 		// 每一列所占宽度
			
			// 获取Data
			proSimpleSpecialSubgrade.setProSimpleInfo(proSimpleInfo);
			List<ProSimpleSpecialSubgrade> list = proSimpleSpecialSubgradeService.findList(proSimpleSpecialSubgrade);
			
			String[][] data = null;
			if(list != null && list.size() > 0){
				data = new String[list.size()][colLength.length];
				for (int i = 0; i < list.size(); i++) {
					proSimpleSpecialSubgrade = list.get(i);
					String[] str = new String[7];
					str[0] = String.valueOf(i+1);
					str[1] = proSimpleSpecialSubgrade.getContractSectionLabel();
					str[2] = proSimpleSpecialSubgrade.getSpecialSubgradeName();
					str[3] = proSimpleSpecialSubgrade.getStake();
					str[4] = proSimpleSpecialSubgrade.getLength();
					str[5] = proSimpleSpecialSubgrade.getProcessingPlan();
					str[6] = proSimpleSpecialSubgrade.getRemarks();
					data[i] = str;
				}
			} else {
				addMessage(redirectAttributes, "没有数据");
    			return "redirect:"+Global.getAdminPath()+"/project/proSimpleSpecialSubgrade/?repage";
			}
			
			new ExportExcel(fujian, title, projectName, colLength.length, true)
					.setTableTitle(tableTitle)
					.setDataArray(data)
					.setColLength(colLength)
					.setFooter(footer, colLength.length)
					.write(response, filename)
					.dispose();

			logger.debug("Export success.");
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		
		return "redirect:"+Global.getAdminPath()+"/project/proSimpleSpecialSubgrade/?repage";
	}
	
	
}