package cn.linkmore.prefecture.entity;

import java.util.Date;
/**
 * entity 车位操作日志
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class StallOperateLog {
	
	public static final short SOURCE_APP = 2;  
	public static final Integer STATUS_TRUE = 1;
	public static final Integer STATUS_FALSE = 0;
	public static final short OPERATION_ONLINE= 1;
	public static final short OPERATION_OFFLINE = 2;
	public static final short OPERATION_RELEASE= 3;
    private Long id;

    private Long operatorId;

    private Short source;

    private Long stallId;

    private Short operation;

    private String remark;

    private Long remarkId;

    private Date createTime;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Short getSource() {
        return source;
    }

    public void setSource(Short source) {
        this.source = source;
    }

    public Long getStallId() {
        return stallId;
    }

    public void setStallId(Long stallId) {
        this.stallId = stallId;
    }

    public Short getOperation() {
        return operation;
    }

    public void setOperation(Short operation) {
        this.operation = operation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(Long remarkId) {
        this.remarkId = remarkId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}