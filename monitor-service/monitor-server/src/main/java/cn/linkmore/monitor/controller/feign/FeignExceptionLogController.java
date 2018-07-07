package cn.linkmore.monitor.controller.feign;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.monitor.entity.ExceptionLog;
import cn.linkmore.monitor.request.ReqExceptionLog;
import cn.linkmore.monitor.service.ExceptionLogService;
import cn.linkmore.util.ObjectUtils;

/**
 * 内部调用--异常日志记录
 * 
 * @author GFF
 * @Date 2018年7月4日
 * @Version v2.0
 */
@RestController
@RequestMapping(value = "/feign/exception-log")
public class FeignExceptionLogController {

	@Resource
	private ExceptionLogService exceptionLogService;

	/**
	 * @Description 添加异常日志
	 * @Author GFF
	 * @Version v2.0
	 */
	@RequestMapping(value="/v2.0/info",method=RequestMethod.POST)
	public void info(@RequestBody ReqExceptionLog log) {
		ExceptionLog el = this.copy(log);
		el.setLevel("INFO");
		this.exceptionLogService.save(el);
	}
	
	@RequestMapping(value="/v2.0/bind",method=RequestMethod.POST)
	public void bindException(@RequestBody ReqExceptionLog log) {
		ExceptionLog el = this.copy(log);
		el.setLevel("BusinessException");
		this.exceptionLogService.save(el);
	}

	@RequestMapping(value="/v2.0/valid",method=RequestMethod.POST)
	public void validException(@RequestBody ReqExceptionLog log) {
		ExceptionLog el = this.copy(log);
		el.setLevel("ValidException");
		this.exceptionLogService.save(el);

	}

	@RequestMapping(value="/v2.0/feign",method=RequestMethod.POST)
	public void feignException(@RequestBody ReqExceptionLog log) {
		ExceptionLog el = this.copy(log);
		el.setLevel("FeignException");
		this.exceptionLogService.save(el);
	}
	
	private ExceptionLog copy(ReqExceptionLog log) {
		ExceptionLog object = ObjectUtils.copyObject(log, new ExceptionLog());
		object.setCreateTime(new Date());
		return object;
		
	}
	
}
