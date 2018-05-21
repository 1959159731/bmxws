package com.platform.modules.quality.web;

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
import com.platform.modules.quality.entity.QualityTestroomRemarkUserinfo;
import com.platform.modules.quality.service.QualityTestroomRemarkUserinfoService;

/**
 * 试验室人员备案信息Controller
 * @author Mr.Jia
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/quality/qualityTestroomRemarkUserinfo")
public class QualityTestroomRemarkUserinfoController extends BaseController {

	@Autowired
	private QualityTestroomRemarkUserinfoService qualityTestroomRemarkUserinfoService;
	
	@ModelAttribute
	public QualityTestroomRemarkUserinfo get(@RequestParam(required=false) String id) {
		QualityTestroomRemarkUserinfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qualityTestroomRemarkUserinfoService.get(id);
		}
		if (entity == null){
			entity = new QualityTestroomRemarkUserinfo();
		}
		return entity;
	}
	
	@RequiresPermissions("quality:qualityTestroomRemarkUserinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(QualityTestroomRemarkUserinfo qualityTestroomRemarkUserinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QualityTestroomRemarkUserinfo> page = qualityTestroomRemarkUserinfoService.findPage(new Page<QualityTestroomRemarkUserinfo>(request, response), qualityTestroomRemarkUserinfo); 
		model.addAttribute("page", page);
		return "modules/quality/qualityTestroomRemarkUserinfoList";
	}

	@RequiresPermissions("quality:qualityTestroomRemarkUserinfo:view")
	@RequestMapping(value = "form")
	public String form(QualityTestroomRemarkUserinfo qualityTestroomRemarkUserinfo, Model model) {
		model.addAttribute("qualityTestroomRemarkUserinfo", qualityTestroomRemarkUserinfo);
		return "modules/quality/qualityTestroomRemarkUserinfoForm";
	}

	@RequiresPermissions("quality:qualityTestroomRemarkUserinfo:edit")
	@RequestMapping(value = "save")
	public String save(QualityTestroomRemarkUserinfo qualityTestroomRemarkUserinfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, qualityTestroomRemarkUserinfo)){
			return form(qualityTestroomRemarkUserinfo, model);
		}
		qualityTestroomRemarkUserinfoService.save(qualityTestroomRemarkUserinfo);
		addMessage(redirectAttributes, "保存试验室人员备案信息成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualityTestroomRemarkUserinfo/?repage";
	}
	
	@RequiresPermissions("quality:qualityTestroomRemarkUserinfo:edit")
	@RequestMapping(value = "delete")
	public String delete(QualityTestroomRemarkUserinfo qualityTestroomRemarkUserinfo, RedirectAttributes redirectAttributes) {
		qualityTestroomRemarkUserinfoService.delete(qualityTestroomRemarkUserinfo);
		addMessage(redirectAttributes, "删除试验室人员备案信息成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualityTestroomRemarkUserinfo/?repage";
	}

}