package cn.linkmore.user.security;
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
import cn.linkmore.bean.exception.StatusEnum;

@ControllerAdvice
public class ExceptionHandle {
	private  final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
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

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseEntity<?> handle(Exception e,HttpServletRequest request, HttpServletResponse response) {  
		response.setStatus(200);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stackArray = e.getStackTrace();  
        for (int i = 0; i < stackArray.length; i++) {  
            StackTraceElement element = stackArray[i];  
            sb.append(element.toString() + "\n");  
        }   
		log.info("user app Api service throw valid exception:{}",sb.toString());
		return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request); 
	}
}