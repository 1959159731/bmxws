/**
 * 
 */
package com.platform.common.utils.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.platform.common.utils.Encodes;
import com.platform.common.utils.Reflections;
import com.platform.common.utils.excel.annotation.ExcelField;
import com.platform.modules.project.entity.ProSimpleInfo;
import com.platform.modules.sys.utils.DictUtils;

/**
 * 导出Excel文件（导出“XLSX”格式，支持大数据量导出   @see org.apache.poi.ss.SpreadsheetVersion）
 * @author ThinkGem
 * @version 2013-04-21
 */
public class ExportExcel {
	
	private static Logger log = LoggerFactory.getLogger(ExportExcel.class);
			
	/**
	 * 工作薄对象
	 */
	private SXSSFWorkbook wb;
	
	/**
	 * 工作表对象
	 */
	private Sheet sheet;
	
	/**
	 * 样式列表
	 */
	private Map<String, CellStyle> styles;
	
	/**
	 * 当前行号
	 */
	private int rownum;
	
	/**
	 * 注解列表（Object[]{ ExcelField, Field/Method }）
	 */
	List<Object[]> annotationList = Lists.newArrayList();
	
	/**
	 * 构造函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param cls 实体对象，通过annotation.ExportField获取标题
	 */
	public ExportExcel(String title, Class<?> cls){
		this(title, cls, 1);
	}
	
	/**
	 * 构造函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param cls 实体对象，通过annotation.ExportField获取标题
	 * @param type 导出类型（1:导出数据；2：导出模板）
	 * @param groups 导入分组
	 */
	public ExportExcel(String title, Class<?> cls, int type, int... groups){
		// Get annotation field 
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs){
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==type)){
				if (groups!=null && groups.length>0){
					boolean inGroup = false;
					for (int g : groups){
						if (inGroup){
							break;
						}
						for (int efg : ef.groups()){
							if (g == efg){
								inGroup = true;
								annotationList.add(new Object[]{ef, f});
								break;
							}
						}
					}
				}else{
					annotationList.add(new Object[]{ef, f});
				}
			}
		}
		// Get annotation method
		Method[] ms = cls.getDeclaredMethods();
		for (Method m : ms){
			ExcelField ef = m.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==type)){
				if (groups!=null && groups.length>0){
					boolean inGroup = false;
					for (int g : groups){
						if (inGroup){
							break;
						}
						for (int efg : ef.groups()){
							if (g == efg){
								inGroup = true;
								annotationList.add(new Object[]{ef, m});
								break;
							}
						}
					}
				}else{
					annotationList.add(new Object[]{ef, m});
				}
			}
		}
		// Field sorting
		Collections.sort(annotationList, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((ExcelField)o1[0]).sort()).compareTo(
						new Integer(((ExcelField)o2[0]).sort()));
			};
		});
		// Initialize
		List<String> headerList = Lists.newArrayList();
		for (Object[] os : annotationList){
			String t = ((ExcelField)os[0]).title();
			// 如果是导出，则去掉注释
			if (type==1){
				String[] ss = StringUtils.split(t, "**", 2);
				if (ss.length==2){
					t = ss[0];
				}
			}
			headerList.add(t);
		}
		initialize(title, headerList);
	}
	
	
	/**
	 * 构造函数(用来下载上传模板)
	 * @param title
	 */
	public ExportExcel(String[] title) {
		
		this.wb = new SXSSFWorkbook(500);
		this.sheet = wb.createSheet("Export");
		this.styles = createStyles(wb);
		Row headerRow = sheet.createRow(rownum++);
		headerRow.setHeightInPoints(16);
		
		for (int i = 0; i < title.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(styles.get("header"));
			String[] ss = StringUtils.split(title[i], "**", 2);
			if (ss.length==2){
				cell.setCellValue(ss[0]);
				Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
						new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
				comment.setString(new XSSFRichTextString(ss[1]));
				cell.setCellComment(comment);
			}else{
				cell.setCellValue(title[i]);
			}
			sheet.autoSizeColumn(i);
		}
		
		for (int i = 0; i < title.length; i++) {  
			int colWidth = sheet.getColumnWidth(i)*2;
	        sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
		}
		log.debug("Initialize success.");
	}
	
	
	/**
	 * 构造函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headers 表头数组
	 */
	public ExportExcel(String title, String[] headers) {
		initialize(title, Lists.newArrayList(headers));
	}
	
	/**
	 * 构造函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 */
	public ExportExcel(String title, List<String> headerList) {
		initialize(title, headerList);
	}
	
	/**
	 * 初始化函数
	 * @param title 表格标题，传“空值”，表示无标题
	 * @param headerList 表头列表
	 */
	private void initialize(String title, List<String> headerList) {
		this.wb = new SXSSFWorkbook(500);
		this.sheet = wb.createSheet("Export");
		this.styles = createStyles(wb);
		// Create title
		if (StringUtils.isNotBlank(title)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), titleRow.getRowNum(), headerList.size()-1));
		}
		// Create header
		if (headerList == null){
			throw new RuntimeException("headerList not null!");
		}
		Row headerRow = sheet.createRow(rownum++);
		headerRow.setHeightInPoints(16);
		for (int i = 0; i < headerList.size(); i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellStyle(styles.get("header"));
			String[] ss = StringUtils.split(headerList.get(i), "**", 2);
			if (ss.length==2){
				cell.setCellValue(ss[0]);
				Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
						new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
				comment.setString(new XSSFRichTextString(ss[1]));
				cell.setCellComment(comment);
			}else{
				cell.setCellValue(headerList.get(i));
			}
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < headerList.size(); i++) {  
			int colWidth = sheet.getColumnWidth(i)*2;
	        sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);  
		}
		log.debug("Initialize success.");
	}
	
	/**
	 * 创建表格样式
	 * @param wb 工作薄对象
	 * @return 样式列表
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont);
		style.setWrapText(true);
		styles.put("title", style);

		style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = wb.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setWrapText(true);
		styles.put("dataLeft", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		style.setWrapText(true);
		styles.put("dataLeftTop", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setWrapText(true);
		styles.put("dataCenter", style);

		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		style.setWrapText(true);
		styles.put("dataRight", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
//		style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);
		
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("title"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		titleFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		style.setFont(dataFont);
		styles.put("fujian", style);

		return styles;
	}

	/**
	 * 添加一行
	 * @return 行对象
	 */
	public Row addRow(){
		return sheet.createRow(rownum++);
	}
	

	/**
	 * 添加一个单元格
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 添加值
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val){
		return this.addCell(row, column, val, 0, Class.class);
	}
	
	/**
	 * 添加一个单元格
	 * @param row 添加的行
	 * @param column 添加列号
	 * @param val 添加值
	 * @param align 对齐方式（1：靠左；2：居中；3：靠右）
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType){
		Cell cell = row.createCell(column);
		String cellFormatString = "@";
		try {
			if(val == null){
				cell.setCellValue("");
			}else if(fieldType != Class.class){
				cell.setCellValue((String)fieldType.getMethod("setValue", Object.class).invoke(null, val));
			}else{
				if(val instanceof String) {
					cell.setCellValue((String) val);
				}else if(val instanceof Integer) {
					cell.setCellValue((Integer) val);
					cellFormatString = "0";
				}else if(val instanceof Long) {
					cell.setCellValue((Long) val);
					cellFormatString = "0";
				}else if(val instanceof Double) {
					cell.setCellValue((Double) val);
					cellFormatString = "0.00";
				}else if(val instanceof Float) {
					cell.setCellValue((Float) val);
					cellFormatString = "0.00";
				}else if(val instanceof Date) {
					cell.setCellValue((Date) val);
					cellFormatString = "yyyy-MM-dd HH:mm";
				}else {
					cell.setCellValue((String)Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), 
						"fieldtype."+val.getClass().getSimpleName()+"Type")).getMethod("setValue", Object.class).invoke(null, val));
				}
			}
			if (val != null){
				CellStyle style = styles.get("data_column_"+column);
				if (style == null){
					style = wb.createCellStyle();
					// style.cloneStyleFrom(styles.get("data"+(align>=1&&align<=3?align:"")));
			        style.setDataFormat(wb.createDataFormat().getFormat(cellFormatString));
					styles.put("data_column_" + column, style);
				}
				cell.setCellStyle(style);
			}
		} catch (Exception ex) {
			log.info("Set cell value ["+row.getRowNum()+","+column+"] error: " + ex.toString());
			cell.setCellValue(val.toString());
		}
		return cell;
	}

	/**
	 * 添加数据（通过annotation.ExportField添加数据）
	 * @return list 数据列表
	 */
	public <E> ExportExcel setDataList(List<E> list){
		for (E e : list){
			int colunm = 0;
			Row row = this.addRow();
			StringBuilder sb = new StringBuilder();
			for (Object[] os : annotationList){
				ExcelField ef = (ExcelField)os[0];
				Object val = null;
				// Get entity value
				try{
					if (StringUtils.isNotBlank(ef.value())){
						val = Reflections.invokeGetter(e, ef.value());
					}else{
						if (os[1] instanceof Field){
							val = Reflections.invokeGetter(e, ((Field)os[1]).getName());
						}else if (os[1] instanceof Method){
							val = Reflections.invokeMethod(e, ((Method)os[1]).getName(), new Class[] {}, new Object[] {});
						}
					}
					// If is dict, get dict label
					if (StringUtils.isNotBlank(ef.dictType())){
						val = DictUtils.getDictLabel(val==null?"":val.toString(), ef.dictType(), "");
					}
				}catch(Exception ex) {
					// Failure to ignore
					log.info(ex.toString());
					val = "";
				}
				this.addCell(row, colunm++, val, ef.align(), ef.fieldType());
				sb.append(val + ", ");
			}
			log.debug("Write success: ["+row.getRowNum()+"] "+sb.toString());
		}
		return this;
	}
	
	/**
	 * 输出数据流
	 * @param os 输出数据流
	 */
	public ExportExcel write(OutputStream os) throws IOException{
		wb.write(os);
		return this;
	}
	
	/**
	 * 输出到客户端
	 * @param fileName 输出文件名
	 */
	public ExportExcel write(HttpServletResponse response, String fileName) throws IOException{
		response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(fileName));
		write(response.getOutputStream());
		return this;
	}
	
	/**
	 * 输出到文件
	 * @param fileName 输出文件名
	 */
	public ExportExcel writeFile(String name) throws FileNotFoundException, IOException{
		FileOutputStream os = new FileOutputStream(name);
		this.write(os);
		return this;
	}
	
	/**
	 * 清理临时文件
	 */
	public ExportExcel dispose(){
		wb.dispose();
		return this;
	}
	
	/**
	 * 初始化表头信息，可共用
	 * @param titleHeader
	 * @param title
	 * @param printType
	 * @param proSimpleInfo
	 */
	public ExportExcel(String fujian, String title, String projectName, int colLength, boolean printType){
		init(fujian, title, projectName, colLength, printType);
	}
	
	/**
	 * 初始化表头信息，(中心试验室用)
	 * @param titleHeader
	 * @param title
	 * @param printType
	 * @param proSimpleInfo
	 */
	public ExportExcel(String fujian, String title, int colLength, boolean printType){
		init(fujian, title, colLength, printType);
	}
	
	
	/**
	 * 初始化表头信息，(中心试验室用)
	 * @param titleHeader
	 * @param title
	 * @param printType
	 * @param proSimpleInfo
	 */
	private void init(String titleHeader, String title, int colLength, boolean printType) {
		this.wb = new SXSSFWorkbook(500);
		this.sheet = wb.createSheet("Export");
		if(printType){
			this.sheet.getPrintSetup().setLandscape(printType);
		}
		this.styles = createStyles(wb);
		
		// 创建附件名
		if (StringUtils.isNotBlank(titleHeader)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(25);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("fujian"));
			titleCell.setCellValue(titleHeader);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), 0, colLength - 1));
		}
		
		// 创建表头
		if (StringUtils.isNotBlank(title)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), 0, colLength - 1));
		}
		
		log.debug("Initialize success.");
	}
	
	
	/**
	 * 设置tableTop(中心试验室)
	 * @param tableTitle
	 */
	public ExportExcel setTableTop(String[] tableTitle){
		for (int i = 0; i < 4; i++) {
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(25);
			for (int j = 0; j < 5; j++) {
				if(i == 0){
					Cell titleCell = titleRow.createCell(j);
					titleCell.setCellStyle(styles.get("dataCenter"));
					if(j == 0){
						titleCell.setCellValue("合同段");
					} else if(j == 1){
						titleCell.setCellValue(tableTitle[0]);
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum(), 1, 4));
					} else {
						titleCell.setCellValue("");
					}
				}
				
				if(i == 1){
					Cell titleCell = titleRow.createCell(j);
					titleCell.setCellStyle(styles.get("dataCenter"));
					if(j == 0){
						titleCell.setCellValue("母体试验室");
					} else if(j == 1){
						titleCell.setCellValue(tableTitle[1]);
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum(), 1, 4));
					} else {
						titleCell.setCellValue("");
					}
				}
				
				if(i == 2){
					Cell titleCell = titleRow.createCell(j);
					titleCell.setCellStyle(styles.get("dataCenter"));
					if(j == 0){
						titleCell.setCellValue("资质编号");
					} else if(j == 1){
						titleCell.setCellValue(tableTitle[2]);
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum(), 1, 2));
					} else if(j == 2){
						titleCell.setCellValue("");
					} else if(j == 3) {
						titleCell.setCellValue("授权负责人");
					} else {
						titleCell.setCellValue(tableTitle[3]);
					}
				}
				
				if(i == 3){
					Cell titleCell = titleRow.createCell(j);
					titleCell.setCellStyle(styles.get("dataCenter"));
					if(j == 0){
						titleCell.setCellValue("备案人员");
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum(), 0, 4));
					} else {
						titleCell.setCellValue("");
					}
				}
				
				sheet.autoSizeColumn(j);
			}
			
		}
		return this;
	}
	
	
	/**
	 * 添加数据
	 * @param data
	 * @return
	 */
	public ExportExcel setDataArrayForCenter(String[][] data){
		Row row;
		Cell cell;
		if(data != null && data.length > 0){
			for (int i = 0; i < data.length; i++) {
				row = sheet.createRow(rownum++);
				row.setHeightInPoints(25);
				for (int j = 0; j < data[i].length; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(styles.get("dataCenter"));
					cell.setCellValue(data[i][j]);
					sheet.autoSizeColumn(j);
				}
			}
		}
		return this;
	}
	
	
	/**
	 * 设置text （中心试验室）
	 * @param tableTitle
	 */
	public ExportExcel setText(String text){
		
		String[][] data = {
						  {"", text, "", "", "" },
						  {"授", "", "", "", "" },
						  {"权", "", "", "", "" },
						  {"开", "", "", "", "" },
						  {"展", "", "", "", "" },
						  {"的", "", "", "", "" },
						  {"试", "", "", "", "" },
						  {"验", "", "", "", "" },
						  {"检", "", "", "", "" },
						  {"测", "", "", "", "" },
						  {"项", "", "", "", "" },
						  {"目", "", "", "", "" },
						  {"及", "", "", "", "" },
						  {"参", "", "", "", "" },
						  {"数", "", "", "", "" },
						  {"", "", "", "", "" }
						 };
		
		String title = "";
		for (int i = 0; i < data.length; i++) {
			if(i == data.length - 1){
				title += data[i][0];
			} else {
				title += data[i][0] + "\r\n";
			}
		}
		
		// tableTitle
		if(text != null){
			for (int i = 0; i < data.length; i++) {
				Row textRow = sheet.createRow(rownum++);
				textRow.setHeightInPoints(25);
				
				String[] dataOne = data[i];
				if(i == 0){
					Cell titleCell = textRow.createCell(0);
					titleCell.setCellStyle(styles.get("dataCenter"));
					titleCell.setCellValue(title);
					sheet.addMergedRegion(new CellRangeAddress(textRow.getRowNum(),
					 		textRow.getRowNum() + 15, 0, 0));
				} else {
					Cell titleCell = textRow.createCell(0);
					titleCell.setCellStyle(styles.get("dataCenter"));
					titleCell.setCellValue("");
				}
				
				for (int j = 1; j < dataOne.length; j++) {
					Cell titleCell = textRow.createCell(j);
					titleCell.setCellStyle(styles.get("dataCenter"));
					titleCell.setCellValue(dataOne[j]);
					if(i == 0 && j == 1 ){
						titleCell.setCellStyle(styles.get("dataLeftTop"));
						sheet.addMergedRegion(new CellRangeAddress(textRow.getRowNum(),
								textRow.getRowNum() + 15, 1, 4));
					}
					sheet.autoSizeColumn(i);
				}
			}
		}
		return this;
	}
	
	
	/**
	 * 初始化表头信息，可共用
	 * @param titleHeader
	 * @param title
	 * @param printType
	 * @param proSimpleInfo
	 */
	private void init(String titleHeader, String title, String projectName, int colLength, boolean printType) {
		this.wb = new SXSSFWorkbook(500);
		this.sheet = wb.createSheet("Export");
		if(printType){
			this.sheet.getPrintSetup().setLandscape(printType);
		}
		this.styles = createStyles(wb);
		
		// 创建附件名
		if (StringUtils.isNotBlank(titleHeader)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(25);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("fujian"));
			titleCell.setCellValue(titleHeader);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), 0, colLength - 1));
		}
		
		// 创建表头
		if (StringUtils.isNotBlank(title)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), 0, colLength - 1));
		}
		
		// 创建表头下面的项目名称
		if (StringUtils.isNotBlank(projectName)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(25);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("fujian"));
			titleCell.setCellValue(projectName);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), 0, colLength - 1));
		}
		
		log.debug("Initialize success.");
	}
	
	
	/**
	 * 设置tableTitle Normal
	 * @param tableTitle
	 */
	public ExportExcel setTableTitle(String[] tableTitle){
		// tableTitle
		if(tableTitle != null){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(25);
			for (int i = 0; i < tableTitle.length; i++) {
				Cell titleCell = titleRow.createCell(i);
				titleCell.setCellStyle(styles.get("dataCenter"));
				titleCell.setCellValue(tableTitle[i]);
				sheet.autoSizeColumn(i);
			}
		}
		return this;
	}
	
	
	
	/**
	 * 设置tableTitle 特殊（施工单位信息）
	 * @param tableTitle
	 */
	public ExportExcel setTableTitleForConstruction(String[][] tableTitle){
		// tableTitle
		for (int i = 0; i < tableTitle.length; i++) {
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(25);
			for (int j = 0; j < tableTitle[i].length; j++) {
				
				if(i == 0){
					Cell titleCell = titleRow.createCell(j);
					titleCell.setCellStyle(styles.get("dataCenter"));
					titleCell.setCellValue(tableTitle[i][j]);
					if(j == 5){
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum(), 5, 11));
					}
					if(j <= 4){
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum()+1, j, j));
					}
				}
				
				if(i == 1){
					Cell titleCell = titleRow.createCell(j);
					titleCell.setCellStyle(styles.get("dataCenter"));
					titleCell.setCellValue(tableTitle[i][j]);
				}
				
				sheet.autoSizeColumn(j);
			}
			
		}
		return this;
	}
	
	
	/**
	 * 设置tableTitle 特殊（施工单位履约对照信息）
	 * @param tableTitle
	 */
	public ExportExcel setTableTitleForConstructionControlTable(String[][] tableTitle){
		// tableTitle
		for (int i = 0; i < tableTitle.length; i++) {
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(25);
			for (int j = 0; j < tableTitle[i].length; j++) {
				
				if(i == 0){
					Cell titleCell = titleRow.createCell(j);
					titleCell.setCellStyle(styles.get("dataCenter"));
					titleCell.setCellValue(tableTitle[i][j]);
					if(j == 0){
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum()+1, j, j));
					}
					if(j == 2){
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum(), 1, 3));
					}
					
					
					// 监理单位合同人员与进场人员对照信息
					if(j == 4 && tableTitle[i].length == 12){
							sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
									titleRow.getRowNum(), 4, 10));
					}
					
					// 施工单位合同人员与进场人员对照信息
					if(j == 4 && tableTitle[i].length == 11){
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum(), 4, 9));
					}
					
					if(j == tableTitle[i].length - 1){
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum()+1, j, j));
					}
				}
				
				if(i == 1){
					Cell titleCell = titleRow.createCell(j);
					titleCell.setCellStyle(styles.get("dataCenter"));
					titleCell.setCellValue(tableTitle[i][j]);
				}
				
				sheet.autoSizeColumn(j);
			}
			
		}
		return this;
	}
	
	
	/**
	 * 设置tableTitle 特殊（监理单位履约对照信息）
	 * @param tableTitle
	 */
	public ExportExcel setTableTitleForSupervisonContractTable(String[][] tableTitle){
		// tableTitle
		for (int i = 0; i < tableTitle.length; i++) {
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(25);
			for (int j = 0; j < tableTitle[i].length; j++) {
				
				if(i == 0){
					Cell titleCell = titleRow.createCell(j);
					titleCell.setCellStyle(styles.get("dataCenter"));
					titleCell.setCellValue(tableTitle[i][j]);
					if(j == 0){
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum()+1, j, j));
					}
					if(j == 2){
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum(), 1, 3));
					}
					
					// 监理单位合同人员与进场人员对照信息
					if(j == 4){
							sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
									titleRow.getRowNum(), 4, 7));
					}
					
					// 施工单位合同人员与进场人员对照信息
					if(j >= 5){
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum()+1, j, j));
					}
				}
				
				if(i == 1){
					Cell titleCell = titleRow.createCell(j);
					titleCell.setCellStyle(styles.get("dataCenter"));
					titleCell.setCellValue(tableTitle[i][j]);
				}
				
				sheet.autoSizeColumn(j);
			}
			
		}
		return this;
	}
	
	
	/**
	 * 设置tableTitle 特殊（第三方检测单位人员情况及工作内容）
	 * @param tableTitle
	 */
	public ExportExcel setTableTitleForCheckUser(String[][] tableTitle){
		// tableTitle
		for (int i = 0; i < tableTitle.length; i++) {
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(25);
			for (int j = 0; j < tableTitle[i].length; j++) {
				
				if(i == 0){
					Cell titleCell = titleRow.createCell(j);
					titleCell.setCellStyle(styles.get("dataCenter"));
					titleCell.setCellValue(tableTitle[i][j]);
					if(j == 0){
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum(), 0, 1));
					}
					if(j == 2){
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum(), 2, 3));
					}
					if(j >= 4){
						sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
								titleRow.getRowNum()+1, j, j));
					}
				}
				
				if(i == 1){
					Cell titleCell = titleRow.createCell(j);
					titleCell.setCellStyle(styles.get("dataCenter"));
					titleCell.setCellValue(tableTitle[i][j]);
				}
				
				sheet.autoSizeColumn(j);
			}
			
		}
		return this;
	}
	
	
	
	/**
	 * 设置列宽
	 * @param length
	 * @return
	 */
	public ExportExcel setColLength(int[] length){
		//设置列宽
		for(int j = 0; j < length.length; j++) {
		    sheet.setColumnWidth(j, MSExcelUtil.pixel2WidthUnits(length[j])); 
		}
		
		return this;
	}
	
	
	/**
	 * 添加数据
	 * @param data
	 * @return
	 */
	public ExportExcel setDataArray(String[][] data){
		Row row;
		Cell cell;
		if(data != null && data.length > 0){
			for (int i = 0; i < data.length; i++) {
				row = sheet.createRow(rownum++);
				for (int j = 0; j < data[i].length; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(styles.get("dataCenter"));
					cell.setCellValue(data[i][j]);
					sheet.autoSizeColumn(j);
				}
			}
		}
		return this;
	}
	
	
	/**
	 * 添加数据(第三方检测单位新人员情况及工作内容)
	 * @param data
	 * @return
	 */
	public ExportExcel setDataArrayForCheckUser(String[][] data, int rowSpan){
		Row row;
		Cell cell;
		if(data != null && data.length > 0){
			for (int i = 0; i < data.length; i++) {
				row = sheet.createRow(rownum++);
				row.setHeightInPoints(25);
				for (int j = 0; j < data[i].length; j++) {
					cell = row.createCell(j);
					cell.setCellStyle(styles.get("dataCenter"));
					cell.setCellValue(data[i][j]);
					sheet.autoSizeColumn(j);
					if(i == 0 && j >= 4){
						sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(),
								row.getRowNum() + rowSpan - 1, j, j));
					}
				}
			}
		}
		return this;
	}
	
	/**
	 * 添加页脚信息
	 * @param footer
	 * @return
	 */
	public ExportExcel setFooter(String footer, int length){
		Row row = sheet.createRow(rownum++);
		row.setHeightInPoints(25);
		Cell cell = row.createCell(0);
		cell.setCellStyle(styles.get("fujian"));
		cell.setCellValue(footer);
		sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(),
				row.getRowNum(), 0, length - 1));
		return this;
	}
	
	
	
	/**
	 * 项目信息总表 导出 构造函数
	 * @param titleHeader
	 * @param title
	 * @param printType
	 * @param proSimpleInfo
	 */
	public ExportExcel(String titleHeader, String title, boolean printType, ProSimpleInfo proSimpleInfo){
		exportProjectSimpleInfo(titleHeader, title, printType, proSimpleInfo);
	}
	
	/**
	 * 项目信息总表 导出
	 * @param titleHeader
	 * @param title
	 * @param printType
	 * @param proSimpleInfo
	 */
	private void exportProjectSimpleInfo(String titleHeader, String title, boolean printType, ProSimpleInfo proSimpleInfo) {
		this.wb = new SXSSFWorkbook(500);
		this.sheet = wb.createSheet("Export");
		this.sheet.getPrintSetup().setLandscape(printType);
		this.styles = createStyles(wb);
		
		// 创建附件名
		if (StringUtils.isNotBlank(titleHeader)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(22);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("fujian"));
			titleCell.setCellValue(titleHeader);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), titleRow.getRowNum(), 7));
		}
		
		// 创建表头
		if (StringUtils.isNotBlank(title)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), 0, 7));
		}
		
		// table Row-01
		Row dataRow = sheet.createRow(rownum++);
		dataRow.setHeightInPoints(23);
		Cell dataCell = dataRow.createCell(0);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("项目名称");
		
		dataCell = dataRow.createCell(1);
		dataCell.setCellStyle(styles.get("dataLeft"));
		dataCell.setCellValue(proSimpleInfo.getProjectName());
		sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
				dataRow.getRowNum(), 1, 2));
		
		dataCell = dataRow.createCell(2);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("");
		
		dataCell = dataRow.createCell(3);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("项目简称");
		
		dataCell = dataRow.createCell(4);
		dataCell.setCellStyle(styles.get("dataLeft"));
		dataCell.setCellValue(proSimpleInfo.getProjectSimpleName());
		
		dataCell = dataRow.createCell(5);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("项目类型");
		
		dataCell = dataRow.createCell(6);
		dataCell.setCellStyle(styles.get("dataLeft"));
		dataCell.setCellValue(DictUtils.getDictLabel(proSimpleInfo.getProjectType(), "project_simple_type", ""));
		sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
				dataRow.getRowNum(), 6, 7));
		
		dataCell = dataRow.createCell(7);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("");
		
		// table Row-02
		dataRow = sheet.createRow(rownum++);
		dataRow.setHeightInPoints(23);
		dataCell = dataRow.createCell(0);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("现场管理机构");
		
		dataCell = dataRow.createCell(1);
		dataCell.setCellStyle(styles.get("dataLeft"));
		dataCell.setCellValue(proSimpleInfo.getSiteManagementAgencies());
		sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
				dataRow.getRowNum(), 1, 2));
		
		dataCell = dataRow.createCell(2);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("");
		
		dataCell = dataRow.createCell(3);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("设计单位");
		
		dataCell = dataRow.createCell(4);
		dataCell.setCellStyle(styles.get("dataLeft"));
		dataCell.setCellValue(proSimpleInfo.getDesignUnit());
		sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
				dataRow.getRowNum(), 4, 5));
		
		dataCell = dataRow.createCell(5);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("");
		
		dataCell = dataRow.createCell(6);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("项目法人");
		
		dataCell = dataRow.createCell(7);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue(proSimpleInfo.getProjectLegal());
		
		// table Row-03
		dataRow = sheet.createRow(rownum++);
		dataRow.setHeightInPoints(23);
		dataCell = dataRow.createCell(0);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("");
		sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
				dataRow.getRowNum(), 0, 1));
		
		dataCell = dataRow.createCell(1);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("");
		
		
		dataCell = dataRow.createCell(2);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("审批单位");
		sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
				dataRow.getRowNum(), 2, 3));
		
		dataCell = dataRow.createCell(3);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("");
		
		
		dataCell = dataRow.createCell(4);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("批准文号");
		sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
				dataRow.getRowNum(), 4, 5));
		
		dataCell = dataRow.createCell(5);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("");
		
		dataCell = dataRow.createCell(6);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("批准时间");
		sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
				dataRow.getRowNum(), 6, 7));
		
		dataCell = dataRow.createCell(7);
		dataCell.setCellStyle(styles.get("dataCenter"));
		dataCell.setCellValue("");
		
		// table Row-04-07
		String[][] appData = proSimpleInfo.getExportAppData();
		for (int i = 0; i < 4; i++) {
			dataRow = sheet.createRow(rownum++);
			dataRow.setHeightInPoints(23);
			for (int j = 0; j < 8; j++) {
				dataCell = dataRow.createCell(j);
				dataCell.setCellStyle(styles.get("dataCenter"));
				dataCell.setCellValue(appData[i][j]);
				if(j % 2 == 0){
					sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
							dataRow.getRowNum(), j, j+1));
				}
				if(j % 2 == 1){
					dataCell.setCellValue("");
				}
			}
		}
		
		// table normal info Row 08-10
		String[][] normalData = proSimpleInfo.getExportNormalData();
		for (int i = 0; i < 3; i++) {
			dataRow = sheet.createRow(rownum++);
			dataRow.setHeightInPoints(23);
			for (int j = 0; j < 8; j++) {
				dataCell = dataRow.createCell(j);
				dataCell.setCellStyle(styles.get("dataCenter"));
				dataCell.setCellValue(normalData[i][j]);
			}
		}
		
		// table normal info Row 11 - 15
		String[][] bridgeData = proSimpleInfo.getExportBridgeData();
		for (int i = 0; i < 5; i++) {
			dataRow = sheet.createRow(rownum++);
			dataRow.setHeightInPoints(23);
			for (int j = 0; j < 8; j++) {
				dataCell = dataRow.createCell(j);
				dataCell.setCellStyle(styles.get("dataCenter"));
				dataCell.setCellValue(bridgeData[i][j]);
				if(i == 0 && (j == 0 || j == 4)){
					sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
							dataRow.getRowNum(), j, j+3));
				}
			}
		}
		
		// table normal info Row 16-18
		String[][] exportSupervision = proSimpleInfo.getExportSupervision();
		for (int i = 0; i < 3; i++) {
			dataRow = sheet.createRow(rownum++);
			dataRow.setHeightInPoints(23);
			for (int j = 0; j < 8; j++) {
				dataCell = dataRow.createCell(j);
				dataCell.setCellStyle(styles.get("dataCenter"));
				dataCell.setCellValue(exportSupervision[i][j]);
				if(i == 0 && j == 0){
					sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
							dataRow.getRowNum(), j, j+3));
				}
				
				if((i == 1 || i == 2) && j == 1){
					sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
							dataRow.getRowNum(), j, j+1));
				}
				if( i == 1 && j == 4){
					sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
							dataRow.getRowNum(), j, j+1));
				}
				if( i == 2 && j == 4){
					sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
							dataRow.getRowNum(), j, j+3));
				}
			}
		}
		
		// 页脚备注信息
		if (StringUtils.isNotBlank("remark")){
			dataRow = sheet.createRow(rownum++);
			dataRow.setHeightInPoints(23);
			Cell remarkCell = dataRow.createCell(0);
			remarkCell.setCellStyle(styles.get("fujian"));
			remarkCell.setCellValue("注：1、隧道累计长度以单洞计；2、项目类型是指国家高速公路网、西部省际通道、省高速公路网、独立特大型结构物工程以及其他高速公路项目。");
			sheet.addMergedRegion(new CellRangeAddress(dataRow.getRowNum(),
					dataRow.getRowNum(), 0, 7));
		}
		
		//设置列宽
		for(int j = 0; j < 8; j++) {
		    sheet.setColumnWidth(j, MSExcelUtil.pixel2WidthUnits(108)); 
		}
		
		log.debug("Initialize success.");
	}

}
