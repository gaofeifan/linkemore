package cn.linkmore.account.controller.app.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
@ApiModel("修改昵称")
public class ReqUpdateNickname {
	@ApiParam(value="昵称",required=true) 
	@Length(min=1,max=10,message="昵称长度为1-10个字符")  
	@NotBlank(message="昵称不能为空") 
	private String nickname;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
