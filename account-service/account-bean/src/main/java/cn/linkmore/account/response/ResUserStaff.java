package cn.linkmore.account.response;

import java.util.Date;

public class ResUserStaff {

	  private Long id;

	    private String username;

	    private String realname;

	    private Short status;

	    private Date createTime;

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

	    public String getRealname() {
	        return realname;
	    }

	    public void setRealname(String realname) {
	        this.realname = realname == null ? null : realname.trim();
	    }

	    public Short getStatus() {
	        return status;
	    }

	    public void setStatus(Short status) {
	        this.status = status;
	    }

	    public Date getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Date createTime) {
	        this.createTime = createTime;
	    }
}
