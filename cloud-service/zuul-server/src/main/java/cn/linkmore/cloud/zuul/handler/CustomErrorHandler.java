package cn.linkmore.cloud.zuul.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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