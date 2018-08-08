package cn.linkmore.enterprise.controller.ent.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("修改企业长租用户")
public class ReqUpdateEntRentUser {
	
	@ApiModelProperty(value = "vip用户ID", required = true)
	private Long id ;

	@ApiModelProperty(value = "手机号", required = true)
    private String mobile;
	@ApiModelProperty(value = "真是姓名", required = true)
    private String realname;
	@ApiModelProperty(value = "车牌号", required = true)
    private String plate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}
    
    
}
