package com.platform.modules.ssp.controller;

import java.io.File;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.platform.common.beanvalidator.BeanValidators;
import com.platform.common.config.Constant;
import com.platform.common.config.Global;
import com.platform.common.persistence.Page;
import com.platform.common.utils.FileUtils;
import com.platform.common.utils.StringUtils;
import com.platform.common.utils.excel.ImportExcel;
import com.platform.common.web.BaseController;
import com.platform.modules.project.entity.ProSimpleInfo;
import com.platform.modules.project.service.ProSimpleInfoService;
import com.platform.modules.safe.entity.SafeDangerProjectManager;
import com.platform.modules.safe.entity.SafeProManagerPerson;
import com.platform.modules.ssp.entity.QualitySafe;
import com.platform.modules.ssp.entity.QualitySafePhoto;
import com.platform.modules.ssp.service.QualitySafeService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.DictUtils;
import com.platform.modules.sys.utils.UserUtils;


/**
 * 质量隐患Controller
 * 
 */

@Controller
@RequestMapping(value = "${adminPath}/ssp/qualitySafe")
public class SSPController extends BaseController{
	
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	QualitySafeService qualitySafeService ;
	
	
//	@RequestMapping(value="qualitySafe")
//	public String getService(QualitySafe qualitySafe,HttpServletRequest request, HttpServletResponse response,Model model){
//	
//		Page<QualitySafe> page = qualitySafeService.findPage(new Page<QualitySafe>(request, response), qualitySafe);
//		List<QualitySafe>	list = qualitySafeService.findList(new QualitySafe());
//		model.addAttribute("list", list);
//		model.addAttribute("page", page);
//		return "modules/ssp/safeList";
//		List<QualitySafe> list = qualitySafeService.findList();
//		for(QualitySafe item:list){
//	          System.out.println(item);
//	    }
//		
//		ModelAndView mv = new ModelAndView("modules/ssp/abc");
//		
//		return mv;
//	}
	
	
	
	@ModelAttribute
	public QualitySafe get(@RequestParam(required=false) String id) {
		QualitySafe entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qualitySafeService.get(id);
		}
		if (entity == null){
			entity = new QualitySafe();
		}
		return entity;
	}
	
	@RequiresPermissions("ssp:qualitySafe:view")
	@RequestMapping(value = {"list", ""})
	public String list(QualitySafe qualitySafe, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		Page<QualitySafe>  page = qualitySafeService.findPage(new Page<QualitySafe>(request, response), qualitySafe);
		List<QualitySafe>  list = qualitySafeService.findList(new QualitySafe());
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		return "modules/ssp/safeList";
	}
	
	

	@RequiresPermissions("ssp:qualitySafe:view")
	@RequestMapping(value = "form")
	public String form(QualitySafe qualitySafe, Model model) {
		
		List<QualitySafe> list = qualitySafeService.findList(new QualitySafe());
		model.addAttribute("list", list);
		model.addAttribute("qualitySafe", qualitySafe);
		return "modules/ssp/safeForm";
	}
	
	

	/**
	 * 质量隐患随手拍的添加功能
	 * 
	 */
	@RequiresPermissions("ssp:qualitySafe:edit")
	@RequestMapping(value = "save")
	public String save(QualitySafe qualitySafe, Model model,HttpServletRequest request ,RedirectAttributes redirectAttributes) {
	
		if (!beanValidator(model, qualitySafe)){
			return form(qualitySafe, model);
		}
		
//		System.out.println("********************************");
//		System.out.println(request.getRequestURI()+"************"+request.getQueryString());
//		System.out.println("********************************");
//		
		qualitySafeService.save(qualitySafe);
		addMessage(redirectAttributes, "保存安全隐患成功");
		return "redirect:"+Global.getAdminPath()+"/ssp/qualitySafe/?repage";
	}
	
	
	
	/**
	 * 质量隐患随手拍的删除功能
	 * 
	 */
	
	@RequiresPermissions("ssp:qualitySafe:edit")
	@RequestMapping(value = "delete")
	public String delete(QualitySafe qualitySafe,HttpServletRequest request,Model model, RedirectAttributes redirectAttributes) {
//		System.out.println("********************************");
//		System.out.println(request.getRequestURI()+"************"+request.getQueryString());
//		System.out.println("********************************");
		qualitySafeService.delete(qualitySafe);
		addMessage(redirectAttributes, "删除安全隐患成功");
		return "redirect:"+Global.getAdminPath()+"/ssp/qualitySafe/?repage";
	}
	
	

	/**
	 * 图片下载
	 * 
	 */
	@RequestMapping(value="download")
	public String downLoad(HttpServletRequest request, HttpServletResponse response, 
			String dangeProId, RedirectAttributes redirectAttributes) {
		try {
			SafeDangerProjectManager entity = null;
			if (StringUtils.isNotBlank(dangeProId)){
				
			}
			String filePath = Global.getConfig("userfiles.basedir") + entity.getFilePath().replace("/platform", "");
			
			
			String filename = URLDecoder.decode(filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length()), "UTF-8");
			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1) + filename;
			File file = new File(filePath);
			return FileUtils.downFile(file, request, response, filename);
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/safe/safeDangerProjectManager/list?repage";
	}
	
	
	/**
	 * 图片查看
	 * 
	 */
	@RequestMapping(value="see")
	public String see(HttpServletRequest request, HttpServletResponse response, 
			String dangeProId, RedirectAttributes redirectAttributes) {
		try {
			SafeDangerProjectManager entity = null;
			if (StringUtils.isNotBlank(dangeProId)){
				
			}
			String filePath = Global.getConfig("userfiles.basedir") + entity.getFilePath().replace("/platform", "");
			
			
			String filename = URLDecoder.decode(filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length()), "UTF-8");
			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1) + filename;
			File file = new File(filePath);
			return FileUtils.downFile(file, request, response, filename);
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/safe/safeDangerProjectManager/list?repage";
	}
	
	

	/**
	 * 图片上传
	 * 
	 */
	@RequestMapping(value="upload")
	public String upload(MultipartFile file,HttpServletRequest request, HttpServletResponse response, 
			String dangeProId, RedirectAttributes redirectAttributes) {
		
			
		return "redirect:"+Global.getAdminPath()+"/safe/safeProManagerPerson/?repage";
		
	}
	
	
	
}
