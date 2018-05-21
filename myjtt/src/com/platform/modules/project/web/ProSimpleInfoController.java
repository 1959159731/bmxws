package com.platform.modules.project.web;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * 项目基本信息Controller
 * 
 * @author Mr.Jia
 * @version 2017-12-06
 */
@Controller
@RequestMapping(value = "${adminPath}/project/proSimpleInfo")
public class ProSimpleInfoController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ProSimpleInfoController.class);

	@Autowired
	private ProSimpleInfoService proSimpleInfoService;

	@ModelAttribute
	public ProSimpleInfo get(@RequestParam(required = false) String id) {
		ProSimpleInfo entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = proSimpleInfoService.get(id);
		}
		if (entity == null) {
			entity = new ProSimpleInfo();
		}
		return entity;
	}

	@RequiresPermissions("project:proSimpleInfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(ProSimpleInfo proSimpleInfo, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<ProSimpleInfo> page = proSimpleInfoService.findPage(new Page<ProSimpleInfo>(request, response),
				proSimpleInfo);
		model.addAttribute("page", page);
		return "modules/project/proSimpleInfoList";
	}

	@RequiresPermissions("project:proSimpleInfo:view")
	@RequestMapping(value = "form")
	public String form(ProSimpleInfo proSimpleInfo, Model model) {
		model.addAttribute("proSimpleInfo", proSimpleInfo);
		return "modules/project/proSimpleInfoForm";
	}

	@RequiresPermissions("project:proSimpleInfo:edit")
	@RequestMapping(value = "save")
	public String save(ProSimpleInfo proSimpleInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, proSimpleInfo)) {
			return form(proSimpleInfo, model);
		}
		proSimpleInfoService.save(proSimpleInfo);
		addMessage(redirectAttributes, "保存项目基本信息成功");
		return "redirect:" + Global.getAdminPath() + "/project/proSimpleInfo/?repage";
	}

	@RequiresPermissions("project:proSimpleInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(ProSimpleInfo proSimpleInfo,HttpServletRequest request, RedirectAttributes redirectAttributes) {
	
		proSimpleInfoService.delete(proSimpleInfo);
		addMessage(redirectAttributes, "删除项目基本信息成功");
		return "redirect:" + Global.getAdminPath() + "/project/proSimpleInfo/?repage";
	}

	@RequiresPermissions("project:proSimpleInfo:view")
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public void export(HttpServletRequest request, HttpServletResponse response, ProSimpleInfo proSimpleInfo,
			RedirectAttributes redirectAttributes) {
		try {
			proSimpleInfo = proSimpleInfoService.get(proSimpleInfo.getId());
			
			String filename = proSimpleInfo.getProjectName() + "_" + DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
			
			String headTitle = "附表1：";
			
			String title = "高速公路项目基本信息表";
			
			String[][] appData = new String[4][8];
			appData[0][0] = "项目立项";
			appData[1][0] = "工可批复";
			appData[2][0] = "初步设计审查";
			appData[3][0] = "施工图设计批复";
			appData[0][2] = proSimpleInfo.getProSimpleApprove().getApprovalCompany();
			appData[0][4] = proSimpleInfo.getProSimpleApprove().getApprovalNum();
			appData[0][6] = DateUtils.formatDate(proSimpleInfo.getProSimpleApprove().getApprovalTime(), "yyyy-MM-dd");					
			appData[1][2] = proSimpleInfo.getProSimpleApprove().getWorkersApprovalCompany();			
			appData[1][4] = proSimpleInfo.getProSimpleApprove().getWorkersApprovalNum();				
			appData[1][6] = DateUtils.formatDate(proSimpleInfo.getProSimpleApprove().getWorkersApprovalTime(), "yyyy-MM-dd");		
			appData[2][2] = proSimpleInfo.getProSimpleApprove().getDesignApprovalCompany();			
			appData[2][4] = proSimpleInfo.getProSimpleApprove().getDesignApprovalNum();				
			appData[2][6] = DateUtils.formatDate(proSimpleInfo.getProSimpleApprove().getDesignApprovalTime(), "yyyy-MM-dd");				
			appData[3][2] = proSimpleInfo.getProSimpleApprove().getConstructionDesignApprovalUnit();
			appData[3][4] = proSimpleInfo.getProSimpleApprove().getConstructionDesignApprovalNum();	
			appData[3][6] = DateUtils.formatDate(proSimpleInfo.getProSimpleApprove().getConstructionDesignApprovalTime(), "yyyy-MM-dd");
			proSimpleInfo.setExportAppData(appData);         
			
			String[][] normalData = new String[3][8];
			normalData[0][0] = "建设里程（km）";
			normalData[1][0] = "工程概算（万元）";
			normalData[2][0] = "批准工期（月）";
			normalData[0][1] = String.valueOf(proSimpleInfo.getBuildMileage());
			normalData[1][1] = String.valueOf(proSimpleInfo.getProjectEstimate());
			normalData[2][1] = String.valueOf(proSimpleInfo.getApproveDuration());
			normalData[0][2] = "设计时速（km/h）";
			normalData[1][2] = "建安费（万元）";
			normalData[2][2] = "合同工期（月）";
			normalData[0][3] = String.valueOf(proSimpleInfo.getDesignSpeed());
			normalData[1][3] = String.valueOf(proSimpleInfo.getConstructionResetFee());
			normalData[2][3] = String.valueOf(proSimpleInfo.getContractDuration());
			normalData[0][4] = "路基宽度（m）";
			normalData[1][4] = "办理监督手续时间";
			normalData[2][4] = "（拟）开工时间";
			normalData[0][5] = String.valueOf(proSimpleInfo.getSubgradeWidth());
			normalData[1][5] = DateUtils.formatDate(proSimpleInfo.getAppSupervisionTime(), "yyyy-MM-dd");
			normalData[2][5] = DateUtils.formatDate(proSimpleInfo.getProposedStartTime(), "yyyy-MM-dd");
			normalData[0][6] = "桥隧比例（%）";
			normalData[1][6] = "监督管理受理时间";
			normalData[2][6] = "（拟）交工时间";
			normalData[0][7] = String.valueOf(proSimpleInfo.getBridgeRatio());
			normalData[1][7] = DateUtils.formatDate(proSimpleInfo.getManSupervisionTime(), "yyyy-MM-dd");
			normalData[2][7] = DateUtils.formatDate(proSimpleInfo.getProposedDeliveryTime(), "yyyy-MM-dd");
			proSimpleInfo.setExportNormalData(normalData);  
			
			
			String[][] bridgeData = new String[5][8];
			bridgeData[0][0] = "桥梁工程";
			bridgeData[0][4] = "隧道工程";
			bridgeData[1][0] = "特大桥（座）";
			bridgeData[1][1] = String.valueOf(proSimpleInfo.getProSimpleBridgeTunnel().getExtraLargeBridgeNum());
			bridgeData[1][2] = "累计长度（m）";
			bridgeData[1][3] = String.valueOf(proSimpleInfo.getProSimpleBridgeTunnel().getExtraLargeBridgeLength());
			bridgeData[1][4] = "特长隧道（座）";
			bridgeData[1][5] = String.valueOf(proSimpleInfo.getProSimpleBridgeTunnel().getExtraLongTunnelNum());
			bridgeData[1][6] = "累计长度（m）";
			bridgeData[1][7] = String.valueOf(proSimpleInfo.getProSimpleBridgeTunnel().getExtraLongTunnelLength());
			bridgeData[2][0] = "大桥（座）";
			bridgeData[2][1] = String.valueOf(proSimpleInfo.getProSimpleBridgeTunnel().getLargeBridgeNum());
			bridgeData[2][2] = "累计长度（m）";
			bridgeData[2][3] = String.valueOf(proSimpleInfo.getProSimpleBridgeTunnel().getLargeBridgeLength());
			bridgeData[2][4] = "长隧道（座）";
			bridgeData[2][5] = String.valueOf(proSimpleInfo.getProSimpleBridgeTunnel().getLongTunnelNum());
			bridgeData[2][6] = "累计长度（m）";
			bridgeData[2][7] = String.valueOf(proSimpleInfo.getProSimpleBridgeTunnel().getLongTunnelLength());
			bridgeData[3][0] = "中桥（座）";
			bridgeData[3][1] = String.valueOf(proSimpleInfo.getProSimpleBridgeTunnel().getMediumBridgeNum());
			bridgeData[3][2] = "累计长度（m）";
			bridgeData[3][3] = String.valueOf(proSimpleInfo.getProSimpleBridgeTunnel().getMediumBridgeLength());
			bridgeData[3][4] = "中隧道（座）";
			bridgeData[3][5] = String.valueOf(proSimpleInfo.getProSimpleBridgeTunnel().getMediumTunnelNum());
			bridgeData[3][6] = "累计长度（m）";
			bridgeData[3][7] = String.valueOf(proSimpleInfo.getProSimpleBridgeTunnel().getMediumTunnelLength());
			bridgeData[4][4] = "短隧道（座）";
			bridgeData[4][5] = String.valueOf(proSimpleInfo.getProSimpleBridgeTunnel().getShortTunnelNum());
			bridgeData[4][6] = "累计长度（m）";
			bridgeData[4][7] = String.valueOf(proSimpleInfo.getProSimpleBridgeTunnel().getShortTunnelLength());
			proSimpleInfo.setExportBridgeData(bridgeData);
			
			String[][] exportSupervision = new String[3][8];
			exportSupervision[0][0] = "施工、监理单位";
			exportSupervision[1][0] = "施工合同段（个）";
			exportSupervision[1][1] = String.valueOf(proSimpleInfo.getProSimpleSupervision().getContractSectionNum());
			exportSupervision[1][3] = "监理模式";
			exportSupervision[1][4] = proSimpleInfo.getProSimpleSupervision().getSupervisionMode();
			exportSupervision[1][6] = "监理合同段（个）";
			exportSupervision[1][7] = String.valueOf(proSimpleInfo.getProSimpleSupervision().getSupervisionSectionNum());
			exportSupervision[2][0] = "项目联系人";
			exportSupervision[2][1] = proSimpleInfo.getProSimpleSupervision().getProjectContactUsername();
			exportSupervision[2][3] = "联系电话";
			exportSupervision[2][4] = proSimpleInfo.getProSimpleSupervision().getProjectContactPhone();
			proSimpleInfo.setExportSupervision(exportSupervision);
			
			
			ExportExcel export = new ExportExcel(headTitle, title, true, proSimpleInfo);

			export.write(response, filename);

			export.dispose();

			logger.debug("Export success.");

		} catch (Exception e) {

		}
	}

	public static void main(String[] args) throws IOException {

		// create a new file
		FileOutputStream out = new FileOutputStream("D:/workbook.xls");
		// create a new workbook
		HSSFWorkbook wb = new HSSFWorkbook();
		// create a new sheet
		HSSFSheet sheet = wb.createSheet();
		// 设置横版
		sheet.getPrintSetup().setLandscape(true);
		// 2.model
		HSSFRow row = sheet.createRow(2);
		row.setHeightInPoints(20);
		HSSFCell cell = row.createCell(2);
		HSSFFont cnFont = wb.createFont();
		cnFont.setFontHeightInPoints((short) 10);
		// font.setFontName("汉仪报宋简");
		cnFont.setFontName("隶书");
		HSSFCellStyle cnStyle = wb.createCellStyle();
		cnStyle.setFont(cnFont);
		cell.setCellStyle(cnStyle);
		HSSFRichTextString richText = new HSSFRichTextString("中文字体测试");
		cell.setCellValue(richText);
		HSSFCell enCell = row.createCell(3);
		HSSFFont enFont = wb.createFont();
		enFont.setFontHeightInPoints((short) 10);
		enFont.setFontName("Arial Black");
		HSSFCellStyle enStyle = wb.createCellStyle();
		enStyle.setFont(enFont);
		enCell.setCellStyle(enStyle);
		enCell.setCellValue(new HSSFRichTextString("English font test"));
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 4000);

		// 3.output
		sheet.setDisplayGridlines(false);
		sheet.setPrintGridlines(false);
		HSSFPrintSetup printSetup = sheet.getPrintSetup();
		// A4纸
		printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		wb.write(out);
		out.close();
	}
}