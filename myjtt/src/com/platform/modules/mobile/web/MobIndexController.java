package com.platform.modules.mobile.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.platform.common.web.BaseController;
import com.platform.modules.project.entity.ProSimpleInfo;
import com.platform.modules.project.service.ProSimpleInfoService;

/**
 * 首页展示信息Controller
 * 
 * @author Mr.Jia
 * @version 2017-12-06
 */
@Controller
@RequestMapping(value = "${adminPath}/mobile/index")
public class MobIndexController extends BaseController {

	@Autowired
	private ProSimpleInfoService proSimpleInfoService;

	
	@RequestMapping(value = { "/", "" })
	public String home(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		// 查找所有项目信息
		ProSimpleInfo proSimpleInfo = new ProSimpleInfo();
		List<ProSimpleInfo> projectList = proSimpleInfoService.findList(proSimpleInfo);
		
		// 安全report info
		List<ProSimpleInfo> safeReportInfoList = proSimpleInfoService.selectSafeReportInfo();
		List<ProSimpleInfo> qualityReportInfoList = proSimpleInfoService.selectQualityReportInfo();
		
		model.addAttribute("projectList", projectList);
		model.addAttribute("safeReportInfoList", safeReportInfoList);
		model.addAttribute("qualityReportInfoList", qualityReportInfoList);
		return "modules/sys/sysIndex";
	}



}