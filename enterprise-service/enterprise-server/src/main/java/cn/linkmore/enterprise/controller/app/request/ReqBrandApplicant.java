package cn.linkmore.enterprise.controller.app.request;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("品牌申请人")
public class ReqBrandApplicant {
	
	@ApiModelProperty(value = "企业ID", required = true)
	@NotBlank(message="企业ID不能为空") 
	private Long entId;
	
	@ApiModelProperty(value = "手机号，必填", required = true)
	@NotBlank(message="手机号不能为空") 
	@Pattern(regexp="^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\\d{8})$", message="无效手机号") 
	private String mobile;

	public Long getEntId() {
		return entId;
	}

	public void setEntId(Long entId) {
		this.entId = entId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
