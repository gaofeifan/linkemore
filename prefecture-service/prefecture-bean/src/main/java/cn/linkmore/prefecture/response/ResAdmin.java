package cn.linkmore.prefecture.response;

import java.util.Date;

/**
 * 管理版响应
 * @author   GFF
 * @Date     2018年9月12日
 * @Version  v2.0
 */
public class ResAdmin {
	public static final String ADMIN_ALL_CODE = "admin-all";
	public static final String ADMIN_ALL = "超级管理员";
	
	private Long id;

	private String cellphone;

	private String realname;

	private Date createTime;

	private Date updateTime;

	private Date loginTime;

	private Short status;

	private String code;

	private Boolean isOperate;

	private String type;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone == null ? null : cellphone.trim();
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname == null ? null : realname.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsOperate() {
		return isOperate;
	}

	public void setIsOperate(Boolean isOperate) {
		this.isOperate = isOperate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
