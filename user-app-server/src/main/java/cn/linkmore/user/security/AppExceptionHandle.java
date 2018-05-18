package cn.linkmore.user.security;

import java.io.IOException;
import java.io.PrintWriter;
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
import cn.linkmore.util.JsonUtil;

@ControllerAdvice(basePackages = { "cn.linkmore.user.controller" })
public class AppExceptionHandle {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public void handleValidationException(ConstraintViolationException ex, HttpServletRequest request,
			HttpServletResponse response) {
		Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
		StringBuilder sb = new StringBuilder();
		for (ConstraintViolation<?> violation : errors) {
			sb.append(violation.getMessage() + "\n");
		}
		log.warn("user app Api service throw valid exception:{}",sb.toString());
		response.setStatus(400);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			ResponseEntity<?> re = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request); 
			pw.write(JsonUtil.toJson(re));
			pw.flush();
		} catch (IOException ie) {

		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public void handle(Exception e,HttpServletRequest request, HttpServletResponse response) {  
		response.setStatus(200);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] stackArray = e.getStackTrace();  
        for (int i = 0; i < stackArray.length; i++) {  
            StackTraceElement element = stackArray[i];  
            sb.append(element.toString() + "\n");  
        }   
		log.warn("user app Api service throw valid exception:{}",sb.toString());
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			ResponseEntity<?> re = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request); 
			pw.write(JsonUtil.toJson(re));
			pw.flush();
		} catch (IOException ie) {

		} finally {
			log.warn("user app Api service throw biz exception");
			if (pw != null) {
				pw.close();
			}
		}
	}
}