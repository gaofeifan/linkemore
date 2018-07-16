package cn.linkmore.account.controller.app.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel("解密手机号")
public class ReqMiniMobile { 
	@ApiParam(value = "密文", required = true)
	private String data;
	@ApiParam(value = "向量", required = true)
	private String iv;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getIv() {
		return iv;
	}
	public void setIv(String iv) {
		this.iv = iv;
	}
	
}
