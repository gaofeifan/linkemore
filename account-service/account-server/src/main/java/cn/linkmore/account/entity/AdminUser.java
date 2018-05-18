package cn.linkmore.account.entity;

import java.util.Date;

/**
 * 管理员用户
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
public class AdminUser {
    private Long id;

    /**
     *  手机号(全局唯一)
     */ 
    private String cellphone;

    /**
     *  姓名
     */ 
    private String realname;

    private Date createTime;

    private Date updateTime;

    /**
     *  最近一次登录时间
     */ 
    private Date loginTime;

    /**
     *  状态(0禁用,1启用)
     */ 
    private Short status;

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
}