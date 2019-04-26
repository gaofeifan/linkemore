package cn.linkmore.feign;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
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
import cn.linkmore.monitor.client.FeignExceptionLogClient;
import cn.linkmore.monitor.request.ReqExceptionLog;
@ControllerAdvice
public class ExceptionHandle {
	private  final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	private static final String API_APP_PATH="/app/";
	private static final String API_OPS_PATH="/ops/";
	private static final String API_FEIGN_PATH="/feign/"; 
	private static final String API_STAFF_PATH="/staff/"; 
	
	private static final String BUSINESS = "business";
	private static final String FEIGN = "feign";
	private static final String VALID = "valid";
	
	
	@Resource
	private FeignExceptionLogClient feignExceptionLogClient;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<?> handleBindException(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) {
		StatusEnum se = StatusEnum.VALID_EXCEPTION; 
		response.setStatus(200);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			List<FieldError> fieldErrors=e.getBindingResult().getFieldErrors();
			String message = null;
			if(CollectionUtils.isNotEmpty(fieldErrors)) {
				FieldError error = fieldErrors.get(0);
				message = error.getDefaultMessage();
			}   
			se.label = message;
			ReqExceptionLog log = new ReqExceptionLog();
			log.setContent(se.label);
			log.setParameter(e.getStackTrace().length > 0 ? e.getStackTrace()[0].getClassName():null);
			log.setUrl(request.getRequestURI());
			addExceptionLog(log, BUSINESS);
			return ResponseEntity.fail(se, request);
		} catch (Exception e1) {
			return ResponseEntity.fail(se, request);
		} 
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ResponseEntity<?> handleValidationException(ConstraintViolationException ex, HttpServletRequest request,
			HttpServletResponse response) { 
		response.setStatus(200);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {
			ReqExceptionLog log = new ReqExceptionLog();
			log.setUrl(request.getRequestURI());
			if(ex.getConstraintViolations().size() != 0) {
				Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
				log.setContent(violations.iterator().next().getMessage());
				log.setParameter(violations.iterator().next().getRootBeanClass().getName());
				addExceptionLog(log, VALID);
				return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION.code,violations.iterator().next().getMessage(),request);
			}
			log.setContent(StatusEnum.VALID_EXCEPTION.label);
			log.setParameter(ex.getStackTrace().length > 0 ? ex.getStackTrace()[0].getClassName():null);
			addExceptionLog(log, VALID);
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		} catch (Exception e) {
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		} 
	}
	
	private ExceptionInfo opsHandle(Exception e,HttpServletRequest request, HttpServletResponse response) { 
		ExceptionInfo ei = new ExceptionInfo();
		response.setStatus(500);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {
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
			if (e instanceof BusinessException) {
				BusinessException b = (BusinessException) e;
				ei.setException(BusinessException.class.getName());
				ei.setStatus(b.getCode());
				ei.setMessage(b.getMessage());
			} else {
				log.info("ops handle exception = {}", e.getMessage());
				ei.setException(InternalException.class.getName());
				ei.setStatus(StatusEnum.SERVER_EXCEPTION.code);
				ei.setMessage(e.getMessage());
			}
			ei.setPath(uri);
			ei.setTimestamp(new Date().getTime());
			return ei;
		} catch (Exception e1) {
			return ei;
		}
	}
	
	private ExceptionInfo feignHandle(Exception e,HttpServletRequest request, HttpServletResponse response) { 
		ExceptionInfo ei = new ExceptionInfo();
		response.setStatus(500);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		try {
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
			if (e instanceof BusinessException) {
				BusinessException b = (BusinessException) e;
				ei.setException(BusinessException.class.getName());
				ei.setStatus(b.getCode());
				ei.setMessage(b.getMessage());
			} else {
				log.info("feign handle exception = {}", e.getMessage());
				ei.setException(InternalException.class.getName());
				ei.setStatus(StatusEnum.BAD_REQUEST.code);
				ei.setMessage(StatusEnum.BAD_REQUEST.label);
			}
			ei.setPath(uri);
			ei.setTimestamp(new Date().getTime());
			return ei;
		} catch (Exception e1) {
			return ei;
		}
	}
	
	private ResponseEntity<?> appHandle(Exception e,HttpServletRequest request, HttpServletResponse response) {
		StatusEnum se = StatusEnum.SERVER_EXCEPTION;
		try {
			response.setStatus(200);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");

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
		} catch (Exception e1) {
			return ResponseEntity.fail(se, request);
		} 
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Object handle(Exception e,HttpServletRequest request, HttpServletResponse response) {   
		try {
			String uri = request.getRequestURI();
			ReqExceptionLog log = new ReqExceptionLog();
			log.setUrl(uri);
			log.setContent(e.getMessage());
			log.setParameter(e.getStackTrace().length > 0 ? e.getStackTrace()[0].getClassName():null);
			this.addExceptionLog(log, BUSINESS);
			if(uri.indexOf(API_FEIGN_PATH)==0) {
				return this.feignHandle(e, request, response);
			}else if(uri.indexOf(API_APP_PATH)==0) {
				return this.appHandle(e, request, response);
			}else if(uri.indexOf(API_STAFF_PATH)==0) {
				return this.appHandle(e, request, response);
			}else if(uri.indexOf(API_OPS_PATH)==0) {
				return this.opsHandle(e, request, response);
			}
		} catch (Exception e1) {
			return this.feignHandle(e, request, response);
		}
		return this.feignHandle(e, request, response);
	}
	
	private void addExceptionLog(ReqExceptionLog log,String falg) {
		new Runnable() {
			public void run() {
				if(falg.equals(VALID)) {
					feignExceptionLogClient.validException(log);
				}else if(falg.equals(FEIGN)) {
					feignExceptionLogClient.feignException(log);
				}else if(falg.equals(BUSINESS)) {
					feignExceptionLogClient.bindException(log);
				}
			}
		}.run();
	}
	
	public void addFeignExceptionLog(String content) {
		ReqExceptionLog log = new ReqExceptionLog();
		log.setContent(content);
		this.addExceptionLog(log, FEIGN);
	}
}