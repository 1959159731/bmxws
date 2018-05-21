package com.platform.modules.quality.web;

import java.util.ArrayList;
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
import com.platform.modules.quality.entity.QualityConsTestroomRemark;
import com.platform.modules.quality.entity.QualityTestroomRemarkUserinfo;
import com.platform.modules.quality.service.QualityConsTestroomRemarkService;
import com.platform.modules.quality.service.QualityTestroomRemarkUserinfoService;

/**
 * 施工单位试验室备案信息Controller
 * @author Mr.Jia
 * @version 2017-12-13
 */
@Controller
@RequestMapping(value = "${adminPath}/quality/qualityConsTestroomRemark")
public class QualityConsTestroomRemarkController extends BaseController {

	@Autowired
	private QualityConsTestroomRemarkService qualityConsTestroomRemarkService;
	@Autowired
	private QualityTestroomRemarkUserinfoService qualityTestroomRemarkUserinfoService;
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public QualityConsTestroomRemark get(@RequestParam(required=false) String id) {
		QualityConsTestroomRemark entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qualityConsTestroomRemarkService.get(id);
		}
		if (entity == null){
			entity = new QualityConsTestroomRemark();
		}
		return entity;
	}
	
	@RequiresPermissions("quality:qualityConsTestroomRemark:view")
	@RequestMapping(value = {"list", ""})
	public String list(QualityConsTestroomRemark qualityConsTestroomRemark, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QualityConsTestroomRemark> page = qualityConsTestroomRemarkService.findPage(new Page<QualityConsTestroomRemark>(request, response), qualityConsTestroomRemark); 
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("page", page);
		return "modules/quality/qualityConsTestroomRemarkList";
	}

	@RequiresPermissions("quality:qualityConsTestroomRemark:view")
	@RequestMapping(value = "form")
	public String form(QualityConsTestroomRemark qualityConsTestroomRemark, Model model) {
		// 项目列表信息
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		
		// 所属人员信息
		List<QualityTestroomRemarkUserinfo> userList = new ArrayList<QualityTestroomRemarkUserinfo>();
		if(qualityConsTestroomRemark != null && qualityConsTestroomRemark.getId() != null && !qualityConsTestroomRemark.getId().equals("")){
			QualityTestroomRemarkUserinfo qualityTestroomRemarkUserinfo = new QualityTestroomRemarkUserinfo();
			qualityTestroomRemarkUserinfo.setRemarkId(qualityConsTestroomRemark.getId());
			qualityTestroomRemarkUserinfo.setRemarkUsertype("2");		// 1-中心试验室人员；2-施工单位试验室人员
			userList = qualityTestroomRemarkUserinfoService.findList(qualityTestroomRemarkUserinfo);
		}
		model.addAttribute("userList", userList);
		
		model.addAttribute("qualityConsTestroomRemark", qualityConsTestroomRemark);
		return "modules/quality/qualityConsTestroomRemarkForm";
	}

	@RequiresPermissions("quality:qualityConsTestroomRemark:edit")
	@RequestMapping(value = "save")
	public String save(QualityConsTestroomRemark qualityConsTestroomRemark, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, qualityConsTestroomRemark)){
			return form(qualityConsTestroomRemark, model);
		}
		qualityConsTestroomRemarkService.save(qualityConsTestroomRemark);
		
		
		// -----------人员信息解析Start-------------------------------------------------------------
		if(qualityConsTestroomRemark.getRemarkUsers() != null ){
			qualityTestroomRemarkUserinfoService.deleteByRemarkId(qualityConsTestroomRemark.getId());
			
			String approachUsers = qualityConsTestroomRemark.getRemarkUsers();
			String[] str = approachUsers.split("==");
			String[] usernames = str[0].split("--");		
			String[] qualifications = str[1].split("--");
			String[] qCertNum = str[2].split("--");
			String[] remark = str[3].split("--");
			
			for (int i = 0; i < usernames.length; i++) {
				QualityTestroomRemarkUserinfo qualityTestroomRemarkUserinfo = new QualityTestroomRemarkUserinfo();
				qualityTestroomRemarkUserinfo.setRemarkId(qualityConsTestroomRemark.getId());
				qualityTestroomRemarkUserinfo.setRemarkUsertype("2");
				qualityTestroomRemarkUserinfo.setName(usernames[i]);
				qualityTestroomRemarkUserinfo.setQualifications(qualifications[i]);
				qualityTestroomRemarkUserinfo.setQualificationCertNum(qCertNum[i]);
				if(remark.length > 0){
					qualityTestroomRemarkUserinfo.setRemarks(remark[i]);
				}
				qualityTestroomRemarkUserinfoService.save(qualityTestroomRemarkUserinfo);
			}
			
		}
		// -----------人员信息解析End-------------------------------------------------------------
		
		
		addMessage(redirectAttributes, "保存施工单位试验室备案信息成功");
		return "redirect:"+Global.getAdminPath() + "/quality/qualityConsTestroomRemark/?repage";
	}
	
	@RequiresPermissions("quality:qualityConsTestroomRemark:edit")
	@RequestMapping(value = "delete")
	public String delete(QualityConsTestroomRemark qualityConsTestroomRemark, RedirectAttributes redirectAttributes) {
		qualityConsTestroomRemarkService.delete(qualityConsTestroomRemark);
		addMessage(redirectAttributes, "删除施工单位试验室备案信息成功");
		return "redirect:"+Global.getAdminPath() + "/quality/qualityConsTestroomRemark/?repage";
	}

	
	
	@RequiresPermissions("quality:qualityConsTestroomRemark:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String export(HttpServletRequest request, HttpServletResponse response, 
			QualityConsTestroomRemark qualityConsTestroomRemark,
			RedirectAttributes redirectAttributes) {
		
		try {
			ProSimpleInfo proSimpleInfo = proSimpleInfoService.get(qualityConsTestroomRemark.getProSimpleInfo().getId());
			String filename = proSimpleInfo.getProjectSimpleName() + "_施工单位试验室备案信息表_" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			
			String fujian = "附表 5：";
			String title = proSimpleInfo.getProjectSimpleName() + "施工单位试验室备案信息表";
			
			List<QualityConsTestroomRemark> qualityConsTestroomRemarkList = qualityConsTestroomRemarkService.findList(qualityConsTestroomRemark);
			qualityConsTestroomRemark = qualityConsTestroomRemarkList.get(0);
			String[] tableTop = { qualityConsTestroomRemark.getContractSectionLabel(), qualityConsTestroomRemark.getMaternalLaboratory()
							, qualityConsTestroomRemark.getQacertificateNum(), qualityConsTestroomRemark.getAuthorizedPerson() };
			
			String[] tableTitle = {"序号", "姓名", "从业资格", "资格证书编号", "备注"};
			int[] colLength = {80, 100, 140, 150, 150}; 		// 每一列所占宽度
			
			// 获取Data
			QualityTestroomRemarkUserinfo qualityTestroomRemarkUserinfo = new QualityTestroomRemarkUserinfo();
			qualityTestroomRemarkUserinfo.setRemarkId(qualityConsTestroomRemark.getId());
			List<QualityTestroomRemarkUserinfo> list = qualityTestroomRemarkUserinfoService.findList(qualityTestroomRemarkUserinfo);
			
			String[][] data = null;
			if(list != null && list.size() > 0){
				data = new String[list.size()][colLength.length];
				for (int i = 0; i < list.size(); i++) {
					qualityTestroomRemarkUserinfo = list.get(i);
					String[] str = new String[5];
					str[0] = String.valueOf(i+1);
					str[1] = qualityTestroomRemarkUserinfo.getName();
					str[2] = qualityTestroomRemarkUserinfo.getQualifications();
					str[3] = qualityTestroomRemarkUserinfo.getQualificationCertNum();
					str[4] = qualityTestroomRemarkUserinfo.getRemarks() == null ? "" : qualityTestroomRemarkUserinfo.getRemarks();
					data[i] = str;
				}
			} else {
				addMessage(redirectAttributes, "没有数据");
    			return "redirect:"+Global.getAdminPath() + "/quality/qualityConsTestroomRemark/?repage";
			}
			
			new ExportExcel(fujian, title, colLength.length, false)
					.setTableTop(tableTop)
					.setTableTitle(tableTitle)
					.setDataArrayForCenter(data)
					.setText(qualityConsTestroomRemark.getDetectionProjectParameter())
					.setColLength(colLength)
					.write(response, filename)
					.dispose();

			logger.debug("Export success.");
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		
		return "redirect:"+Global.getAdminPath() + "/quality/qualityConsTestroomRemark/?repage";
	}
	
	
	
}