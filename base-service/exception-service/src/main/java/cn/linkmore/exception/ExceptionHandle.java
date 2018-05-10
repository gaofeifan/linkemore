package cn.linkmore.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.linkmore.bean.exception.BaseException;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.exception.InternalException;
import cn.linkmore.util.JsonUtil;

@ControllerAdvice
public class ExceptionHandle {
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
		response.setStatus(400);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			BaseException be = new BusinessException(StatusEnum.BAD_REQUEST);
			pw.write(JsonUtil.toJson(be));
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
	public void handle(Exception e, HttpServletResponse response) {
		BaseException be = null;
		if (e instanceof BusinessException) {
			be = (BusinessException) e;
		} else {
			be = new InternalException("未知异常");
		}
		response.setStatus(500);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(JsonUtil.toJson(be));
			pw.flush();
		} catch (IOException ie) {

		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}