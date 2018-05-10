package cn.linkmore.common.entity;
import java.io.Serializable;
import java.util.Date;
/**
 * @Version 2.0
 * @author  GFF
 * @Date     2018年5月10日
 */
public class AdminUser implements Serializable{
	public static final int STATUS_FALSE = 0;
	public static final int STATUS_TRUE = 1;
    /**
	 * 
	 */
	private static final long serialVersionUID = -6631425800654808128L;

	private Long id;

    private String cellphone;

    private String realname;

    private Date createTime;

    private Date updateTime;

    private Date loginTime;

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