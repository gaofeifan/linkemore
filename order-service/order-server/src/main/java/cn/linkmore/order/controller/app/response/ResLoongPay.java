package cn.linkmore.order.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("龙支付")
public class ResLoongPay {
	@ApiModelProperty(value="调用龙支付的参数")
	private String param;

	@ApiModelProperty(value="自定义app字段值")
	private String thirdAppInfo;

	public ResLoongPay() {}

	public ResLoongPay(String param, String thirdAppInfo) {
		this.param = param;
		this.thirdAppInfo = thirdAppInfo;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getThirdAppInfo() {
		return thirdAppInfo;
	}

	public void setThirdAppInfo(String thirdAppInfo) {
		this.thirdAppInfo = thirdAppInfo;
	}
}
