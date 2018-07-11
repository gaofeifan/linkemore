package cn.linkmore.account.controller.app.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
@ApiModel("修改账户名")
public class ReqUpdateRealname {
	@ApiParam(value="账号名称",required=true) 
	@Length(min=1,max=4,message="长度为1-4个字符")  
	@NotBlank(message="账号名称不能为空") 
	private String realname;

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	
}
