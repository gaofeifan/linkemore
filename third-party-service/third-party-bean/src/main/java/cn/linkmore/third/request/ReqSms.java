package cn.linkmore.third.request;

import java.util.Map;

import cn.linkmore.bean.common.Constants;
/**
 * 请求 - 短信
 * @author liwenlong
 * @version 2.0
 */
public class ReqSms {
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 模板
	 */
	private Constants.SmsTemplate st;
	
	/**
	 * 短信体参数
	 */
	private Map<String,String> param;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Constants.SmsTemplate getSt() {
		return st;
	}
	public void setSt(Constants.SmsTemplate st) {
		this.st = st;
	}
	public Map<String, String> getParam() {
		return param;
	}
	public void setParam(Map<String, String> param) {
		this.param = param;
	} 
}
