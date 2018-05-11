package cn.linkmore.third.request;

import java.util.Map;

import cn.linkmore.bean.constant.SmsTemplate;

public class ReqSms {
	private String mobile;
	private SmsTemplate st;
	private Map<String,String> param;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public SmsTemplate getSt() {
		return st;
	}
	public void setSt(SmsTemplate st) {
		this.st = st;
	}
	public Map<String, String> getParam() {
		return param;
	}
	public void setParam(Map<String, String> param) {
		this.param = param;
	} 
}
