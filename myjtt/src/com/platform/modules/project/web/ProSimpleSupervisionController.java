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
import com.platform.modules.project.entity.ProSimpleSupervision;
import com.platform.modules.project.service.ProSimpleSupervisionService;

/**
 * 项目施工、监理单位信息Controller
 * @author Mr.Jia
 * @version 2017-12-06
 */
@Controller
@RequestMapping(value = "${adminPath}/project/proSimpleSupervision")
public class ProSimpleSupervisionController extends BaseController {

	@Autowired
	private ProSimpleSupervisionService proSimpleSupervisionService;
	
	@ModelAttribute
	public ProSimpleSupervision get(@RequestParam(required=false) String id) {
		ProSimpleSupervision entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = proSimpleSupervisionService.get(id);
		}
		if (entity == null){
			entity = new ProSimpleSupervision();
		}
		return entity;
	}
	
	@RequiresPermissions("project:proSimpleSupervision:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProSimpleSupervision proSimpleSupervision, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProSimpleSupervision> page = proSimpleSupervisionService.findPage(new Page<ProSimpleSupervision>(request, response), proSimpleSupervision); 
		model.addAttribute("page", page);
		return "modules/project/proSimpleSupervisionList";
	}

	@RequiresPermissions("project:proSimpleSupervision:view")
	@RequestMapping(value = "form")
	public String form(ProSimpleSupervision proSimpleSupervision, Model model) {
		model.addAttribute("proSimpleSupervision", proSimpleSupervision);
		return "modules/project/proSimpleSupervisionForm";
	}

	@RequiresPermissions("project:proSimpleSupervision:edit")
	@RequestMapping(value = "save")
	public String save(ProSimpleSupervision proSimpleSupervision, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, proSimpleSupervision)){
			return form(proSimpleSupervision, model);
		}
		proSimpleSupervisionService.save(proSimpleSupervision);
		addMessage(redirectAttributes, "保存项目施工、监理单位信息成功");
		return "redirect:"+Global.getAdminPath()+"/project/proSimpleSupervision/?repage";
	}
	
	@RequiresPermissions("project:proSimpleSupervision:edit")
	@RequestMapping(value = "delete")
	public String delete(ProSimpleSupervision proSimpleSupervision, RedirectAttributes redirectAttributes) {
		proSimpleSupervisionService.delete(proSimpleSupervision);
		addMessage(redirectAttributes, "删除项目施工、监理单位信息成功");
		return "redirect:"+Global.getAdminPath()+"/project/proSimpleSupervision/?repage";
	}

}