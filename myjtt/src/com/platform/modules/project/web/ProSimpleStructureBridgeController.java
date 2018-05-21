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
import com.platform.modules.project.entity.ProSimpleStructureBridge;
import com.platform.modules.project.service.ProSimpleInfoService;
import com.platform.modules.project.service.ProSimpleStructureBridgeService;

/**
 * 主要结构物桥梁Controller
 * @author Mr.Jia
 * @version 2017-12-08
 */
@Controller
@RequestMapping(value = "${adminPath}/project/proSimpleStructureBridge")
public class ProSimpleStructureBridgeController extends BaseController {

	@Autowired
	private ProSimpleStructureBridgeService proSimpleStructureBridgeService;
	
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public ProSimpleStructureBridge get(@RequestParam(required=false) String id) {
		ProSimpleStructureBridge entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = proSimpleStructureBridgeService.get(id);
		}
		if (entity == null){
			entity = new ProSimpleStructureBridge();
		}
		return entity;
	}
	
	@RequiresPermissions("project:proSimpleStructureBridge:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProSimpleStructureBridge proSimpleStructureBridge, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProSimpleStructureBridge> page = proSimpleStructureBridgeService.findPage(new Page<ProSimpleStructureBridge>(request, response), proSimpleStructureBridge);
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("page", page);
		return "modules/project/proSimpleStructureBridgeList";
	}

	@RequiresPermissions("project:proSimpleStructureBridge:view")
	@RequestMapping(value = "form")
	public String form(ProSimpleStructureBridge proSimpleStructureBridge, Model model) {
		if(proSimpleStructureBridge.getId() != null && !"".equals(proSimpleStructureBridge.getId())){
			String constractSectionType = proSimpleStructureBridge.getContractSectionType();
			if(Constant.getContractSectionType(constractSectionType).length() > 0){
				proSimpleStructureBridge.setContractSectionLabel(
						proSimpleStructureBridge.getContractSectionLabel().substring(3).toString());
			}
		}
		
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("proSimpleStructureBridge", proSimpleStructureBridge);
		return "modules/project/proSimpleStructureBridgeForm";
	}

	@RequiresPermissions("project:proSimpleStructureBridge:edit")
	@RequestMapping(value = "save")
	public String save(ProSimpleStructureBridge proSimpleStructureBridge, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, proSimpleStructureBridge)){
			return form(proSimpleStructureBridge, model);
		}

		String contractSectionType = Constant.getContractSectionType(
				proSimpleStructureBridge.getContractSectionType());
		proSimpleStructureBridge.setContractSectionLabel(contractSectionType + proSimpleStructureBridge.getContractSectionLabel());
		proSimpleStructureBridgeService.save(proSimpleStructureBridge);
		addMessage(redirectAttributes, "保存主要结构物桥梁成功");
		return "redirect:"+Global.getAdminPath()+"/project/proSimpleStructureBridge/?repage";
	}
	
	@RequiresPermissions("project:proSimpleStructureBridge:edit")
	@RequestMapping(value = "delete")
	public String delete(ProSimpleStructureBridge proSimpleStructureBridge, RedirectAttributes redirectAttributes) {
		proSimpleStructureBridgeService.delete(proSimpleStructureBridge);
		addMessage(redirectAttributes, "删除主要结构物桥梁成功");
		return "redirect:"+Global.getAdminPath()+"/project/proSimpleStructureBridge/?repage";
	}
	
	
	@RequiresPermissions("project:proSimpleSpecialSubgrade:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String export(HttpServletRequest request, HttpServletResponse response, ProSimpleStructureBridge proSimpleStructureBridge,
			RedirectAttributes redirectAttributes) {
		
		try {
			ProSimpleInfo proSimpleInfo = proSimpleInfoService.get(proSimpleStructureBridge.getProSimpleInfo().getId());
			String filename = proSimpleInfo.getProjectSimpleName() + "_结构物桥梁工程_" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			
			String fujian = "附表   2-2：";
			String title = "高速公路项目主要结构物（桥梁工程）工程信息表";
			String projectName = "项目名称：" + proSimpleInfo.getProjectSimpleName();
			
			String[] tableTitle = {"序号", "合同段编号", "结构物名称", "桩号", "长度（m）", "结构形式", "工程主要技术特点、难点", "备注"};
			int[] colLength = {36, 144, 144, 144, 72, 80, 150, 100}; 		// 每一列所占宽度
			
			// 获取Data
			proSimpleStructureBridge.setProSimpleInfo(proSimpleInfo);
			List<ProSimpleStructureBridge> list = proSimpleStructureBridgeService.findList(proSimpleStructureBridge);
			
			String[][] data = null;
			if(list != null && list.size() > 0){
				data = new String[list.size()][colLength.length];
				for (int i = 0; i < list.size(); i++) {
					proSimpleStructureBridge = list.get(i);
					String[] str = new String[8];
					str[0] = String.valueOf(i+1);
					str[1] = proSimpleStructureBridge.getContractSectionLabel();
					str[2] = proSimpleStructureBridge.getStructureName();
					str[3] = proSimpleStructureBridge.getStake();
					str[4] = proSimpleStructureBridge.getLength();
					str[5] = proSimpleStructureBridge.getStructureType();
					str[6] = proSimpleStructureBridge.getTechnicalCharacter();
					str[7] = proSimpleStructureBridge.getRemarks();
					data[i] = str;
				}
			} else {
				addMessage(redirectAttributes, "没有数据");
    			return "redirect:"+Global.getAdminPath()+"/project/proSimpleStructureBridge/?repage";
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
		
		return "redirect:"+Global.getAdminPath()+"/project/proSimpleStructureBridge/?repage";
	}

}