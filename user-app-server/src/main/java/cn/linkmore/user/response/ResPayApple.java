package cn.linkmore.user.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("支付[银联ApplePay]")
public class ResPayApple {
	@ApiModelProperty(value = "交易流水号")
	private String tn;

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}
	
}
