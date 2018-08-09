package cn.linkmore.enterprise.controller.ent.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("新增企业vip用户")
public class ReqAddEntVipUser {
	
	
	@ApiModelProperty(value = "id", required = true)
    private Long id;
	@ApiModelProperty(value = "企业Id", required = true)
	private Long entId;
	@ApiModelProperty(value = "企业车区id", required = true)
    private Long entPreId;
	@ApiModelProperty(value = "车区id", required = true)
    private Long preId;

	@ApiModelProperty(value = "手机号", required = true)
    private String mobile;
	@ApiModelProperty(value = "真是姓名", required = true)
    private String realname;
	@ApiModelProperty(value = "车牌号", required = true)
    private String plate;


	public Long getEntId() {
		return entId;
	}

	public void setEntId(Long entId) {
		this.entId = entId;
	}

	public Long getEntPreId() {
		return entPreId;
	}

	public void setEntPreId(Long entPreId) {
		this.entPreId = entPreId;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
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
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    
    
}
