package cn.linkmore.monitor.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.monitor.entity.ExceptionLog;
import cn.linkmore.monitor.service.ExceptionLogService;

@RestController
@RequestMapping(value = "/exception-log")
public class ExceptionLogController {

	@Resource
	private ExceptionLogService exceptionLogService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public List<ExceptionLog> list() {
		List<ExceptionLog> logs = this.exceptionLogService.list();
		return logs;
	}
}
