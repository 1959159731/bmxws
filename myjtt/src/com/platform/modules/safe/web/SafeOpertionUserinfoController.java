package com.platform.modules.safe.web;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.platform.common.beanvalidator.BeanValidators;
import com.platform.common.config.Constant;
import com.platform.common.config.Global;
import com.platform.common.persistence.Page;
import com.platform.common.web.BaseController;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.StringUtils;
import com.platform.common.utils.excel.ExportExcel;
import com.platform.common.utils.excel.ImportExcel;
import com.platform.modules.safe.entity.SafeOpertionUserinfo;
import com.platform.modules.safe.service.SafeOpertionUserinfoService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.DictUtils;
import com.platform.modules.sys.utils.UserUtils;

/**
 * 特种、专用设备操作人员信息Controller
 * @author Mr.Jia
 * @version 2017-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/safe/safeOpertionUserinfo")
public class SafeOpertionUserinfoController extends BaseController {

	@Autowired
	private SafeOpertionUserinfoService safeOpertionUserinfoService;
	
	@ModelAttribute
	public SafeOpertionUserinfo get(@RequestParam(required=false) String id) {
		SafeOpertionUserinfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = safeOpertionUserinfoService.get(id);
		}
		if (entity == null){
			entity = new SafeOpertionUserinfo();
		}
		return entity;
	}
	
	@RequiresPermissions("safe:safeOpertionUserinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(SafeOpertionUserinfo safeOpertionUserinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SafeOpertionUserinfo> page = safeOpertionUserinfoService.findPage(new Page<SafeOpertionUserinfo>(request, response), safeOpertionUserinfo); 
		model.addAttribute("page", page);
		return "modules/safe/safeOpertionUserinfoList";
	}

	@RequiresPermissions("safe:safeOpertionUserinfo:view")
	@RequestMapping(value = "form")
	public String form(SafeOpertionUserinfo safeOpertionUserinfo, Model model) {
		model.addAttribute("safeOpertionUserinfo", safeOpertionUserinfo);
		return "modules/safe/safeOpertionUserinfoForm";
	}

	@RequiresPermissions("safe:safeOpertionUserinfo:edit")
	@RequestMapping(value = "save")
	public String save(SafeOpertionUserinfo safeOpertionUserinfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, safeOpertionUserinfo)){
			return form(safeOpertionUserinfo, model);
		}
		safeOpertionUserinfoService.save(safeOpertionUserinfo);
		addMessage(redirectAttributes, "保存特种、专用设备操作人员信息成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeOpertionUserinfo/?repage";
	}
	
	@RequiresPermissions("safe:safeOpertionUserinfo:edit")
	@RequestMapping(value = "delete")
	public String delete(SafeOpertionUserinfo safeOpertionUserinfo, RedirectAttributes redirectAttributes) {
		safeOpertionUserinfoService.delete(safeOpertionUserinfo);
		addMessage(redirectAttributes, "删除特种、专用设备操作人员信息成功");
		return "redirect:"+Global.getAdminPath()+"/safe/safeOpertionUserinfo/?repage";
	}

	
	/**
	 * 按照用户类型查找所有用户
	 * 用于特种设备、专用设备人员添加
	 * @param request
	 * @param opertionUserType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getOpertionUser", method = RequestMethod.POST)
	public List<SafeOpertionUserinfo> getOpertionUser(HttpServletRequest request, String opertionUserType) {
		SafeOpertionUserinfo safeOpertionUserinfo = new SafeOpertionUserinfo();
		safeOpertionUserinfo.setOpertionUserType(opertionUserType);
		List<SafeOpertionUserinfo> result = safeOpertionUserinfoService.findList(safeOpertionUserinfo);
		
		for (int i = 0; i < result.size(); i++) {
			safeOpertionUserinfo = result.get(i);
			safeOpertionUserinfo.setGender(safeOpertionUserinfo.getGender()
					.equals(Constant.SEX_MAN_NUM)
					? Constant.SEX_MAN : Constant.SEX_WOMAN );
			safeOpertionUserinfo.setCertificateValid( DateUtils.parseDate(
					DateUtils.formatDate(safeOpertionUserinfo.getCreateDate(), "yyyy-MM-dd") ));
		}
		return result;
	}
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeOpertionUserinfo:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SafeOpertionUserinfo> list = ei.getDataList(SafeOpertionUserinfo.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			SafeOpertionUserinfo safeOpertionUserinfo = new SafeOpertionUserinfo();
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						safeOpertionUserinfo = list.get(i);
						safeOpertionUserinfo.setCreateBy(user);
						safeOpertionUserinfo.setUpdateBy(user);
						safeOpertionUserinfo.setCreateDate(now);
						safeOpertionUserinfo.setUpdateDate(now);
						safeOpertionUserinfo.setGender(DictUtils.getDictValue(safeOpertionUserinfo.getGender(), "sex", "1"));
						safeOpertionUserinfo.setOpertionUserType(DictUtils.getDictValue(safeOpertionUserinfo.getOpertionUserType(), "opertion_user_type", "1"));
						safeOpertionUserinfoService.save(safeOpertionUserinfo);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>人员 " + safeOpertionUserinfo.getName() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>人员 " + safeOpertionUserinfo.getName() + " 导入失败："+ex.getMessage());
					}
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条人员信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条人员信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeOpertionUserinfo/?repage";
    }
	
	
	
	/**
	 * 下载导入建设单位用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("safe:safeOpertionUserinfo:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "特种、专用设备操作人员导入模板.xlsx";
    		List<SafeOpertionUserinfo> list = Lists.newArrayList(); 
    		list.add(new SafeOpertionUserinfo());
    		new ExportExcel("特种、专用设备操作人员信息", SafeOpertionUserinfo.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/safe/safeOpertionUserinfo/?repage";
    }
}