package cn.linkmore.user.security;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.StatusEnum;

@ControllerAdvice 
public class AppExceptionHandle {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ResponseEntity<?> handleValidationException(ConstraintViolationException ex, HttpServletRequest request,
			HttpServletResponse response) {
		Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
		StringBuilder sb = new StringBuilder();
		for (ConstraintViolation<?> violation : errors) {
			sb.append(violation.getMessage() + "\n");
		}
		log.info("user app Api service throw valid exception:{}",sb.toString());
		response.setStatus(400);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request); 
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