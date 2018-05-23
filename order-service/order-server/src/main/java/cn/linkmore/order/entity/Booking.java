package cn.linkmore.order.entity;

import java.util.Date;
/**
 * 预约记录
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class Booking {
	public static final Short REASON_NONE = 0;
	public static final short REASON_EXCEPTION = 1;
	public static final short REASON_STALL_NONE = 2;
	public static final short REASON_STALL_EXCEPTION = 3;
	public static final short REASON_STALL_ORDERED = 4;
	public static final short REASON_CARNO_BUSY = 5;
	public static final short REASON_USER_LIMIT = 6;
	
    private Long id;

    private Long preId;

    private Long userId;

    private String day;

    private Short status;

    private Short first;

    private Short source;

    private Short reason;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getFirst() {
        return first;
    }

    public void setFirst(Short first) {
        this.first = first;
    }

    public Short getSource() {
        return source;
    }

    public void setSource(Short source) {
        this.source = source;
    }

    public Short getReason() {
        return reason;
    }

    public void setReason(Short reason) {
        this.reason = reason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}