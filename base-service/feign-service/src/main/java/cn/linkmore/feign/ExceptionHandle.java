package cn.linkmore.feign;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.ExceptionInfo;
import cn.linkmore.bean.exception.InternalException;
import cn.linkmore.bean.exception.StatusEnum;

@ControllerAdvice
public class ExceptionHandle {
	private  final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	private static final String API_APP_PATH="/app/";
	private static final String API_OPS_PATH="/ops/";
	private static final String API_FEIGN_PATH="/feign/"; 
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<?> handleBindException(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) {
		List<FieldError> fieldErrors=e.getBindingResult().getFieldErrors();
		String message = null;
		if(CollectionUtils.isNotEmpty(fieldErrors)) {
			FieldError error = fieldErrors.get(0);
			message = error.getDefaultMessage();
		}   
		response.setStatus(200);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		StatusEnum se = StatusEnum.VALID_EXCEPTION; 
		se.label = message;
		return ResponseEntity.fail(se, request); 
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ResponseEntity<?> handleValidationException(ConstraintViolationException ex, HttpServletRequest request,
			HttpServletResponse response) { 
		response.setStatus(200);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8"); 
		return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request); 
	}
	
	private ExceptionInfo feignHandle(Exception e,HttpServletRequest request, HttpServletResponse response) { 
		String uri = request.getRequestURI();  
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stackArray = e.getStackTrace();  
        for (int i = 0; i < stackArray.length; i++) {  
            StackTraceElement element = stackArray[i];  
            sb.append(element.toString() + "\n");   
        }   
		log.info("------------------------");
		log.info("micro service throw biz exception {}", sb.toString());
		log.info("url:{}",uri);
		log.info("");
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
	
	private ResponseEntity<?> appHandle(Exception e,HttpServletRequest request, HttpServletResponse response) {
		response.setStatus(200);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		StatusEnum se = StatusEnum.SERVER_EXCEPTION;

		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stackArray = e.getStackTrace();  
        for (int i = 0; i < stackArray.length; i++) {  
            StackTraceElement element = stackArray[i];  
            sb.append(element.toString() + "\n");  
        }   
        log.info("------------------------");
		log.info("App Api service throw  exception:{}",sb.toString());
		log.info("");
		if (e instanceof BusinessException) {
			BusinessException b = (BusinessException) e; 
			se = b.getStatusEnum();
		}
		return ResponseEntity.fail(se, request); 
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Object handle(Exception e,HttpServletRequest request, HttpServletResponse response) {   
		String uri = request.getRequestURI();  
		if(uri.indexOf(API_FEIGN_PATH)==0) {
			return this.feignHandle(e, request, response);
		}else if(uri.indexOf(API_APP_PATH)==0) {
			return this.appHandle(e, request, response);
		}else if(uri.indexOf(API_OPS_PATH)==0) {
			
		}
		return this.feignHandle(e, request, response);
	}
}