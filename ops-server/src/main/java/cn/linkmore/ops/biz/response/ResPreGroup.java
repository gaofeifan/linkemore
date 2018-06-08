package cn.linkmore.ops.biz.response;

import java.util.Date;

public class ResPreGroup {
	
    private Long id;

    private String name;

    private String preIds;
    
    private int preCount;

    private String content;

    private String operator;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getPreCount() {
		return preCount;
	}

	public void setPreCount(int preCount) {
		this.preCount = preCount;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPreIds() {
		return preIds;
	}

	public void setPreIds(String preIds) {
		this.preIds = preIds;
	}
    
}