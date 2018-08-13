package cn.linkmore.ops.excel.controller;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.linkmore.bean.common.Constants;
import cn.linkmore.ops.excel.response.ResponseBean;
import cn.linkmore.ops.excel.service.FileImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "文件导入管理", produces = "application/json")
@RestController
@RequestMapping("/fileImport")
public class FileImportController extends FileBaseController {

	@Autowired
	FileImportService fileImportService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	// 计算导入文件的大小
	protected String getFileSize(long l) {
		int size = (int) l;
		double fileSize = 0;
		String empty = "";
		if (size > 1024) {
			fileSize = size / 1024.0;
			empty = "KB";
		} else if (size > 1048576) {
			fileSize = size / 1048576.0;
			empty = "MB";
		} else {
			empty = "B";
		}
		String format = String.format("%.2f", fileSize) + empty;
		return format;
	}

	/**
	 * 导入用户录入的信息
	 */
	@ApiOperation(value = "导入录入的用户信息", notes = "导入录入的用户信息", consumes = "application/json")
	@RequestMapping(value = "/customerInfoImport", method = RequestMethod.POST)
	public Map<String, Object> customerInfoImport(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			String fileName = "customerInfoImport";
			ResponseBean result = importExcel4(fileName, file);
			String fileSize = getFileSize(file.getSize());
//			request.setAttribute(Log.LOG_CONTENT_ATTRIBUTE_NAME,
//					"操作成功[文件名：" + file.getOriginalFilename() + ",文件大小：" + fileSize);
			msg.put("message", true);

			if (result.getMsg().indexOf("全部失败") != -1 ? true
					: false || result.getMsg().indexOf("存在") != -1 ? true : false) {
				msg.put("message", result.getMsg());
			} else {
				msg.put("result", result.getMsg());
			}
		} catch (Exception e) {
//			request.setAttribute(Log.LOG_CONTENT_ATTRIBUTE_NAME, "操作失败：系统异常");
//			msg.put("message", "系统异常");
		}
		return msg;
	}


	// 用户信息录入导入
	public ResponseBean importExcel4(String fileName, MultipartFile file) {
		ResponseBean result = new ResponseBean();

		if (file != null && !file.isEmpty()) {
			if (fileImportService.ready(fileName)) {
				try {
					String msg = fileImportService.invoke4(fileName, file);// file.getOriginalFilename()
					result.setMsg(msg);
				} catch (Exception e) {
//					throwException(Errors.IMPORT_ERROR.code, e.getMessage());
				}
			} else {
//				throwException(Constants.ExpiredTime.imp.IMPORT_ERROR_IMPORTING);
			}
		} else {
//			throwException(Errors.MPORT_ERROR_FILE);
		}
		return result;
	}

	
	/**
	 * 导入用户录入的信息
	 */
	@ApiOperation(value = "导入长租用户", notes = "导入长租用户", consumes = "application/json")
	@RequestMapping(value = "/rent-user-import", method = RequestMethod.POST)
	public Map<String, Object> RentUserImport(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		try {
			String fileName = "rentUserImport";
			ResponseBean result = importExcel5(fileName, file);
			String fileSize = getFileSize(file.getSize());
//			request.setAttribute(Log.LOG_CONTENT_ATTRIBUTE_NAME,
//					"操作成功[文件名：" + file.getOriginalFilename() + ",文件大小：" + fileSize);
			msg.put("message", true);

			if (result.getMsg().indexOf("全部失败") != -1 ? true
					: false || result.getMsg().indexOf("存在") != -1 ? true : false) {
				msg.put("message", result.getMsg());
			} else {
				msg.put("result", result.getMsg());
			}
		} catch (Exception e) {
//			request.setAttribute(Log.LOG_CONTENT_ATTRIBUTE_NAME, "操作失败：系统异常");
//			msg.put("message", "系统异常");
		}
		return msg;
	}


	// 长租用户导入
	public ResponseBean importExcel5(String fileName, MultipartFile file) {
		ResponseBean result = new ResponseBean();

		if (file != null && !file.isEmpty()) {
			if (fileImportService.ready(fileName)) {
				try {
					String msg = fileImportService.invoke5(fileName, file);// file.getOriginalFilename()
					result.setMsg(msg);
				} catch (Exception e) {
//					throwException(Errors.IMPORT_ERROR.code, e.getMessage());
				}
			} else {
//				throwException(Constants.ExpiredTime.imp.IMPORT_ERROR_IMPORTING);
			}
		} else {
//			throwException(Errors.MPORT_ERROR_FILE);
		}
		return result;
	}
}
