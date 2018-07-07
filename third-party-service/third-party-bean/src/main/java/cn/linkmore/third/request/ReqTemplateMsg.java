package cn.linkmore.third.request;

import java.util.ArrayList;
import java.util.List;

public class ReqTemplateMsg {

	private List<String> openId = new ArrayList<>();
	
	private String templateId;
	
	private String url;
	
	private String topcolor = "#FF0000";

	private Object data;
	
	private String token;
	
	public List<String> getOpenId() {
		return openId;
	}

	public void setOpenId(List<String> openId) {
		this.openId = openId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
