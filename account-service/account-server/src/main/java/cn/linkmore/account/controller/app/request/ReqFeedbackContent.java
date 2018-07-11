package cn.linkmore.account.controller.app.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
@ApiModel("添加投诉建议")
public class ReqFeedbackContent {

	@NotBlank(message = "内容不能为空")
	@Length(min=3,max=255,message="内容长度需要在3-255之间")
	@ApiParam(value = "内容", required = true)
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
