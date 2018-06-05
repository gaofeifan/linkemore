package cn.linkmore.ops.biz.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.account.request.ReqCustomerInfoExport;
import cn.linkmore.account.response.ResCustomerInfoExport;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.CustomerInfoService;
import cn.linkmore.ops.controller.FileBaseController;
import cn.linkmore.ops.service.DataExportService;
import io.swagger.annotations.Api;

/**
 * @author GFF
 * @Date 2018年5月31日
 * @Version v2.0
 */
@Api(value = "用户信息录入", produces = "application/json")
@RestController
@RequestMapping(value = "/admin/biz/customer_info")
public class CustomerInfoController extends FileBaseController {

	@Autowired
	private CustomerInfoService infoService;
	@Resource
	private DataExportService dataExportService;

	/**
	 * 获取列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return infoService.findList(pageable);
	}

	/**
	 * 导出
	 */
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public void exportChargeRecord(ReqCustomerInfoExport pageable, HttpServletResponse response) {
		List<ResCustomerInfoExport> result = infoService.getExportList(pageable);
		HSSFWorkbook workBook = dataExportService.exportCustomerInfo(result);
		String fileName = "用户信息录入" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls";
		writeFile(fileName, workBook, "用户信息录入导出失败", response);
	}

	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
