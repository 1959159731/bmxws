package com.platform.modules.sys.utils;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfFont;
import com.platform.common.config.Global;
import com.platform.common.utils.Encodes;

public class WordUtils {
	
	private static Logger logger = LoggerFactory.getLogger(WordUtils.class);
	
	
	public static final String RECTANGLE_1 = "1";		// 竖版
	
	public static final String RECTANGLE_2 = "2";		// 横版
	
	
	/**
	 * 工作薄对象
	 */
	private Document document;
	
	/**
	 * 表头列数
	 */
	private int L;
	
	/**
	 * 工作表对象
	 */
	private Table table;
	
	
	/** 
	 * 标题字体
	 */
    private RtfFont titleFont = new RtfFont("黑体", 22, Font.BOLD, Color.BLACK);
    
    /** 
     * 正文字体
     */
    private RtfFont contextFont = new RtfFont("仿宋_GB2312", 12, Font.NORMAL, Color.BLACK);
    
    private HttpServletResponse response;
	
	public WordUtils(){
		
	}
	
	
	public WordUtils(String rectangleType){
		initialize(rectangleType);
	}


	private void initialize(String rectangleType) {
		Rectangle rectangle = new Rectangle(PageSize.A4);
		if(rectangleType.equals(WordUtils.RECTANGLE_1)){
			this.document = new Document(rectangle, 50, 50, 70, 90);
		} else {
	        this.document = new Document(rectangle.rotate(), 60, 60, 90, 90);		// 创建word 文档,并旋转，使其横向
		}
		
		logger.debug("Create document success ------------------");
	}
	
	/**
	 * safe 
	 * 附件一： 安全生产管理
	 * @param title
	 * @param fuTitle
	 * @param widths
	 * @param head
	 * @param dataList
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public WordUtils exportSafeProManagerPerson(String title, String[] fuTitle, int[] widths, List<String> head,
			List<String[]> dataList, List<String> imgPath, HttpServletResponse response) throws IOException, DocumentException {
		
		RtfWriter2.getInstance(this.document, response.getOutputStream());
		this.document.open();
		
		// 
		String fujianStr = "   附件  1";
        
        // fuTitle
        String contextString = " 项目名称：" + fuTitle[0] + "    合同段编号：" + fuTitle[1] + "    施工单位：" + fuTitle[2] + " ";

		
        // Table
		L = head.size();
		this.table = new Table(head.size(), 5 + dataList.size());
        table.setWidth(100);						// 表格所占页面宽度 
        table.setWidths(widths);
        table.setAlignment(Element.ALIGN_CENTER);	// 居中显示
        table.setAutoFillEmptyCells(true);			// 自动填满 
        
        
        // 附件标题
        Cell[] leftTitleCell = new Cell[head.size()];	
		leftTitleCell[0] = new Cell(new Phrase(fujianStr, contextFont));
		leftTitleCell[0].setColspan(7);
		leftTitleCell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
		leftTitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
		leftTitleCell[0].disableBorderSide(2);
        table.addCell(leftTitleCell[0]);
        
        // 标题
        Cell[] TitleCell = new Cell[head.size()];	
        TitleCell[0] = new Cell(new Phrase(title, titleFont));
        TitleCell[0].setColspan(7);
        TitleCell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        TitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        TitleCell[0].disableBorderSide(2);
        table.addCell(TitleCell[0]);
        
        // 换行
        Cell[] nullCell = new Cell[head.size()];	
        nullCell[0] = new Cell(new Phrase("", titleFont));
        nullCell[0].setColspan(7);
        nullCell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        nullCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        nullCell[0].disableBorderSide(2);
        table.addCell(nullCell[0]);
        
        // 附标题
        Cell[] fuTitleCell = new Cell[head.size()];	
        fuTitleCell[0] = new Cell(new Phrase(contextString, contextFont));
        fuTitleCell[0].setColspan(7);
        fuTitleCell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
        fuTitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        fuTitleCell[0].disableBorderSide(2);
        table.addCell(fuTitleCell[0]);
        
        // 表格标题
		Cell[] cellHeaders = new Cell[head.size()];	
		for (int i = 0; i < head.size(); i++) {
			cellHeaders[i] = new Cell(new Phrase(head.get(i), contextFont));
		}
		for (int i = 0; i < cellHeaders.length; i++) {	// 表格标题样式
            cellHeaders[i].setHorizontalAlignment(Element.ALIGN_CENTER);
            cellHeaders[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellHeaders[i]);
        }
		
		
		// 表格填充数据
		for (int i = 0; i < dataList.size(); i++) {	
			String[] strArr = dataList.get(i);
			for (int j = 0; j < strArr.length; j++) {
				String data = strArr[j];
				Paragraph contextCell = new Paragraph(data);
				if(data.trim().equals("") || data == null){
					contextCell = new Paragraph("\n");
					contextCell.setSpacingBefore(0);
				} else {
					contextCell.setSpacingBefore(13);
				}
				Cell cell = new Cell( contextCell );
	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            table.addCell(cell);
			}
		}
		
		// 将表格添加进文档
		this.document.add(table);
		
		// left title
		String footer = "  注：应后附按本表次序排列的职业证书扫描件。";
		Paragraph footerPara = new Paragraph(footer);
		footerPara.setAlignment(Element.ALIGN_LEFT);	// 正文格式对齐方式
		footerPara.setFont(contextFont);
		footerPara.setSpacingBefore(2);				// 与上一段落（标题）的行距
        this.document.add(footerPara);
		        
		        
        
        
        
        // 添加附件图片
        for (int i = 0; i < imgPath.size(); i++) {
        	Image img = Image.getInstance(Global.getConfig("cas.project.url") + imgPath.get(i));  
            document.add(img); 
		}
        
		this.response = response;
		logger.debug("export file is success ------------------");
		return this;
	}
	
	
	/**
	 * safe 
	 * 附件二： 特种设备操作人员
	 * @param title
	 * @param fuTitle
	 * @param widths
	 * @param head
	 * @param dataList
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public WordUtils exportSafeSpecEquOperPerson(String title, String[] fuTitle, int[] widths, List<String> head,
			List<String[]> dataList, List<String> imgPath, HttpServletResponse response) throws IOException, DocumentException {
		RtfWriter2.getInstance(this.document, response.getOutputStream());
		this.document.open();
		
		// 
		String fujianStr = "   附件  2";
        
        // fuTitle
        String contextString = " 项目名称：" + fuTitle[0] + "    合同段编号：" + fuTitle[1] + "    施工单位：" + fuTitle[2] + " ";

		
        // Table
		L = head.size();
		this.table = new Table(head.size(), 5 + dataList.size());
        table.setWidth(100);						// 表格所占页面宽度 
        table.setWidths(widths);
        table.setAlignment(Element.ALIGN_CENTER);	// 居中显示
        table.setAutoFillEmptyCells(true);			// 自动填满 
        
        
        // 附件标题
        Cell[] leftTitleCell = new Cell[head.size()];	
		leftTitleCell[0] = new Cell(new Phrase(fujianStr, contextFont));
		leftTitleCell[0].setColspan(8);
		leftTitleCell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
		leftTitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
		leftTitleCell[0].disableBorderSide(2);
        table.addCell(leftTitleCell[0]);
        
        // 标题
        Cell[] TitleCell = new Cell[head.size()];	
        TitleCell[0] = new Cell(new Phrase(title, titleFont));
        TitleCell[0].setColspan(8);
        TitleCell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        TitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        TitleCell[0].disableBorderSide(2);
        table.addCell(TitleCell[0]);
        
        // 换行
        Cell[] nullCell = new Cell[head.size()];	
        nullCell[0] = new Cell(new Phrase("", titleFont));
        nullCell[0].setColspan(8);
        nullCell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        nullCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        nullCell[0].disableBorderSide(2);
        table.addCell(nullCell[0]);
        
        // 附标题
        Cell[] fuTitleCell = new Cell[head.size()];	
        fuTitleCell[0] = new Cell(new Phrase(contextString, contextFont));
        fuTitleCell[0].setColspan(8);
        fuTitleCell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
        fuTitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        fuTitleCell[0].disableBorderSide(2);
        table.addCell(fuTitleCell[0]);
        
        // 表格标题
		Cell[] cellHeaders = new Cell[head.size()];	
		for (int i = 0; i < head.size(); i++) {
			cellHeaders[i] = new Cell(new Phrase(head.get(i), contextFont));
		}
		for (int i = 0; i < cellHeaders.length; i++) {	// 表格标题样式
            cellHeaders[i].setHorizontalAlignment(Element.ALIGN_CENTER);
            cellHeaders[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellHeaders[i]);
        }
		
		
		// 表格填充数据
		for (int i = 0; i < dataList.size(); i++) {	
			String[] strArr = dataList.get(i);
			for (int j = 0; j < strArr.length; j++) {
				String data = strArr[j];
				Paragraph contextCell = new Paragraph(data);
				if(data.trim().equals("") || data == null){
					contextCell = new Paragraph("\n");
					contextCell.setSpacingBefore(0);
				} else {
					contextCell.setSpacingBefore(13);
				}
				Cell cell = new Cell( contextCell );
	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            table.addCell(cell);
			}
		}
		
		// 将表格添加进文档
		this.document.add(table);
		
		// left title
		String footer = "  注：应后附按本表次序排列的执业证书扫描件。";
		Paragraph footerPara = new Paragraph(footer);
		footerPara.setAlignment(Element.ALIGN_LEFT);	// 正文格式对齐方式
		footerPara.setFont(contextFont);
		footerPara.setSpacingBefore(2);				// 与上一段落（标题）的行距
        this.document.add(footerPara);
		        
        // 添加附件图片
        for (int i = 0; i < imgPath.size(); i++) {
        	Image img = Image.getInstance(Global.getConfig("cas.project.url") + imgPath.get(i));  
            document.add(img); 
		}
        
		this.response = response;
		logger.debug("export file is success ------------------");
		return this;
	}
	
	/**
	 * safe 
	 * 附件三：特种作业人员
	 * @param title
	 * @param fuTitle
	 * @param widths
	 * @param head
	 * @param dataList
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public WordUtils exportSafeSpecOperPerson(String title, String[] fuTitle, int[] widths, List<String> head,
			List<String[]> dataList, List<String> imgPath, HttpServletResponse response) throws IOException, DocumentException {
		RtfWriter2.getInstance(this.document, response.getOutputStream());
		this.document.open();
		
		// 
		String fujianStr = "   附件  3";
        
        // fuTitle
        String contextString = " 项目名称：" + fuTitle[0] + "    合同段编号：" + fuTitle[1] + "    施工单位：" + fuTitle[2] + " ";

		
        // Table
		L = head.size();
		this.table = new Table(head.size(), 5 + dataList.size());
        table.setWidth(100);						// 表格所占页面宽度 
        table.setWidths(widths);
        table.setAlignment(Element.ALIGN_CENTER);	// 居中显示
        table.setAutoFillEmptyCells(true);			// 自动填满 
        
        
        // 附件标题
        Cell[] leftTitleCell = new Cell[head.size()];	
		leftTitleCell[0] = new Cell(new Phrase(fujianStr, contextFont));
		leftTitleCell[0].setColspan(7);
		leftTitleCell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
		leftTitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
		leftTitleCell[0].disableBorderSide(2);
        table.addCell(leftTitleCell[0]);
        
        // 标题
        Cell[] TitleCell = new Cell[head.size()];	
        TitleCell[0] = new Cell(new Phrase(title, titleFont));
        TitleCell[0].setColspan(7);
        TitleCell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        TitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        TitleCell[0].disableBorderSide(2);
        table.addCell(TitleCell[0]);
        
        // 换行
        Cell[] nullCell = new Cell[head.size()];	
        nullCell[0] = new Cell(new Phrase("", titleFont));
        nullCell[0].setColspan(7);
        nullCell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        nullCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        nullCell[0].disableBorderSide(2);
        table.addCell(nullCell[0]);
        
        // 附标题
        Cell[] fuTitleCell = new Cell[head.size()];	
        fuTitleCell[0] = new Cell(new Phrase(contextString, contextFont));
        fuTitleCell[0].setColspan(7);
        fuTitleCell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
        fuTitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        fuTitleCell[0].disableBorderSide(2);
        table.addCell(fuTitleCell[0]);
        
        // 表格标题
		Cell[] cellHeaders = new Cell[head.size()];	
		for (int i = 0; i < head.size(); i++) {
			cellHeaders[i] = new Cell(new Phrase(head.get(i), contextFont));
		}
		for (int i = 0; i < cellHeaders.length; i++) {	// 表格标题样式
            cellHeaders[i].setHorizontalAlignment(Element.ALIGN_CENTER);
            cellHeaders[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellHeaders[i]);
        }
		
		
		// 表格填充数据
		for (int i = 0; i < dataList.size(); i++) {	
			String[] strArr = dataList.get(i);
			for (int j = 0; j < strArr.length; j++) {
				String data = strArr[j];
				Paragraph contextCell = new Paragraph(data);
				if(data.trim().equals("") || data == null){
					contextCell = new Paragraph("\n");
					contextCell.setSpacingBefore(0);
				} else {
					contextCell.setSpacingBefore(13);
				}
				Cell cell = new Cell( contextCell );
	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            table.addCell(cell);
			}
		}
		
		// 将表格添加进文档
		this.document.add(table);
		
		// left title
		String footer = "  注：应后附按本表次序排列的执业证书扫描件。";
		Paragraph footerPara = new Paragraph(footer);
		footerPara.setAlignment(Element.ALIGN_LEFT);	// 正文格式对齐方式
		footerPara.setFont(contextFont);
		footerPara.setSpacingBefore(2);				// 与上一段落（标题）的行距
        this.document.add(footerPara);
		        
        // 添加附件图片
        for (int i = 0; i < imgPath.size(); i++) {
        	Image img = Image.getInstance(Global.getConfig("cas.project.url") + imgPath.get(i));  
            document.add(img); 
		}
        
		this.response = response;
		logger.debug("export file is success ------------------");
		return this;
	}
	
	
	/**
	 * safe
	 * 附件4: 特种设备
	 * @param title
	 * @param fuTitle
	 * @param widths
	 * @param head
	 * @param dataList
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public WordUtils exportSafeSpecEqui(String title, String[] fuTitle, int[] widths, List<String> head,
			List<String[][]> dataList, HttpServletResponse response) throws IOException, DocumentException {
		RtfWriter2.getInstance(this.document, response.getOutputStream());
		this.document.open();
		
		// 
		String fujianStr = "   附件  4";
        
        // fuTitle
        String contextString = " 项目名称：" + fuTitle[0] + "          合同段编号：" + fuTitle[1] + "       施工单位：" + fuTitle[2] + " ";

		
        // Table
		L = head.size();
		this.table = new Table(head.size(), 4 + dataList.size());
        table.setWidth(100);						// 表格所占页面宽度 
        table.setWidths(widths);
        table.setAlignment(Element.ALIGN_CENTER);	// 居中显示
        table.setAutoFillEmptyCells(true);			// 自动填满 
        
        
        // 附件标题
        Cell[] leftTitleCell = new Cell[head.size()];	
		leftTitleCell[0] = new Cell(new Phrase(fujianStr, contextFont));
		leftTitleCell[0].setColspan(14);
		leftTitleCell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
		leftTitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
		leftTitleCell[0].disableBorderSide(2);
        table.addCell(leftTitleCell[0]);
        
        // 标题
        Cell[] TitleCell = new Cell[head.size()];	
        TitleCell[0] = new Cell(new Phrase(title, titleFont));
        TitleCell[0].setColspan(14);
        TitleCell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        TitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        TitleCell[0].disableBorderSide(2);
        table.addCell(TitleCell[0]);
        
        // 附标题
        Cell[] fuTitleCell = new Cell[head.size()];	
        fuTitleCell[0] = new Cell(new Phrase(contextString, contextFont));
        fuTitleCell[0].setColspan(14);
        fuTitleCell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
        fuTitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        fuTitleCell[0].disableBorderSide(2);
        table.addCell(fuTitleCell[0]);
        
        // 表格标题
		Cell[] cellHeaders = new Cell[head.size()];	
		for (int i = 0; i < head.size(); i++) {
			cellHeaders[i] = new Cell(new Phrase(head.get(i), contextFont));
		}
		for (int i = 0; i < cellHeaders.length; i++) {	// 表格标题样式
            cellHeaders[i].setHorizontalAlignment(Element.ALIGN_CENTER);
            cellHeaders[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellHeaders[i]);
        }
		
		
		// 表格填充数据
		for (int i = 0; i < dataList.size(); i++) {	
			String[][] strArr = dataList.get(i);
			
			if(strArr.length == 1){
				for (int j = 0; j < strArr[0].length; j++) {
					String data = strArr[0][j];
					Paragraph contextCell = new Paragraph( data );
					contextCell.setSpacingBefore(18);
					Cell cell = new Cell( contextCell );
		            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table.addCell(cell);
				}
			} else {
				for (int j = 0; j < 9; j++) {
					if(j <= 8){
						String data = strArr[i][j];
						Paragraph contextCell = new Paragraph( data );
						contextCell.setSpacingBefore(18);
						Cell cell = new Cell( contextCell );
						cell.setRowspan(2);
			            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			            table.addCell(cell);
					}
				}
				
				for (int j = 0; j < 2; j++) {
					for (int k = 9; k < strArr[0].length; k++) {
						String data = strArr[j][k];
						Paragraph contextCell = new Paragraph( data );
						contextCell.setSpacingBefore(18);
						Cell cell = new Cell( contextCell );
			            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			            table.addCell(cell);
					}
				}
			}
			
		}
		
		// 将表格添加进文档
		this.document.add(table);
		
		// left title
		String footer = "注：特种设备指列入特种设备名录的设备,应由质监局特种设备监督检验机构检测并办理使用登记";
		Paragraph footerPara = new Paragraph(footer);
		footerPara.setAlignment(Element.ALIGN_LEFT);	// 正文格式对齐方式
		footerPara.setFont(contextFont);
		footerPara.setSpacingBefore(2);				// 与上一段落（标题）的行距
        this.document.add(footerPara);
		        
		        
		this.response = response;
		logger.debug("export file is success ------------------");
		return this;
	}
	
	

	/**
	 * safe
	 * 附件6：危险性较大工程管理
	 * @param title
	 * @param fuTitle
	 * @param widths
	 * @param head
	 * @param dataList
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public WordUtils exportSafeDangerProjectManager(String title, String[] fuTitle, int[] widths, List<String> head,
			List<String[]> dataList, HttpServletResponse response) throws IOException, DocumentException {
		RtfWriter2.getInstance(this.document, response.getOutputStream());
		this.document.open();
		
		// 
		String fujianStr = "   附件  6";
        
        // fuTitle
        String contextString = " 项目名称：" + fuTitle[0] + "          合同段编号：" + fuTitle[1] + "       施工单位：" + fuTitle[2] + " ";

		
        // Table
		L = head.size();
		this.table = new Table(head.size(), 4 + dataList.size());
        table.setWidth(100);						// 表格所占页面宽度 
        table.setWidths(widths);
        table.setAlignment(Element.ALIGN_CENTER);	// 居中显示
        table.setAutoFillEmptyCells(true);			// 自动填满 
        
        
        // 附件标题
        Cell[] leftTitleCell = new Cell[head.size()];	
		leftTitleCell[0] = new Cell(new Phrase(fujianStr, contextFont));
		leftTitleCell[0].setColspan(6);
		leftTitleCell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
		leftTitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
		leftTitleCell[0].disableBorderSide(2);
        table.addCell(leftTitleCell[0]);
        
        // 标题
        Cell[] TitleCell = new Cell[head.size()];	
        TitleCell[0] = new Cell(new Phrase(title, titleFont));
        TitleCell[0].setColspan(6);
        TitleCell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        TitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        TitleCell[0].disableBorderSide(2);
        table.addCell(TitleCell[0]);
        
        // 附标题
        Cell[] fuTitleCell = new Cell[head.size()];	
        fuTitleCell[0] = new Cell(new Phrase(contextString, contextFont));
        fuTitleCell[0].setColspan(6);
        fuTitleCell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
        fuTitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        fuTitleCell[0].disableBorderSide(2);
        table.addCell(fuTitleCell[0]);
        
        // 表格标题
		Cell[] cellHeaders = new Cell[head.size()];	
		for (int i = 0; i < head.size(); i++) {
			cellHeaders[i] = new Cell(new Phrase(head.get(i), contextFont));
		}
		for (int i = 0; i < cellHeaders.length; i++) {	// 表格标题样式
            cellHeaders[i].setHorizontalAlignment(Element.ALIGN_CENTER);
            cellHeaders[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellHeaders[i]);
        }
		
		
		// 表格填充数据
		for (int i = 0; i < dataList.size(); i++) {	
			String[] strArr = dataList.get(i);
			for (int j = 0; j < strArr.length; j++) {
				String data = strArr[j];
				Paragraph contextCell = new Paragraph(data);
				if(data.trim().equals("") || data == null){
					contextCell = new Paragraph("\n");
					contextCell.setSpacingBefore(0);
				} else {
					contextCell.setSpacingBefore(18);
				}
				Cell cell = new Cell( contextCell );
	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            table.addCell(cell);
			}
		}
		
		// 将表格添加进文档
		this.document.add(table);
		
		// left title
		String footer = "  注：危险性较大工程范围及分类以《公路工程施工安全技术规范》（JTG F90）附录A中的规定为准。 ";
		Paragraph footerPara = new Paragraph(footer);
		footerPara.setAlignment(Element.ALIGN_LEFT);	// 正文格式对齐方式
		footerPara.setFont(contextFont);
		footerPara.setSpacingBefore(2);				// 与上一段落（标题）的行距
        this.document.add(footerPara);
		        
		        
		this.response = response;
		logger.debug("export file is success ------------------");
		return this;
	}
	
	/**
	 * safe
	 * 附件7：应急管理导出
	 * @param title
	 * @param fuTitle
	 * @param widths
	 * @param head
	 * @param dataList
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public WordUtils exportSafeEmgcResPlanManager(String title, String[] fuTitle, 
			int[] widths, List<String> head, List<String[]> dataList, 
			HttpServletResponse response) 
			throws IOException, DocumentException{
		RtfWriter2.getInstance(this.document, response.getOutputStream());
		this.document.open();
		
		// 
		String fujianStr = "   附件  7";
        
        // fuTitle
        String contextString = " 项目名称：" + fuTitle[0] + "          合同段编号：" + fuTitle[1] + "       施工单位：" + fuTitle[2] + " ";

		
        // Table
		L = head.size();
		this.table = new Table(head.size(), 4 + dataList.size());
        table.setWidth(100);						// 表格所占页面宽度 
        table.setWidths(widths);
        table.setAlignment(Element.ALIGN_CENTER);	// 居中显示
        table.setAutoFillEmptyCells(true);			// 自动填满 
        
        
        // 附件标题
        Cell[] leftTitleCell = new Cell[head.size()];	
		leftTitleCell[0] = new Cell(new Phrase(fujianStr, contextFont));
		leftTitleCell[0].setColspan(8);
		leftTitleCell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
		leftTitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
		leftTitleCell[0].disableBorderSide(2);
        table.addCell(leftTitleCell[0]);
        
        // 标题
        Cell[] TitleCell = new Cell[head.size()];	
        TitleCell[0] = new Cell(new Phrase(title, titleFont));
        TitleCell[0].setColspan(8);
        TitleCell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        TitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        TitleCell[0].disableBorderSide(2);
        table.addCell(TitleCell[0]);
        
        // 附标题
        Cell[] fuTitleCell = new Cell[head.size()];	
        fuTitleCell[0] = new Cell(new Phrase(contextString, contextFont));
        fuTitleCell[0].setColspan(8);
        fuTitleCell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
        fuTitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        fuTitleCell[0].disableBorderSide(2);
        table.addCell(fuTitleCell[0]);
        
        // 表格标题
		Cell[] cellHeaders = new Cell[head.size()];	
		for (int i = 0; i < head.size(); i++) {
			cellHeaders[i] = new Cell(new Phrase(head.get(i), contextFont));
		}
		for (int i = 0; i < cellHeaders.length; i++) {	// 表格标题样式
            cellHeaders[i].setHorizontalAlignment(Element.ALIGN_CENTER);
            cellHeaders[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellHeaders[i]);
        }
		
		
		// 表格填充数据
		for (int i = 0; i < dataList.size(); i++) {	
			String[] strArr = dataList.get(i);
			for (int j = 0; j < strArr.length; j++) {
				String data = strArr[j];
				Paragraph contextCell = new Paragraph(data);
				if(data.trim().equals("") || data == null){
					contextCell = new Paragraph("\n");
					contextCell.setSpacingBefore(0);
				} else {
					contextCell.setSpacingBefore(12);
				}
				Cell cell = new Cell( contextCell );
	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            table.addCell(cell);
			}
		}
		
		// 将表格添加进文档
		this.document.add(table);
		
		// left title
		String footer = "  注：预案类型填写：综合预案、专项预案和现场处置方案，如未组织过应急演练则在“应急演练时间”一栏填“无”。 ";
		Paragraph footerPara = new Paragraph(footer);
		footerPara.setAlignment(Element.ALIGN_LEFT);	// 正文格式对齐方式
		footerPara.setFont(contextFont);
		footerPara.setSpacingBefore(2);				// 与上一段落（标题）的行距
        this.document.add(footerPara);
		        
		        
		this.response = response;
		logger.debug("export file is success ------------------");
		return this;
	}
	
	
	/**
	 * safe
	 * 附件8：施工人员驻地管理台账
	 * @param title
	 * @param fuTitle
	 * @param widths
	 * @param head
	 * @param dataList
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public WordUtils exportSafeConsPersonStation (String title, String[] fuTitle, int[] widths, List<String> head,
			List<String[]> dataList, HttpServletResponse response) throws IOException, DocumentException {
		RtfWriter2.getInstance(this.document, response.getOutputStream());
		this.document.open();
		
		String fujianStr = "   附件  8";
        // fuTitle
        String contextString = " 项目名称：" + fuTitle[0] + "          合同段编号：" + fuTitle[1] + "       施工单位：" + fuTitle[2] + " ";
		
        // Table
		L = head.size();
		this.table = new Table(head.size(), 4 + dataList.size());
        table.setWidth(100);						// 表格所占页面宽度 
        table.setWidths(widths);
        table.setAlignment(Element.ALIGN_CENTER);	// 居中显示
        table.setAutoFillEmptyCells(true);			// 自动填满 
        
        
        // 附件标题
        Cell[] leftTitleCell = new Cell[head.size()];	
		leftTitleCell[0] = new Cell(new Phrase(fujianStr, contextFont));
		leftTitleCell[0].setColspan(7);
		leftTitleCell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
		leftTitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
		leftTitleCell[0].disableBorderSide(2);
        table.addCell(leftTitleCell[0]);
        
        // 标题
        Cell[] TitleCell = new Cell[head.size()];	
        TitleCell[0] = new Cell(new Phrase(title, titleFont));
        TitleCell[0].setColspan(7);
        TitleCell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        TitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        TitleCell[0].disableBorderSide(2);
        table.addCell(TitleCell[0]);
        
        // 附标题
        Cell[] fuTitleCell = new Cell[head.size()];	
        fuTitleCell[0] = new Cell(new Phrase(contextString, contextFont));
        fuTitleCell[0].setColspan(7);
        fuTitleCell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
        fuTitleCell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
        fuTitleCell[0].disableBorderSide(2);
        table.addCell(fuTitleCell[0]);
        
        // 表格标题
		Cell[] cellHeaders = new Cell[head.size()];	
		for (int i = 0; i < head.size(); i++) {
			cellHeaders[i] = new Cell(new Phrase(head.get(i), contextFont));
		}
		for (int i = 0; i < cellHeaders.length; i++) {	// 表格标题样式
            cellHeaders[i].setHorizontalAlignment(Element.ALIGN_CENTER);
            cellHeaders[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cellHeaders[i]);
        }
		
		
		// 表格填充数据
		for (int i = 0; i < dataList.size(); i++) {	
			String[] strArr = dataList.get(i);
			for (int j = 0; j < strArr.length; j++) {
				String data = strArr[j];
				Paragraph contextCell = new Paragraph(data);
				if(data.trim().equals("") || data == null){
					contextCell = new Paragraph("\n");
					contextCell.setSpacingBefore(0);
				} else {
					contextCell.setSpacingBefore(18);
				}
				Cell cell = new Cell( contextCell );
	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            table.addCell(cell);
			}
		}
		
		// 将表格添加进文档
		this.document.add(table);
		
		// left title
		String footer = "  注：驻地来源填写“自建”或“租赁”，驻地类型填写：活动板房、商品房或居民自建房，个别人员零星租用商品房或居民自建房可不列入台账 ";
		Paragraph footerPara = new Paragraph(footer);
		footerPara.setAlignment(Element.ALIGN_LEFT);	// 正文格式对齐方式
		footerPara.setFont(contextFont);
		footerPara.setSpacingBefore(2);				// 与上一段落（标题）的行距
        this.document.add(footerPara);
		        
		        
		this.response = response;
		logger.debug("export file is success ------------------");
		return this;
	}
	
	/**
	 * 浏览器下载
	 * @param response
	 * @param filename
	 * @param doc
	 * @throws IOException
	 */
	public WordUtils write(String filename) throws IOException{
		this.response.reset();
		this.response.setContentType("application/octet-stream; charset=utf-8");
		this.response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(filename));
        this.document.close();
        logger.debug("Download success ------------------");
        return this;
	}
	
	
	
	public static void Demo(HttpServletResponse response, String filename) throws IOException {
        // 创建word 文档,并旋转，使其横向
        /*Document document = getRecTanglePage();
        RtfWriter2.getInstance(document, response.getOutputStream());
        document.open();
        
        
		中间部分为word正文
		document.close();
         */
    }

}
