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
import com.platform.modules.project.entity.ProSimpleStructureTunnel;
import com.platform.modules.project.service.ProSimpleInfoService;
import com.platform.modules.project.service.ProSimpleStructureTunnelService;

/**
 * 主要结构物隧道Controller
 * @author Mr.Jia
 * @version 2017-12-08
 */
@Controller
@RequestMapping(value = "${adminPath}/project/proSimpleStructureTunnel")
public class ProSimpleStructureTunnelController extends BaseController {

	@Autowired
	private ProSimpleStructureTunnelService proSimpleStructureTunnelService;
	
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public ProSimpleStructureTunnel get(@RequestParam(required=false) String id) {
		ProSimpleStructureTunnel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = proSimpleStructureTunnelService.get(id);
		}
		if (entity == null){
			entity = new ProSimpleStructureTunnel();
		}
		return entity;
	}
	
	@RequiresPermissions("project:proSimpleStructureTunnel:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProSimpleStructureTunnel proSimpleStructureTunnel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProSimpleStructureTunnel> page = proSimpleStructureTunnelService.findPage(new Page<ProSimpleStructureTunnel>(request, response), proSimpleStructureTunnel); 
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("page", page);
		return "modules/project/proSimpleStructureTunnelList";
	}

	@RequiresPermissions("project:proSimpleStructureTunnel:view")
	@RequestMapping(value = "form")
	public String form(ProSimpleStructureTunnel proSimpleStructureTunnel, Model model) {
		if(proSimpleStructureTunnel.getId() != null && !"".equals(proSimpleStructureTunnel.getId())){
			String constractSectionType = proSimpleStructureTunnel.getContractSectionType();
			if(Constant.getContractSectionType(constractSectionType).length() > 0){
				proSimpleStructureTunnel.setContractSectionLabel(
						proSimpleStructureTunnel.getContractSectionLabel().substring(3).toString());
			}
		}
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("proSimpleStructureTunnel", proSimpleStructureTunnel);
		return "modules/project/proSimpleStructureTunnelForm";
	}

	@RequiresPermissions("project:proSimpleStructureTunnel:edit")
	@RequestMapping(value = "save")
	public String save(ProSimpleStructureTunnel proSimpleStructureTunnel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, proSimpleStructureTunnel)){
			return form(proSimpleStructureTunnel, model);
		}
		String contractSectionType = Constant.getContractSectionType(
				proSimpleStructureTunnel.getContractSectionType());
		proSimpleStructureTunnel.setContractSectionLabel(contractSectionType + proSimpleStructureTunnel.getContractSectionLabel());
		proSimpleStructureTunnelService.save(proSimpleStructureTunnel);
		addMessage(redirectAttributes, "保存主要结构物隧道成功");
		return "redirect:"+Global.getAdminPath()+"/project/proSimpleStructureTunnel/?repage";
	}
	
	@RequiresPermissions("project:proSimpleStructureTunnel:edit")
	@RequestMapping(value = "delete")
	public String delete(ProSimpleStructureTunnel proSimpleStructureTunnel, RedirectAttributes redirectAttributes) {
		proSimpleStructureTunnelService.delete(proSimpleStructureTunnel);
		addMessage(redirectAttributes, "删除主要结构物隧道成功");
		return "redirect:"+Global.getAdminPath()+"/project/proSimpleStructureTunnel/?repage";
	}

	
	@RequiresPermissions("project:proSimpleStructureTunnel:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String export(HttpServletRequest request, HttpServletResponse response, ProSimpleStructureTunnel proSimpleStructureTunnel,
			RedirectAttributes redirectAttributes) {
		
		try {
			ProSimpleInfo proSimpleInfo = proSimpleInfoService.get(proSimpleStructureTunnel.getProSimpleInfo().getId());
			String filename = proSimpleInfo.getProjectSimpleName() + "_结构物隧道工程_" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			
			String fujian = "附表   2-3：";
			String title = "高速公路项目主要结构物（隧道工程）工程信息表";
			String projectName = "项目名称：" + proSimpleInfo.getProjectSimpleName();
			
			String[] tableTitle = {"序号", "合同段编号", "结构物名称", "桩号", "长度（m）", "结构形式", "工程主要技术特点、难点", "备注"};
			int[] colLength = {36, 144, 144, 144, 72, 80, 150, 100}; 		// 每一列所占宽度
			
			// 获取Data
			proSimpleStructureTunnel.setProSimpleInfo(proSimpleInfo);
			List<ProSimpleStructureTunnel> list = proSimpleStructureTunnelService.findList(proSimpleStructureTunnel);
			
			String[][] data = null;
			if(list != null && list.size() > 0){
				data = new String[list.size()][colLength.length];
				for (int i = 0; i < list.size(); i++) {
					proSimpleStructureTunnel = list.get(i);
					String[] str = new String[8];
					str[0] = String.valueOf(i+1);
					str[1] = proSimpleStructureTunnel.getContractSectionLabel();
					str[2] = proSimpleStructureTunnel.getStructureName();
					str[3] = proSimpleStructureTunnel.getStake();
					str[4] = proSimpleStructureTunnel.getLength();
					str[5] = proSimpleStructureTunnel.getStructureType();
					str[6] = proSimpleStructureTunnel.getTechnicalCharacter();
					str[7] = proSimpleStructureTunnel.getRemarks();
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