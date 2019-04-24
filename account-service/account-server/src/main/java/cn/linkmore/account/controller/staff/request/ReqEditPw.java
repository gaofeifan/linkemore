package cn.linkmore.account.controller.staff.request;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("修改密码")
	public class ReqEditPw {
		@ApiModelProperty(value = "token发送短信成功后生成的", required = true)
		@NotBlank(message="token不能为空") 
		@Length(min=32,max=32,message="token长度应为32位")
		private String token;
		@ApiModelProperty(value = "账号", required = true)
		@NotBlank(message="账号不能为空") 
		private String account;
		@ApiModelProperty(value = "密码，必填", required = true)
		@Pattern(regexp="^[0-9a-zA-Z`~!@#$^&*()=|{}':;',\\\\[\\\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]{6,20}$|^(?=.*\\d+)(?!.*?([\\d])\\1{5})[\\d]{6}$", message="密码不能为连续数字/字母") 
		@NotBlank(message="密码不能为空") 
		private String password;
		@Pattern(regexp="^[0-9a-zA-Z`~!@#$^&*()=|{}':;',\\\\[\\\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]{6,20}$|^(?=.*\\d+)(?!.*?([\\d])\\1{5})[\\d]{6}$", message="密码不能为连续数字/字母") 
		@ApiModelProperty(value = "确认密码，必填", required = true)
		@NotBlank(message="确认密码不能为空") 
		private String repassword;
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getRepassword() {
			return repassword;
		}
		public void setRepassword(String repassword) {
			this.repassword = repassword;
		}

}
