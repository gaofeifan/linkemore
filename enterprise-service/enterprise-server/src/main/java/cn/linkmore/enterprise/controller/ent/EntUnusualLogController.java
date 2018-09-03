package cn.linkmore.enterprise.controller.ent;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.annotation.AopIgnore;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.common.request.ReqUnusualLog;
import cn.linkmore.enterprise.service.UnusualLogService;
import cn.linkmore.util.ObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 物业版异常日志上报
 * @Version 2.0
 * @author  GFF
 * @Date     2018年5月11日
 */
@RestController
@RequestMapping("/ent/exception-logs")
@Api(tags="Exception logs",description="物业版异常日志上报")
public class EntUnusualLogController {
	
	@Resource
	private UnusualLogService unusualLogService;
	
	/**
	 * 新增异常日志上报
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="新增异常日志上报",notes="新增异常日志上报", consumes = "application/json")
	@AopIgnore
	public ResponseEntity<?> upload(@RequestBody @Validated cn.linkmore.enterprise.controller.ent.request.ReqUnusualLog unusualLog,HttpServletRequest request) {
		this.unusualLogService.insert(ObjectUtils.copyObject(unusualLog, new ReqUnusualLog()));
		return ResponseEntity.success("上报成功",request );
	}

}
