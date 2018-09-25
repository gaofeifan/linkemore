package cn.linkmore.enterprise.controller.staff.response;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("手机短信查询结果响应结果")
public class MessageSearchResponseBean {
	@ApiModelProperty(value = "短信内容")
	private String content;
	@ApiModelProperty(value = "发送时间")
	private Date createTime;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
