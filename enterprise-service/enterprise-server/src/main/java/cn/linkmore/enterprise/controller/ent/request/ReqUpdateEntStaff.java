package cn.linkmore.enterprise.controller.ent.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("修改企业员工信息")
public class ReqUpdateEntStaff {
	
	@ApiModelProperty(value="员工id" ,required = true)
	private Long id ;
	
	@ApiModelProperty(value="企业id" ,required = true)
	private Long entId;
	
	@ApiModelProperty(value="手机号" ,required = true)
    private String mobile;
	
	@ApiModelProperty(value="类型" ,required = true)
    private Short type;
	
	@ApiModelProperty(value="真是姓名" ,required = true)
    private String realname;
	
	@ApiModelProperty(value="真是姓名" ,required = true)
    private Short status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
    
}
