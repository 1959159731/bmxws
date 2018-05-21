package com.platform.modules.project.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.platform.common.config.Global;
import com.platform.common.persistence.Page;
import com.platform.common.web.BaseController;
import com.platform.common.utils.StringUtils;
import com.platform.modules.project.entity.ProSimpleBridgeTunnel;
import com.platform.modules.project.service.ProSimpleBridgeTunnelService;

/**
 * 项目桥梁、隧道信息Controller
 * @author Mr.Jia
 * @version 2017-12-06
 */
@Controller
@RequestMapping(value = "${adminPath}/project/proSimpleBridgeTunnel")
public class ProSimpleBridgeTunnelController extends BaseController {

	@Autowired
	private ProSimpleBridgeTunnelService proSimpleBridgeTunnelService;
	
	@ModelAttribute
	public ProSimpleBridgeTunnel get(@RequestParam(required=false) String id) {
		ProSimpleBridgeTunnel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = proSimpleBridgeTunnelService.get(id);
		}
		if (entity == null){
			entity = new ProSimpleBridgeTunnel();
		}
		return entity;
	}
	
	@RequiresPermissions("project:proSimpleBridgeTunnel:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProSimpleBridgeTunnel proSimpleBridgeTunnel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProSimpleBridgeTunnel> page = proSimpleBridgeTunnelService.findPage(new Page<ProSimpleBridgeTunnel>(request, response), proSimpleBridgeTunnel); 
		model.addAttribute("page", page);
		return "modules/project/proSimpleBridgeTunnelList";
	}

	@RequiresPermissions("project:proSimpleBridgeTunnel:view")
	@RequestMapping(value = "form")
	public String form(ProSimpleBridgeTunnel proSimpleBridgeTunnel, Model model) {
		model.addAttribute("proSimpleBridgeTunnel", proSimpleBridgeTunnel);
		return "modules/project/proSimpleBridgeTunnelForm";
	}

	@RequiresPermissions("project:proSimpleBridgeTunnel:edit")
	@RequestMapping(value = "save")
	public String save(ProSimpleBridgeTunnel proSimpleBridgeTunnel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, proSimpleBridgeTunnel)){
			return form(proSimpleBridgeTunnel, model);
		}
		proSimpleBridgeTunnelService.save(proSimpleBridgeTunnel);
		addMessage(redirectAttributes, "保存项目桥梁、隧道信息成功");
		return "redirect:"+Global.getAdminPath()+"/project/proSimpleBridgeTunnel/?repage";
	}
	
	@RequiresPermissions("project:proSimpleBridgeTunnel:edit")
	@RequestMapping(value = "delete")
	public String delete(ProSimpleBridgeTunnel proSimpleBridgeTunnel, RedirectAttributes redirectAttributes) {
		proSimpleBridgeTunnelService.delete(proSimpleBridgeTunnel);
		addMessage(redirectAttributes, "删除项目桥梁、隧道信息成功");
		return "redirect:"+Global.getAdminPath()+"/project/proSimpleBridgeTunnel/?repage";
	}

}