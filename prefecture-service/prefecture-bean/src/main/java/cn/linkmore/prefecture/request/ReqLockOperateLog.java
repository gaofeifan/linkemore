package cn.linkmore.prefecture.request;

/**
 * 请求锁操作信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ReqLockOperateLog {
	/**
	 * 操作人id
	 */
    private Long operatorId;
    /**
     * 来源（1后台，2app电池更换，3app车区管理,4异常订单）
     */
    private Short source;
    /**
     * 车位id
     */
    private Long stallId;
    /**
     * 操作(1上线、2下线、3释放)
     */
    private Short operation;
    /**
     * 锁编号
     */
    private String lockSn;
    /**
     * 状态1 操作成功 0 操作失败
     */
    private Integer status;

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

	public String getLockSn() {
		return lockSn;
	}

	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
