package cn.linkmore.prefecture.controller.app.request;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("第三方请求车区列表")
public class ReqOpenPres {

	@ApiModelProperty(value = "请求appid", required = true) 
	private String appId;

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	} 
	
	
}
