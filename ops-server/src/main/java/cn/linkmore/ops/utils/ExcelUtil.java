package cn.linkmore.ops.utils;


import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Excel工具类
 * @author   GFF
 * @Date     2018年5月26日
 * @Version  v2.0
 */
public class ExcelUtil {
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	public static final String EMPTY = "";
	public static final String POINT = ".";
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

	public static String NO_DEFINE = "no_define";// 未定义的字段
	public static String DEFAULT_DATE_PATTERN = "yyyy年MM月dd日";// 默认日期格式
	public static int DEFAULT_COLOUMN_WIDTH = 17;
	private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * 获得path的后缀名
	 * 
	 * @param path
	 * @return
	 */
	public static String getPostfix(String path) {
		if (path == null || EMPTY.equals(path.trim())) {
			return EMPTY;
		}
		if (path.contains(POINT)) {
			return path.substring(path.lastIndexOf(POINT) + 1, path.length());
		}
		return EMPTY;
	}

	/**
	 * 单元格格式
	 * 
	 * @param hssfCell
	 * @return
	 */
	@SuppressWarnings({ "static-access", "deprecation" })
	public static String getHValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			String cellValue = "";
			if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
				Date date = HSSFDateUtil.getJavaDate(hssfCell
						.getNumericCellValue());
				cellValue = sdf.format(date);
			} else {
				DecimalFormat df = new DecimalFormat("#.##");
				cellValue = df.format(hssfCell.getNumericCellValue());
				String strArr = cellValue.substring(
						cellValue.lastIndexOf(POINT) + 1, cellValue.length());
				if (strArr.equals("00")) {
					cellValue = cellValue.substring(0,
							cellValue.lastIndexOf(POINT));
				}
			}
			return cellValue;
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	/**
	 * 单元格格式
	 * 
	 * @param xssfCell
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getXValue(XSSFCell xssfCell) {
		if (xssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfCell.getBooleanCellValue());
		} else if (xssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			String cellValue = "";
			if (XSSFDateUtil.isCellDateFormatted(xssfCell)) {
				Date date = XSSFDateUtil.getJavaDate(xssfCell
						.getNumericCellValue());
				cellValue = sdf.format(date);
			} else {
				DecimalFormat df = new DecimalFormat("#.##");
				cellValue = df.format(xssfCell.getNumericCellValue());
				String strArr = cellValue.substring(
						cellValue.lastIndexOf(POINT) + 1, cellValue.length());
				if (strArr.equals("00")) {
					cellValue = cellValue.substring(0,
							cellValue.lastIndexOf(POINT));
				}
			}
			return cellValue;
		} else {
			return String.valueOf(xssfCell.getStringCellValue());
		}
	}

	/**
	 * 导出2007版本Excel
	 * 
	 * @param title
	 * @param headMap
	 * @param jsonArray
	 * @param datePattern
	 * @param colWidth
	 * @param out
	 */
	@SuppressWarnings("deprecation")
	public static void exportExcel(String title, Map<String, String> headMap,
			JSONArray jsonArray, String datePattern, int colWidth,
			OutputStream out) {
		if (datePattern == null)
			datePattern = DEFAULT_DATE_PATTERN;
		SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
		workbook.setCompressTempFiles(true);
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Font titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints((short) 20);
		titleFont.setBoldweight((short) 700);
		titleStyle.setFont(titleFont);
		CellStyle headerStyle = workbook.createCellStyle();
		//headerStyle.setFillPattern(HSSFCellSty);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setFont(headerFont);
		CellStyle cellStyle = workbook.createCellStyle();
		//cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		Font cellFont = workbook.createFont();
		cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		cellStyle.setFont(cellFont);
		SXSSFSheet sheet = workbook.createSheet();
		int minBytes = colWidth < DEFAULT_COLOUMN_WIDTH ? DEFAULT_COLOUMN_WIDTH
				: colWidth;
		int[] arrColWidth = new int[headMap.size()];
		String[] properties = new String[headMap.size()];
		String[] headers = new String[headMap.size()];
		int ii = 0;
		for (Iterator<String> iter = headMap.keySet().iterator(); iter
				.hasNext();) {
			String fieldName = iter.next();
			properties[ii] = fieldName;
			headers[ii] = headMap.get(fieldName);

			int bytes = fieldName.getBytes().length;
			arrColWidth[ii] = bytes < minBytes ? minBytes : bytes;
			sheet.setColumnWidth(ii, arrColWidth[ii] * 256);
			ii++;
		}
		int rowIndex = 0;
		for (Object obj : jsonArray) {
			if (rowIndex == 65535 || rowIndex == 0) {
				if (rowIndex != 0)
					sheet = workbook.createSheet();

				SXSSFRow titleRow = sheet.createRow(0);
				titleRow.createCell(0).setCellValue(title);
				titleRow.getCell(0).setCellStyle(titleStyle);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap
						.size() - 1));

				SXSSFRow headerRow = sheet.createRow(1);
				for (int i = 0; i < headers.length; i++) {
					headerRow.createCell(i).setCellValue(headers[i]);
					headerRow.getCell(i).setCellStyle(headerStyle);

				}
				rowIndex = 2;
			}
			JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
			SXSSFRow dataRow = sheet.createRow(rowIndex);
			for (int i = 0; i < properties.length; i++) {
				SXSSFCell newCell = dataRow.createCell(i);

				Object o = jo.get(properties[i]);
				String cellValue = "";
				if (o == null)
					cellValue = "";
				else if (o instanceof Date)
					cellValue = new SimpleDateFormat(datePattern).format(o);
				else if (o instanceof Float || o instanceof Double)
					cellValue = new BigDecimal(o.toString()).setScale(2,
							BigDecimal.ROUND_HALF_UP).toString();
				else
					cellValue = o.toString();

				newCell.setCellValue(cellValue);
				newCell.setCellStyle(cellStyle);
			}
			rowIndex++;
		}
		/*
		 * for (int i = 0; i < headers.length; i++) { sheet.autoSizeColumn(i); }
		 */
		try {
			workbook.write(out);
			workbook.close();
			workbook.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public static void exportReportExcel(Map<Integer,Object> exportMap, String reportTitle, OutputStream out) {
		int colWidth = 0;
		String datePattern = DEFAULT_DATE_PATTERN;
		SXSSFWorkbook workbook = new SXSSFWorkbook(1000);
		workbook.setCompressTempFiles(true);
		
		CellStyle firstTitleStyle = workbook.createCellStyle();
		firstTitleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		firstTitleStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		firstTitleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		Font firstTitleFont = workbook.createFont();
		/*titleFont.setFontHeightInPoints((short) 20);
		titleFont.setBoldweight((short) 700);*/
		firstTitleFont.setFontHeightInPoints((short) 10);
		firstTitleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		firstTitleStyle.setFont(firstTitleFont);
		
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		Font titleFont = workbook.createFont();
		/*titleFont.setFontHeightInPoints((short) 20);
		titleFont.setBoldweight((short) 700);*/
		titleFont.setColor(HSSFFont.COLOR_RED);
		titleStyle.setFont(titleFont);
		CellStyle headerStyle = workbook.createCellStyle();
		//headerStyle.setFillPattern(HSSFCellSty);
		/*headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);*/
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		Font headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setFont(headerFont);
		CellStyle cellStyle = workbook.createCellStyle();
		//cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		/*cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);*/
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		Font cellFont = workbook.createFont();
		cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		cellStyle.setFont(cellFont);
		SXSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0 , reportTitle);
		int minBytes = colWidth < DEFAULT_COLOUMN_WIDTH ? DEFAULT_COLOUMN_WIDTH
				: colWidth;
		int rowIndex = 0;
		int rowLength = 0;
		for (Entry<Integer, Object> entry : exportMap.entrySet()) {
			log.info("key= " + entry.getKey() + " and value= " + entry.getValue());
			Map<String,Object> map = (Map<String, Object>) entry.getValue();
			Map<String, String> headMap = (Map<String, String>) map.get("head");
			JSONArray jsonArray = (JSONArray) map.get("value");
			String title = (String) map.get("title");
			rowLength = rowIndex;
			log.info("every table start size "+ rowLength);
			int[] arrColWidth = new int[headMap.size()];
			String[] properties = new String[headMap.size()];
			String[] headers = new String[headMap.size()];
			int ii = 0;
			for (Iterator<String> iter = headMap.keySet().iterator(); iter
					.hasNext();) {
				String fieldName = iter.next();
				properties[ii] = fieldName;
				headers[ii] = headMap.get(fieldName);

				int bytes = fieldName.getBytes().length;
				arrColWidth[ii] = bytes < minBytes ? minBytes : bytes;
				sheet.setColumnWidth(ii, arrColWidth[ii] * 256);
				ii++;
			}
			
			if (rowIndex == 65535 || rowIndex == 0) {
				if (rowIndex != 0)
					sheet = workbook.createSheet();
				SXSSFRow firstTitleRow = sheet.createRow(0);
				firstTitleRow.createCell(0).setCellValue("1、用户");
				firstTitleRow.getCell(0).setCellStyle(firstTitleStyle);
			}
			if(entry.getKey().equals(1)) {
				SXSSFRow titleRow = sheet.createRow(2);
				titleRow.createCell(0).setCellValue(title);
				titleRow.getCell(0).setCellStyle(titleStyle);
				
				SXSSFRow headerRow = sheet.createRow(3);
				for (int i = 0; i < headers.length; i++) {
					headerRow.createCell(i).setCellValue(headers[i]);
					headerRow.getCell(i).setCellStyle(headerStyle);
				}
				rowIndex = 4;
			}else if(entry.getKey().equals(6)){
				SXSSFRow firstTitleRow = sheet.createRow(rowLength+1);
				firstTitleRow.createCell(0).setCellValue("2、订单");
				firstTitleRow.getCell(0).setCellStyle(firstTitleStyle);
				
				SXSSFRow titleRow = sheet.createRow(rowLength+3);
				titleRow.createCell(0).setCellValue(title);
				titleRow.getCell(0).setCellStyle(titleStyle);
				
				SXSSFRow headerRow = sheet.createRow(rowLength+4);
				for (int i = 0; i < headers.length; i++) {
					headerRow.createCell(i).setCellValue(headers[i]);
					headerRow.getCell(i).setCellStyle(headerStyle);
				}
				rowIndex = rowLength + 5;
			}else if(entry.getKey().equals(15)){
				SXSSFRow firstTitleRow = sheet.createRow(rowLength+1);
				firstTitleRow.createCell(0).setCellValue("3、收入");
				firstTitleRow.getCell(0).setCellStyle(firstTitleStyle);
				
				SXSSFRow titleRow = sheet.createRow(rowLength+3);
				titleRow.createCell(0).setCellValue(title);
				titleRow.getCell(0).setCellStyle(titleStyle);
				
				SXSSFRow headerRow = sheet.createRow(rowLength+4);
				for (int i = 0; i < headers.length; i++) {
					headerRow.createCell(i).setCellValue(headers[i]);
					headerRow.getCell(i).setCellStyle(headerStyle);
				}
				rowIndex = rowLength + 5;
			}else{
				SXSSFRow titleRow = sheet.createRow(rowLength+1);
				titleRow.createCell(0).setCellValue(title);
				titleRow.getCell(0).setCellStyle(titleStyle);
				
				SXSSFRow headerRow = sheet.createRow(rowLength+2);
				for (int i = 0; i < headers.length; i++) {
					headerRow.createCell(i).setCellValue(headers[i]);
					headerRow.getCell(i).setCellStyle(headerStyle);
				}
				rowIndex = rowLength + 3;
			}
			
			for (Object obj : jsonArray) {
				JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
				SXSSFRow dataRow = sheet.createRow(rowIndex);
				for (int i = 0; i < properties.length; i++) {
					SXSSFCell newCell = dataRow.createCell(i);

					Object o = jo.get(properties[i]);
					String cellValue = "";
					if (o == null)
						cellValue = "";
					else if (o instanceof Date)
						cellValue = new SimpleDateFormat(datePattern).format(o);
					else if (o instanceof Float || o instanceof Double)
						cellValue = new BigDecimal(o.toString()).setScale(2,
								BigDecimal.ROUND_HALF_UP).toString();
					else
						cellValue = o.toString();

					newCell.setCellValue(cellValue);
					newCell.setCellStyle(cellStyle);
				}
				rowIndex++;
			}
		}
		
		try {
			workbook.write(out);
			workbook.close();
			workbook.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class XSSFDateUtil extends DateUtil {
	protected static int absoluteDay(Calendar cal, boolean use1904windowing) {
		return DateUtil.absoluteDay(cal, use1904windowing);
	}
}