package cn.linkmore.ops.response;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("响应账户")
public class ResPerson {
	@ApiModelProperty(value = "账户id", required = true)
    private Long id;
	@ApiModelProperty(value = "用户名", required = true)
    private String username;
	@ApiModelProperty(value = "密码", required = true)
    private String password;
	@ApiModelProperty(value = "锁定状态", required = true)
    private Integer lockStatus;
	@ApiModelProperty(value = "锁定时间", required = true)
    private Date lockTime;
	@ApiModelProperty(value = "最后登录ip", required = true)
    private String loginIp;
	@ApiModelProperty(value = "已锁定次数", required = true)
    private Integer lockCount;
	@ApiModelProperty(value = "最后登录时间", required = true)
    private Date loginTime;
	@ApiModelProperty(value = "用户状态", required = true)
    private Integer status;
	@ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
	@ApiModelProperty(value = "真实名称", required = true)
    private String realname;
	@ApiModelProperty(value = "企业id", required = true)
    private Long entId;
	@ApiModelProperty(value = "分类0:系统级别,1自定义，2企业用户", required = true)
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public Integer getLockCount() {
        return lockCount;
    }

    public void setLockCount(Integer lockCount) {
        this.lockCount = lockCount;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Long getEntId() {
        return entId;
    }

    public void setEntId(Long entId) {
        this.entId = entId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}