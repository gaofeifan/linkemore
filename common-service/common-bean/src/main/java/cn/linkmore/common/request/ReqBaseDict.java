package cn.linkmore.common.request;

import java.util.Date;

/**
 * 请求 - 城市信息
 * @author liwenlong
 * @version 2。0
 *
 */
public class ReqBaseDict {
	   private Long id;

	    private Long groupId;

	    private String name;

	    private String code;

	    private Integer orderIndex;

	    private Date createTime;

	    private Integer extra;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Long getGroupId() {
	        return groupId;
	    }

	    public void setGroupId(Long groupId) {
	        this.groupId = groupId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name == null ? null : name.trim();
	    }

	    public String getCode() {
	        return code;
	    }

	    public void setCode(String code) {
	        this.code = code == null ? null : code.trim();
	    }

	    public Integer getOrderIndex() {
	        return orderIndex;
	    }

	    public void setOrderIndex(Integer orderIndex) {
	        this.orderIndex = orderIndex;
	    }

	    public Date getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Date createTime) {
	        this.createTime = createTime;
	    }

	    public Integer getExtra() {
	        return extra;
	    }

	    public void setExtra(Integer extra) {
	        this.extra = extra;
	    }
}
