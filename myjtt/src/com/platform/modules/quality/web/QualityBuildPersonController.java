package com.platform.modules.quality.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.platform.common.beanvalidator.BeanValidators;
import com.platform.common.config.Global;
import com.platform.common.persistence.Page;
import com.platform.common.web.BaseController;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.StringUtils;
import com.platform.common.utils.excel.ExportExcel;
import com.platform.common.utils.excel.ImportExcel;
import com.platform.modules.project.entity.ProSimpleInfo;
import com.platform.modules.project.service.ProSimpleInfoService;
import com.platform.modules.quality.entity.QualityBuildPerson;
import com.platform.modules.quality.service.QualityBuildPersonService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.UserUtils;

/**
 * 建设单位管理人员Controller
 * @author Mr.Jia
 * @version 2017-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/quality/qualityBuildPerson")
public class QualityBuildPersonController extends BaseController {

	@Autowired
	private QualityBuildPersonService qualityBuildPersonService;
	
	@Autowired
	private ProSimpleInfoService proSimpleInfoService;
	
	@ModelAttribute
	public QualityBuildPerson get(@RequestParam(required=false) String id) {
		QualityBuildPerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qualityBuildPersonService.get(id);
		}
		if (entity == null){
			entity = new QualityBuildPerson();
		}
		return entity;
	}
	
	@RequiresPermissions("quality:qualityBuildPerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(QualityBuildPerson qualityBuildPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QualityBuildPerson> page = qualityBuildPersonService.findPage(new Page<QualityBuildPerson>(request, response), qualityBuildPerson);
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("page", page);
		return "modules/quality/qualityBuildPersonList";
	}

	@RequiresPermissions("quality:qualityBuildPerson:view")
	@RequestMapping(value = "form")
	public String form(QualityBuildPerson qualityBuildPerson, Model model) {
		List<ProSimpleInfo> list = proSimpleInfoService.findAllProjectNames(new ProSimpleInfo());
		model.addAttribute("projectList", list);
		model.addAttribute("qualityBuildPerson", qualityBuildPerson);
		return "modules/quality/qualityBuildPersonForm";
	}

	@RequiresPermissions("quality:qualityBuildPerson:edit")
	@RequestMapping(value = "save")
	public String save(QualityBuildPerson qualityBuildPerson, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, qualityBuildPerson)){
			return form(qualityBuildPerson, model);
		}
		qualityBuildPersonService.save(qualityBuildPerson);
		addMessage(redirectAttributes, "保存建设单位管理人员成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualityBuildPerson/?repage";
	}
	
	@RequiresPermissions("quality:qualityBuildPerson:edit")
	@RequestMapping(value = "delete")
	public String delete(QualityBuildPerson qualityBuildPerson, RedirectAttributes redirectAttributes) {
		qualityBuildPersonService.delete(qualityBuildPerson);
		addMessage(redirectAttributes, "删除建设单位管理人员成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualityBuildPerson/?repage";
	}

	
	
	@RequiresPermissions("quality:qualityBuildPerson:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String export(HttpServletRequest request, HttpServletResponse response, 
			QualityBuildPerson qualityBuildPerson,
			RedirectAttributes redirectAttributes) {
		
		try {
			ProSimpleInfo proSimpleInfo = proSimpleInfoService.get(qualityBuildPerson.getProSimpleInfo().getId());
			String filename = proSimpleInfo.getProjectSimpleName() + "_建设单位管理人员表_" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			
			String fujian = "附表   3-1：";
			String title = "建设单位管理人员一览表";
			String projectName = "项目名称及管理单位名称：" 
						+ proSimpleInfo.getProjectSimpleName();
			
			String[] tableTitle = {"序号", "姓名", "部门", "职务/岗位", "职称", "身份证号", "备注"};
			int[] colLength = {36, 50, 100, 100, 100, 145, 90}; 		// 每一列所占宽度
			String footer = "注：填写管理处或项目公司管理人员与技术人员，不涵盖后勤人员";
			
			// 获取Data
			qualityBuildPerson.setProSimpleInfo(proSimpleInfo);
			List<QualityBuildPerson> list = qualityBuildPersonService.findList(qualityBuildPerson);
			
			String[][] data = null;
			if(list != null && list.size() > 0){
				data = new String[list.size()][colLength.length];
				for (int i = 0; i < list.size(); i++) {
					qualityBuildPerson = list.get(i);
					String[] str = new String[7];
					str[0] = String.valueOf(i+1);
					str[1] = qualityBuildPerson.getName();
					str[2] = qualityBuildPerson.getDepartment();
					str[3] = qualityBuildPerson.getPost();
					str[4] = qualityBuildPerson.getJobTitle();
					str[5] = qualityBuildPerson.getIdcard();
					str[6] = qualityBuildPerson.getRemarks();
					data[i] = str;
				}
			} else {
				addMessage(redirectAttributes, "没有数据");
    			return "redirect:"+Global.getAdminPath()+"/quality/qualityBuildPerson/?repage";
			}
			
			new ExportExcel(fujian, title, projectName, colLength.length, false)
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
		
		return "redirect:"+Global.getAdminPath()+"/quality/qualityBuildPerson/?repage";
	}
	
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("quality:qualityBuildPerson:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QualityBuildPerson> list = ei.getDataList(QualityBuildPerson.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			QualityBuildPerson qualityBuildPerson = new QualityBuildPerson();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						qualityBuildPerson = list.get(i);
						if (qualityBuildPerson.getProjectId() != null && !qualityBuildPerson.getProjectId().equals("")){
							ProSimpleInfo projectInfo = new ProSimpleInfo();
							projectInfo.setProjectSimpleName(qualityBuildPerson.getProjectId());
							List<ProSimpleInfo> proList = proSimpleInfoService.findAllProjectNames(projectInfo);
							if(proList != null && proList.size() > 0){
								qualityBuildPerson.setProSimpleInfo(proList.get(0));
								qualityBuildPerson.setCreateBy(user);
								qualityBuildPerson.setUpdateBy(user);
								qualityBuildPerson.setCreateDate(now);
								qualityBuildPerson.setUpdateDate(now);
								qualityBuildPersonService.save(qualityBuildPerson);
								successNum++;
							} else {
								failureMsg.append("<br/>项目" + qualityBuildPerson.getProjectId() + " 不存在;");
							}
						}
						
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>项目 " + qualityBuildPerson.getProjectId() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>项目 " + qualityBuildPerson.getProjectId() + " 导入失败："+ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条建设单位信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条建设单位信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualityBuildPerson/?repage";
    }
	
	
	
	/**
	 * 下载导入建设单位用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("quality:qualityBuildPerson:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "建设单位用户数据导入模板.xlsx";
    		List<QualityBuildPerson> list = Lists.newArrayList(); 
    		list.add(new QualityBuildPerson());
    		new ExportExcel("建设单位用户信息", QualityBuildPerson.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualityBuildPerson/?repage";
    }
}