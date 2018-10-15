package cn.linkmore.feign;

import java.io.BufferedReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.ExceptionInfo;
import cn.linkmore.bean.exception.InternalException;
import cn.linkmore.util.JsonUtil;
import feign.codec.ErrorDecoder;

@Component
public class ExceptionErrorDecoder implements ErrorDecoder { 
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public Exception decode(String s, feign.Response response) { 
		Exception exception = null;
		try {
			if (response.body() != null) { 
				BufferedReader reader = new BufferedReader(response.body().asReader()); 
				String body = reader.readLine();
				log.info("hello body:{}",body);
				ExceptionInfo be = JsonUtil.toObject(body, ExceptionInfo.class); 
				if(be.getException().contains("BusinessException")) {
					exception =  new BusinessException(be.getStatus(), be.getMessage(), be.getMessage());
				}
				exception =  new InternalException(be.getMessage());
			}
		} catch (Exception e) {  
			StringBuffer sb = new StringBuffer();
			StackTraceElement[] stackArray = e.getStackTrace();  
	        for (int i = 0; i < stackArray.length; i++) {
	            StackTraceElement element = stackArray[i];  
	            sb.append(element.toString() + "\n");  
	        }  
	        log.info(sb.toString());
		}
//		return feign.FeignException.errorStatus(s, response);
		return exception;
	} 
}