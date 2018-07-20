package cn.linkmore.enterprise.controller.app.request;

import org.hibernate.validator.constraints.NotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("品牌用户发送优惠券")
public class ReqBrandUserSend {
	
	@ApiModelProperty(value = "企业ID", required = true)
	@NotBlank(message="企业ID不能为空") 
	private Long entId;
	
	@ApiModelProperty(value = "用户ID", required = true)
	@NotBlank(message="用户ID不能为空") 
	private String userId;

	public Long getEntId() {
		return entId;
	}

	public void setEntId(Long entId) {
		this.entId = entId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
