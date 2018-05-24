package cn.linkmore.user.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.user.request.ReqUnusualLog;
import cn.linkmore.user.service.UnusualLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * app异常日志上报
 * @Version 2.0
 * @author  GFF
 * @Date     2018年5月11日
 */
@RestController
@RequestMapping("/app_logs")
@Api(tags="app_logs",description="APP异常日志上报")
public class UnusualLogController {
	
	@Resource
	private UnusualLogService unusualLogService;
	
	/**
	 * 新增异常日志上报
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="新增异常日志上报",notes="新增异常日志上报", consumes = "application/json")
	public void insert(@RequestBody ReqUnusualLog unusualLog) {
		this.unusualLogService.insert(unusualLog);
	}

}