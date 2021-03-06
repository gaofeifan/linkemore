package cn.linkmore.prefecture.response;

import java.util.Date;
/**
 * 响应-车区分组
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResPrefectureGroup {
    private Long id;

    private String name;

    private String preIds;

    private String content;

    private Long operatorId;

    private Date createTime;

    private Date updateTime;

    private Short status;
    //车区总数
    private Integer preCount;
    //操作人
    private String operator;

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

    public String getPreIds() {
        return preIds;
    }

    public void setPreIds(String preIds) {
        this.preIds = preIds == null ? null : preIds.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

	public Integer getPreCount() {
		return preCount;
	}

	public void setPreCount(Integer preCount) {
		this.preCount = preCount;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
}