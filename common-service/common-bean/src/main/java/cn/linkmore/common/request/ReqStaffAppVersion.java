package cn.linkmore.common.request;

import java.util.Date;

public class ReqStaffAppVersion {

	  private Long id;

	    private String version;

	    private Long code;

	    private String name;
	  
	    private Integer status = 0;
	  
	    private String url;

	    private Integer type;
	  
	    private Integer updateStatus = 0;

	    private Date createTime;

	    private String description;

	    private Date updateTime;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getVersion() {
	        return version;
	    }

	    public void setVersion(String version) {
	        this.version = version == null ? null : version.trim();
	    }

	    public Long getCode() {
	        return code;
	    }

	    public void setCode(Long code) {
	        this.code = code;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name == null ? null : name.trim();
	    }

	    public Integer getStatus() {
	        return status;
	    }

	    public void setStatus(Integer status) {
	        this.status = status;
	    }

	    public String getUrl() {
	        return url;
	    }

	    public void setUrl(String url) {
	        this.url = url == null ? null : url.trim();
	    }

	    public Integer getType() {
	        return type;
	    }

	    public void setType(Integer type) {
	        this.type = type;
	    }

	    public Integer getUpdateStatus() {
	        return updateStatus;
	    }

	    public void setUpdateStatus(Integer updateStatus) {
	        this.updateStatus = updateStatus;
	    }

	    public Date getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Date createTime) {
	        this.createTime = createTime;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description == null ? null : description.trim();
	    }

	    public Date getUpdateTime() {
	        return updateTime;
	    }

	    public void setUpdateTime(Date updateTime) {
	        this.updateTime = updateTime;
	    }
}
