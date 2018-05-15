package cn.linkmore.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description  日志记录
 * @author  GFF
 * @Date     2018年5月15日
 */
@RestController
@RequestMapping("/common/log")
public class LogController {
	
	
	/**
	 * @Description  上传app异常日志
	 * @Author   GFF 
	 * @Date       2018年5月15日
	 */
	@RequestMapping(value="/upload_exception_log",method=RequestMethod.POST)
	public void uploadExceptionLog() {
		
	}
	
	/**
	 * @Description  小程序接口访问详情日志
	 * @Author   GFF 
	 * @Date     2018年5月15日
	 */
	@RequestMapping(value="/interface_access_details",method=RequestMethod.POST)
	public void interfaceAccessDetails() {
		
	}

}
