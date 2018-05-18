package cn.linkmore.feign;

import java.io.BufferedReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.ExceptionInfo;
import cn.linkmore.bean.exception.InternalException;
import cn.linkmore.util.JsonUtil;
import feign.codec.ErrorDecoder;

@Configuration
public class ExceptionErrorDecoder implements ErrorDecoder { 
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public Exception decode(String s, feign.Response response) { 
		try {
			if (response.body() != null) { 
				BufferedReader reader = new BufferedReader(response.body().asReader()); 
				String body = reader.readLine();
				log.info("body:{}",body);
				ExceptionInfo be = JsonUtil.toObject(body, ExceptionInfo.class); 
				if(be.getException().contains("BusinessException")) {
					return new BusinessException(be.getStatus(), be.getMessage(), be.getMessage());
				} 
			}
		} catch (Exception var4) { 
			return new InternalException(var4.getMessage());
		}
		return new InternalException("系统异常,请联系管理员");
	} 
}