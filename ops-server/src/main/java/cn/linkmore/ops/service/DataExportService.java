/**
 * 
 */
package cn.linkmore.ops.service;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import cn.linkmore.account.response.ResCustomerInfoExport;
import cn.linkmore.ops.biz.service.UserService;
import cn.linkmore.order.response.ResWalletDetailExport;

/**
 * 数据导出处理
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Service
public class DataExportService {

  @Resource
  private UserService userService;

	/**
	 * 用户信息录入导出
	 */
	private static final List<String> customerInfo = Arrays.asList("地推人员手机号","地推人员名","用户名","车牌号","性别","年龄","孩子数量","孩子年龄","车辆品牌","车辆颜色","车辆排量","停车原因","创建时间");

	public HSSFWorkbook exportCustomerInfo(List<ResCustomerInfoExport> result) {
		// 创建excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		// 创建sheet
		HSSFSheet dataSheet = workBook.createSheet("用户信息导入");
		//为每一列设置宽
		for (int i = 0; i < customerInfo.size(); i++) {
			dataSheet.setColumnWidth(i, 5120);
		}
		
		// 创建excel表头
		HSSFRow firstrow = dataSheet.createRow(0);
		HSSFCell[] firstRowcells = new HSSFCell[customerInfo.size()];
		HSSFFont font = workBook.createFont();
		font.setFontHeightInPoints((short)14); // 设置字体大小 
		font.setFontName("黑体");
		for (int j = 0; j < firstRowcells.length; j++) {
			HSSFRichTextString hts = new HSSFRichTextString(customerInfo.get(j));
			hts.applyFont(font);
			firstRowcells[j] = firstrow.createCell(j);
			firstRowcells[j].setCellValue(hts);
		}
		//格式化日期 excel日期可筛选
		HSSFCellStyle cellStyle = workBook.createCellStyle();
		HSSFDataFormat format = workBook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
		/**
		 * 遍历所有数据，为每一条数据添加一行
		 */
		int row = 1;
		for (ResCustomerInfoExport responseBean : result) {
			buildWalletList(cellStyle,dataSheet, row, responseBean);
			row++;
		}
		return workBook;
	}
	private void buildWalletList(HSSFCellStyle cellStyle,HSSFSheet sheet, int rowNum, ResCustomerInfoExport responseBean) {
		HSSFRow row = sheet.createRow(rowNum);
		//"地推人员手机号","地推人员名","用户名","车牌号","性别","年龄","孩子数量","孩子年龄","车辆品牌","车辆颜色","车辆排量","停车原因","创建时间"
		HSSFCell operator = row.createCell(0);
		HSSFCell realname = row.createCell(1);
		HSSFCell username = row.createCell(2);
		HSSFCell plateNo = row.createCell(3);
		HSSFCell sex = row.createCell(4);
		HSSFCell age = row.createCell(5);
		HSSFCell childNum = row.createCell(6);
		HSSFCell childAge = row.createCell(7);
		HSSFCell modelBrand = row.createCell(8);
		HSSFCell carColor = row.createCell(9);
		HSSFCell carDis = row.createCell(10);
		HSSFCell stopCause = row.createCell(11);
		HSSFCell createTime = row.createCell(12);
		
		createTime.setCellValue(responseBean.getCreateTime());
		createTime.setCellStyle(cellStyle);
		
		operator.setCellValue(responseBean.getOperator());
		realname.setCellValue(responseBean.getRealname());
		username.setCellValue(responseBean.getUsername());
		plateNo.setCellValue(responseBean.getPlateNo());
		sex.setCellValue(responseBean.getSex());
		age.setCellValue(responseBean.getAge());
		childNum.setCellValue(responseBean.getChildNum());
		childAge.setCellValue(responseBean.getChildAge());
		modelBrand.setCellValue(responseBean.getBrandModel());
		carColor.setCellValue(responseBean.getCarColor());
		carDis.setCellValue(responseBean.getCarDis());
		stopCause.setCellValue(responseBean.getStopCause());
		createTime.setCellValue(responseBean.getCreateTime());
		
	}
	/**
	 * 充值明细导出
	 */
	private static final List<String> walletDetail = Arrays.asList("手机号","金额(元)","分类","来源","创建时间","历史账户余额(元)");
	public HSSFWorkbook exportWalletDetail(List<ResWalletDetailExport> responseBeans) {
		// 创建excel
		HSSFWorkbook workBook = new HSSFWorkbook();
		// 创建sheet
		HSSFSheet dataSheet = workBook.createSheet("充值明细信息");
		//为每一列设置宽
		for (int i = 0; i < walletDetail.size(); i++) {
			dataSheet.setColumnWidth(i, 5120);
		}
		
		// 创建excel表头
		HSSFRow firstrow = dataSheet.createRow(0);
		HSSFCell[] firstRowcells = new HSSFCell[walletDetail.size()];
		HSSFFont font = workBook.createFont();
		font.setFontHeightInPoints((short)14); // 设置字体大小 
		font.setFontName("黑体");
		for (int j = 0; j < firstRowcells.length; j++) {
			HSSFRichTextString hts = new HSSFRichTextString(walletDetail.get(j));
			hts.applyFont(font);
			firstRowcells[j] = firstrow.createCell(j);
			firstRowcells[j].setCellValue(hts);
		}
		//格式化日期 excel日期可筛选
		HSSFCellStyle cellStyle = workBook.createCellStyle();
		HSSFDataFormat format = workBook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd HH:mm:ss"));
		int row = 1;
		for (ResWalletDetailExport responseBean : responseBeans) {
			buildWalletList(cellStyle,dataSheet, row, responseBean);
			row++;
		}
		return workBook;
	}
	private void buildWalletList(HSSFCellStyle cellStyle,HSSFSheet sheet, int rowNum, ResWalletDetailExport responseBean) {
		HSSFRow row = sheet.createRow(rowNum);
		//"手机号","金额(元)","分类","来源","创建时间","历史账户余额(元)"
		HSSFCell mobile = row.createCell(0);
		HSSFCell amount = row.createCell(1);
		HSSFCell type = row.createCell(2);
		HSSFCell source = row.createCell(3);
		HSSFCell createTime = row.createCell(4);
		HSSFCell accountAmount = row.createCell(5);
		
		createTime.setCellValue(responseBean.getCreateTime());
		createTime.setCellStyle(cellStyle);
		
		mobile.setCellValue(responseBean.getMobile());
		amount.setCellValue(responseBean.getAmount());
		type.setCellValue(responseBean.getType());
		source.setCellValue(responseBean.getSource());
		if(responseBean.getAccountAmount()!=null){
			accountAmount.setCellValue(responseBean.getAccountAmount());
		}
		
	}
}
