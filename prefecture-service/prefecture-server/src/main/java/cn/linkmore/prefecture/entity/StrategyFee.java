package cn.linkmore.prefecture.entity;

import java.util.Date;

/**
 * 计费策略
 * @author kobe
 *
 */
public class StrategyFee {
	/**
	 * 主键id
	 */
    private Long id;
    /**
     * 策略编号
     */
    private String parkCode;
    /**
     * 策略名称
     */
    private String parkName;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 更新时间
     */
    private Date updateTime;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParkCode() {
        return parkCode;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode == null ? null : parkCode.trim();
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName == null ? null : parkName.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
}