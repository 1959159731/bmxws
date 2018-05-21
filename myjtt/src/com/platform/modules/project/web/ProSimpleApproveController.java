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
import com.platform.modules.project.entity.ProSimpleApprove;
import com.platform.modules.project.service.ProSimpleApproveService;

/**
 * 项目基本信息-审批信息Controller
 * @author Mr.Jia
 * @version 2017-12-06
 */
@Controller
@RequestMapping(value = "${adminPath}/project/proSimpleApprove")
public class ProSimpleApproveController extends BaseController {

	@Autowired
	private ProSimpleApproveService proSimpleApproveService;
	
	@ModelAttribute
	public ProSimpleApprove get(@RequestParam(required=false) String id) {
		ProSimpleApprove entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = proSimpleApproveService.get(id);
		}
		if (entity == null){
			entity = new ProSimpleApprove();
		}
		return entity;
	}
	
	@RequiresPermissions("project:proSimpleApprove:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProSimpleApprove proSimpleApprove, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProSimpleApprove> page = proSimpleApproveService.findPage(new Page<ProSimpleApprove>(request, response), proSimpleApprove); 
		model.addAttribute("page", page);
		return "modules/project/proSimpleApproveList";
	}

	@RequiresPermissions("project:proSimpleApprove:view")
	@RequestMapping(value = "form")
	public String form(ProSimpleApprove proSimpleApprove, Model model) {
		model.addAttribute("proSimpleApprove", proSimpleApprove);
		return "modules/project/proSimpleApproveForm";
	}

	@RequiresPermissions("project:proSimpleApprove:edit")
	@RequestMapping(value = "save")
	public String save(ProSimpleApprove proSimpleApprove, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, proSimpleApprove)){
			return form(proSimpleApprove, model);
		}
		proSimpleApproveService.save(proSimpleApprove);
		addMessage(redirectAttributes, "保存项目基本信息-审批信息成功");
		return "redirect:"+Global.getAdminPath()+"/project/proSimpleApprove/?repage";
	}
	
	@RequiresPermissions("project:proSimpleApprove:edit")
	@RequestMapping(value = "delete")
	public String delete(ProSimpleApprove proSimpleApprove, RedirectAttributes redirectAttributes) {
		proSimpleApproveService.delete(proSimpleApprove);
		addMessage(redirectAttributes, "删除项目基本信息-审批信息成功");
		return "redirect:"+Global.getAdminPath()+"/project/proSimpleApprove/?repage";
	}

}