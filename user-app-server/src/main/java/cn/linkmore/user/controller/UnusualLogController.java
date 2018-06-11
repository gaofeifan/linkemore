package cn.linkmore.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
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
@RequestMapping("/exception-logs")
@Api(tags="Exception logs",description="APP异常日志上报")
public class UnusualLogController {
	
	@Resource
	private UnusualLogService unusualLogService;
	
	/**
	 * 新增异常日志上报
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/upload", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="新增异常日志上报",notes="新增异常日志上报", consumes = "application/json")
	public ResponseEntity<?> upload(@RequestBody @Validated ReqUnusualLog unusualLog,HttpServletRequest request) {
		this.unusualLogService.insert(unusualLog);
		return ResponseEntity.success("上报成功",request );
	}

}
