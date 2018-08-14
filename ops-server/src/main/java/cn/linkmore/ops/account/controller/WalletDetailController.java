package cn.linkmore.ops.account.controller;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.WalletDetailService;
import cn.linkmore.ops.excel.controller.FileBaseController;
import cn.linkmore.ops.service.DataExportService;
import cn.linkmore.order.request.ReqWalletDetailExport;
import cn.linkmore.order.response.ResWalletDetailExport;
import io.swagger.annotations.Api;

@Api(value = "充值明细", produces = "application/json")
@RestController
@RequestMapping("/admin/account/wallet_detail")
public class WalletDetailController extends FileBaseController{

	@Resource
	private WalletDetailService walletDetailService;
	@Resource
    private DataExportService dataExportService;
	

	/**
	 * 钱包--充值明细
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ViewPage walletDetailList(HttpServletRequest request,ViewPageable pageable){
		ViewPage page = this.walletDetailService.getDetailList(pageable);
		return page;
	}
	/**
	 * 导出
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void exportExcel( ReqWalletDetailExport bean,HttpServletResponse response){
		List<ResWalletDetailExport> result = walletDetailService.getListByTime(bean);
		HSSFWorkbook workBook = dataExportService.exportWalletDetail(result);
    	String fileName = "充值明细信息" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls";
    	writeFile(fileName, workBook, "充值明细导出失败", response);
    	
	}
}
