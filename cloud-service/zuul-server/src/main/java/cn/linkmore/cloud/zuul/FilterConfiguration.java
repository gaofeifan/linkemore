package cn.linkmore.cloud.zuul;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
 

@Configuration
public class FilterConfiguration { 
	
	@Bean
	public TokenFilter tokenFilter() {
		return new TokenFilter(); 
	}

	@Bean
	public ErrorFilter errorFilter(CustomErrorHandler errorHandler) {
		ErrorFilter filter = new ErrorFilter();
		filter.setErrorHandler(errorHandler);
		return filter;
	}  
	
	@Component
	public class CustomTokenHandler implements ResponseHandler { 

		@Override
		public int getResponseCode() {
			return HttpServletResponse.SC_OK;
		}
		 
		@Override
		public String getResponseBody(String originMessage, Throwable e) { 
			Map<String,Object> message = new HashMap<String,Object>();
			message.put("code", 403);
			message.put("content", "未授权访问");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", false);
			result.put("message", message);
			result.put("data", null);
			String json = null;
			try {
				json = new ObjectMapper().writeValueAsString(result);
			} catch (JsonProcessingException e1) { 
				e1.printStackTrace();
			}
			return json;
		}
	}
 
	@Component
	public class CustomErrorHandler implements ResponseHandler { 

		@Override
		public int getResponseCode() {
			return HttpServletResponse.SC_OK;
		}
		 
		@Override
		public String getResponseBody(String originMessage, Throwable e) { 
			Map<String,Object> message = new HashMap<String,Object>();
			message.put("code", 404);
			message.put("content", "访问服务未在线");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", false);
			result.put("message", message);
			result.put("data", null);
			String json = null;
			try {
				json = new ObjectMapper().writeValueAsString(result);
			} catch (JsonProcessingException e1) { 
				e1.printStackTrace();
			}
			return json;
		}
	}
}
