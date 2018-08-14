package cn.linkmore.ops.excel;

import io.swagger.annotations.ApiModelProperty;

public class ReqRentUserImport {

	@ApiModelProperty(required=false,value="企业名称")
	private String entName;
	
	@ApiModelProperty(required=false,value="企业车区名称")
	private String entPreName;
	
	@ApiModelProperty(required=false,value="车位名称")
	private String stallName;
	
	@ApiModelProperty(required=false,value="手机号")
	private String mobile;
	
	@ApiModelProperty(required=false,value="用户名称")
	private String username;
	
	@ApiModelProperty(required=false,value="车牌号")
	private String plateNo;

	private Long entId;
	
	private Long entPreId;
	
	private Long stallId;
	
	private Long preId;
	
	private Long userId;
	
	private String realname;
	
	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getEntPreName() {
		return entPreName;
	}

	public void setEntPreName(String entPreName) {
		this.entPreName = entPreName;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

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

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}
}
