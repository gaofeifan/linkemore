package cn.linkmore.feign;

import org.springframework.context.annotation.Configuration;

import cn.linkmore.bean.exception.BaseException;
import cn.linkmore.bean.exception.InternalException;
import cn.linkmore.util.JsonUtil;
import feign.codec.ErrorDecoder;

@Configuration
public class ExceptionErrorDecoder implements ErrorDecoder { 
	@Override
	public Exception decode(String s, feign.Response response) { 
		try {
			if (response.body() != null) { 
				String body = response.body().asReader().toString(); 
				BaseException be = JsonUtil.toObject(body, BaseException.class); 
				return be.getException(); 
			}
		} catch (Exception var4) { 
			return new InternalException(var4.getMessage());
		}
		return new InternalException("系统异常,请联系管理员");
	} 
}