package com.platform.modules.quality.web;

import java.util.ArrayList;
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
import com.platform.modules.quality.entity.QualityThirdCheckInfo;
import com.platform.modules.quality.entity.QualityThirdUserInfo;
import com.platform.modules.quality.service.QualityThirdCheckInfoService;
import com.platform.modules.quality.service.QualityThirdUserInfoService;
import com.platform.modules.sys.entity.User;
import com.platform.modules.sys.utils.UserUtils;

/**
 * 检测单位工作内容汇总信息Controller
 * @author Mr.Jia
 * @version 2017-12-11
 */
@Controller
@RequestMapping(value = "${adminPath}/quality/qualityThirdUserInfo")
public class QualityThirdUserInfoController extends BaseController {

	@Autowired
	private QualityThirdUserInfoService qualityThirdUserInfoService;
	@Autowired
	private QualityThirdCheckInfoService qualityThirdCheckInfoService;
	
	@ModelAttribute
	public QualityThirdUserInfo get(@RequestParam(required=false) String id) {
		QualityThirdUserInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qualityThirdUserInfoService.get(id);
		}
		if (entity == null){
			entity = new QualityThirdUserInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("quality:qualityThirdUserInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(QualityThirdUserInfo qualityThirdUserInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QualityThirdUserInfo> page = qualityThirdUserInfoService.findPage(new Page<QualityThirdUserInfo>(request, response), qualityThirdUserInfo); 
		
		// 获取合同段编号
		List<QualityThirdCheckInfo> qualityThirdCheckInfoList = qualityThirdCheckInfoService.findList(new QualityThirdCheckInfo());
		model.addAttribute("qualityThirdCheckInfoList", qualityThirdCheckInfoList);
		
		model.addAttribute("page", page);
		return "modules/quality/qualityThirdUserInfoList";
	}

	@RequiresPermissions("quality:qualityThirdUserInfo:view")
	@RequestMapping(value = "form")
	public String form(QualityThirdUserInfo qualityThirdUserInfo, Model model) {
		
		// 获取合同段编号
		List<QualityThirdCheckInfo> qualityThirdCheckInfoList = qualityThirdCheckInfoService.findList(new QualityThirdCheckInfo());
		model.addAttribute("qualityThirdCheckInfoList", qualityThirdCheckInfoList);
		
		// -----------人员信息解析Start-------------------------------------------------------------
		if(qualityThirdUserInfo.getApproachUser() != null ){
			String approachUsers = qualityThirdUserInfo.getApproachUser();
			String[] str = approachUsers.split("==");
			String[] usernames = str[0].split("-");		
			String[] cids = str[1].split("-");
			String[] approachUsernames = str[2].split("-");
			String[] approachCids = str[3].split("-");
			
			List<String[]> userList = new ArrayList<String[]>();
			if(usernames.length > 0){		// 存在至少一名合同人员
				if( approachUsernames.length >= usernames.length ){
					for (int j = 0; j < approachUsernames.length; j++) {		
						if(j >= usernames.length ){
							userList.add(new String[]{"", "", approachUsernames[j], approachCids[j]});
						} else {
							userList.add(new String[]{usernames[j], cids[j], approachUsernames[j], approachCids[j]});
						}
					}
				} else {
					for (int j = 0; j < usernames.length; j++) {		
						if(j >= approachUsernames.length ){
							userList.add(new String[]{usernames[j], cids[j], "", ""});
						} else {
							userList.add(new String[]{usernames[j], cids[j], approachUsernames[j], approachCids[j]});
						}
					}
				}
			} else {
				for (int j = 0; j < approachUsernames.length; j++) {		
					userList.add(new String[]{"", "", approachUsernames[j], approachCids[j]});
				}
			}
			qualityThirdUserInfo.setUserList(userList);
		}
		// -----------人员信息解析End-------------------------------------------------------------
		
		
		
		
		model.addAttribute("qualityThirdUserInfo", qualityThirdUserInfo);
		return "modules/quality/qualityThirdUserInfoForm";
	}

	@RequiresPermissions("quality:qualityThirdUserInfo:edit")
	@RequestMapping(value = "save")
	public String save(QualityThirdUserInfo qualityThirdUserInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, qualityThirdUserInfo)){
			return form(qualityThirdUserInfo, model);
		}
		qualityThirdUserInfoService.save(qualityThirdUserInfo);
		addMessage(redirectAttributes, "保存检测单位工作内容汇总信息成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualityThirdUserInfo/?repage";
	}
	
	@RequiresPermissions("quality:qualityThirdUserInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(QualityThirdUserInfo qualityThirdUserInfo, RedirectAttributes redirectAttributes) {
		qualityThirdUserInfoService.delete(qualityThirdUserInfo);
		addMessage(redirectAttributes, "删除检测单位工作内容汇总信息成功");
		return "redirect:"+Global.getAdminPath()+"/quality/qualityThirdUserInfo/?repage";
	}

	
	
	@RequiresPermissions("quality:qualityThirdUserInfo:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String export(HttpServletRequest request, HttpServletResponse response, 
			QualityThirdUserInfo qualityThirdUserInfo,
			RedirectAttributes redirectAttributes) {
		
		try {
			QualityThirdCheckInfo qualityThirdCheckInfo = qualityThirdCheckInfoService.get(qualityThirdUserInfo.getQualityThirdCheckInfo().getId());
			String filename = "合同段" + qualityThirdCheckInfo.getContractSectionLabel() + "_第三方检测单位信息人员情况及工作汇总信息_" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			
			String fujian = "附表   6-2：";
			String title = "第三方检测单位人员情况及工作汇总";
			String projectName = "合同段编号：" 
						+ qualityThirdCheckInfo.getContractSectionLabel();
			
			String[][] tableTitle = {{"合同人员", "", "实际进场人员", "", "检测内容", "办公场所、仪器设备到位情况", "备注"},
								{"姓名", "身份证号", "姓名", "身份证号", "", "", ""}};
			int[] colLength = {100, 150, 100, 150, 125, 125, 125}; 		// 每一列所占宽度
			
			// 获取Data
			List<QualityThirdUserInfo> list = qualityThirdUserInfoService.findList(qualityThirdUserInfo);
			
			String[][] data;
			int rowSpan = 0;
			if(list != null && list.size() > 0){
				qualityThirdUserInfo = list.get(0);
				String approachUsers = qualityThirdUserInfo.getApproachUser();
				String[] approach = approachUsers.split("==");
				String[] usernames = approach[0].split("-");		
				String[] cids = approach[1].split("-");
				String[] approachUsernames = approach[2].split("-");
				String[] approachCids = approach[3].split("-");
				if(usernames.length > 0){		// 存在至少一名合同人员
					if( approachUsernames.length >= usernames.length ){
						data = new String[approachUsernames.length][7];
						rowSpan = approachUsernames.length;
						for (int j = 0; j < approachUsernames.length; j++) {		
							if(j >= usernames.length ){
								data[j][0] = "";
								data[j][1] = "";
								data[j][2] = approachUsernames[j];
								data[j][3] = approachCids[j];
							} else {
								data[j][0] = usernames[j];
								data[j][1] = cids[j];
								data[j][2] = approachUsernames[j];
								data[j][3] = approachCids[j];
							}
						}
					} else {
						data = new String[usernames.length][7];
						rowSpan = usernames.length;
						for (int j = 0; j < usernames.length; j++) {		
							if(j >= approachUsernames.length ){
								data[j][0] = usernames[j];
								data[j][1] = cids[j];
								data[j][2] = "";
								data[j][3] = "";
							} else {
								data[j][0] = usernames[j];
								data[j][1] = cids[j];
								data[j][2] = approachUsernames[j];
								data[j][3] = approachCids[j];
							}
						}
					}
				} else {
					data = new String[approachUsernames.length][7];
					rowSpan = approachUsernames.length;
					for (int j = 0; j < approachUsernames.length; j++) {	
						data[j][0] = "";
						data[j][1] = "";
						data[j][2] = approachUsernames[j];
						data[j][3] = approachCids[j];
					}
				}
				
				for (int i = 0; i < data.length; i++) {
					if(i == 0){
						data[i][4] = qualityThirdUserInfo.getContractContext();
						data[i][5] = qualityThirdUserInfo.getOfficeEqui();
						data[i][6] = qualityThirdUserInfo.getRemarks();
					} else {
						data[i][4] = "";
						data[i][5] = "";
						data[i][6] = "";
					}
				}	
				
					
			} else {
				addMessage(redirectAttributes, "没有数据");
    			return "redirect:"+Global.getAdminPath()+"/quality/qualityThirdUserInfo/?repage";
			}
			
			new ExportExcel(fujian, title, projectName, colLength.length, true)
					.setTableTitleForCheckUser(tableTitle)
					.setDataArrayForCheckUser(data, rowSpan)
					.setColLength(colLength)
					.write(response, filename)
					.dispose();

			logger.debug("Export success.");
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "下载失败！失败信息："+e.getMessage());
		}
		
		return "redirect:"+Global.getAdminPath()+"/quality/qualityThirdUserInfo/?repage";
	}
	

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("quality:qualityThirdUserInfo:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QualityThirdUserInfo> list = ei.getDataList(QualityThirdUserInfo.class);
			
			Date now = new Date();
			User user = UserUtils.getUser();
			QualityThirdUserInfo qualityThirdUserInfo = new QualityThirdUserInfo();
			
			QualityThirdUserInfo qualityThirdUserInfoTemp = new QualityThirdUserInfo();
			List<QualityThirdUserInfo> usersList = new ArrayList<QualityThirdUserInfo>();
			String conName = "";
			String conCid = "";
			String inName = "";
			String inCid = "";
			
			
			
			if(list != null && list.size() > 0){
				for (int i = 0; i < list.size(); i++) {
					try{
						qualityThirdUserInfo = list.get(i);
						if (qualityThirdUserInfo.getContractSectionLabel() != null && !qualityThirdUserInfo.getContractSectionLabel().equals("")){
							QualityThirdCheckInfo qualityThirdCheckInfo = new QualityThirdCheckInfo();
							qualityThirdCheckInfo.setContractSectionLabel(qualityThirdUserInfo.getContractSectionLabel());
							List<QualityThirdCheckInfo> proList = qualityThirdCheckInfoService.findList(qualityThirdCheckInfo);
							if(proList != null && proList.size() > 0){
								qualityThirdUserInfo.setQualityThirdCheckInfo(proList.get(0));
								qualityThirdUserInfo.setCreateBy(user);
								qualityThirdUserInfo.setUpdateBy(user);
								qualityThirdUserInfo.setCreateDate(now);
								qualityThirdUserInfo.setUpdateDate(now);
								qualityThirdUserInfoTemp = qualityThirdUserInfo;
							} else {
								failureMsg.append("<br/>合同段编号" + qualityThirdUserInfo.getContractSectionLabel() + " 不存在;");
							}
						}
						
						conName += qualityThirdUserInfo.getConName() + "-"; 
						conCid += qualityThirdUserInfo.getConCid() + "-";
						inName += qualityThirdUserInfo.getInName() + "-";
						inCid += qualityThirdUserInfo.getInCid() + "-";
						
						if( i == list.size()-1 || (list.get(i+1).getContractSectionLabel() != null && !"".equals(list.get(i+1).getContractSectionLabel()))){
							qualityThirdUserInfoTemp.setApproachUser(conName + "==" + conCid + "==" + inName + "==" + inCid);
							usersList.add(qualityThirdUserInfoTemp);
							qualityThirdUserInfoTemp = new QualityThirdUserInfo();
							conName = "";              
							conCid = "";               
							inName = "";               
							inCid = "";  
						}
						
					}catch(ConstraintViolationException ex){
						failureMsg.append("<br/>合同段编号 " + qualityThirdUserInfo.getContractSectionLabel() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList){
							failureMsg.append(message+"; ");
							failureNum++;
						}
					}catch (Exception ex) {
						failureMsg.append("<br/>合同段编号 " + qualityThirdUserInfo.getContractSectionLabel() + " 导入失败："+ex.getMessage());
					}
				}
			}
			
			if(usersList != null && usersList.size() > 0){
				for (QualityThirdUserInfo qualityThirdUserInfo2 : usersList) {
					qualityThirdUserInfoService.save(qualityThirdUserInfo2);
				}
			}
			
			successNum = usersList.size();
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条检测单位人员及工作情况信息，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条检测单位人员及工作情况信息"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualityThirdUserInfo/?repage";
    }
	
	
	
	/**
	 * 下载导入检测单位人员及工作情况
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("quality:qualityThirdUserInfo:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "检测单位人员及工作情况信息导入模板.xlsx";
    		List<QualityThirdUserInfo> list = Lists.newArrayList(); 
    		list.add(new QualityThirdUserInfo());
    		new ExportExcel("检测单位人员及工作情况信息", QualityThirdUserInfo.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/quality/qualityThirdUserInfo/?repage";
    }
	
}