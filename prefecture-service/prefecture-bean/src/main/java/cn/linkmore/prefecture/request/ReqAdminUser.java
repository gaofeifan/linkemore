package cn.linkmore.prefecture.request;

import java.util.Date;
/**
 * entity 管理员
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ReqAdminUser {
    private Long id;

    private String cellphone;

    private String realname;

    private Date createTime;

    private Date updateTime;

    private Date loginTime;

    private Short status;
    
    private String accountName;
    
    private String password;
    
    private Short gatewayDelete = 0;

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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Short getGatewayDelete() {
		return gatewayDelete;
	}

	public void setGatewayDelete(Short gatewayDelete) {
		this.gatewayDelete = gatewayDelete;
	}
}