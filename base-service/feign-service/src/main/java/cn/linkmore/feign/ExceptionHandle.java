package cn.linkmore.feign;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.ExceptionInfo;
import cn.linkmore.bean.exception.InternalException;
import cn.linkmore.bean.exception.StatusEnum;

@ControllerAdvice
public class ExceptionHandle {
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ExceptionInfo handle(Exception e,HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI(); 
		log.info("----******************-------");
		log.info("micro service throw biz exception {}", e.getMessage());
		log.info("url:{}",uri);
		
		ExceptionInfo ei = new ExceptionInfo();
		if (e instanceof BusinessException) {
			BusinessException b = (BusinessException) e;
			ei.setException(BusinessException.class.getName());
			ei.setStatus(b.getCode());
			ei.setMessage(b.getMessage());
		} else {
			ei.setException(InternalException.class.getName());
			ei.setStatus(StatusEnum.BAD_REQUEST.code);
			ei.setMessage(StatusEnum.BAD_REQUEST.label);
		}
		ei.setPath(uri);
		ei.setTimestamp(new Date().getTime());
		response.setStatus(500);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		return ei;
	}
}