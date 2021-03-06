package cn.linkmore.prefecture.response;

import java.util.Date;

/**
 * 响应-APP车位信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResStall {
	/**
	 * 车位id
	 */
	private Long id;
	/**
	 * 车位名称
	 */
	private String stallName;
	/**
	 * 车位锁编号
	 */
	private String lockSn;
	/**
	 * 车位状态 状态:1，空闲；2，使用中；3,预下线；4，下线  5 故障
	 */
	private Integer status;
	/**
	 * 分类 0自营，1临停，2长租，3VIP
	 */
	private short type;
	/**
	 * 车牌号
	 */
	private String plateNo;
	/**
	 * 车区id
	 */
	private Long preId;
	
	private Date endTime;
	
	private Date createTime;
	
	private Integer bindOrderStatus;
	/**
	 *  锁状态
	 */ 
	private Integer lockStatus;
	
    /**
     * 停车场内分区名称
     */
    private String areaName;
    
	public Long getPreId() {
		return preId;
	}
	public void setPreId(Long preId) {
		this.preId = preId;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public short getType() {
		return type;
	}
	public void setType(short type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStallName() {
		return stallName;
	}
	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
	public String getLockSn() {
		return lockSn;
	}
	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}
	public Integer getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}
	public Integer getBindOrderStatus() {
		return bindOrderStatus;
	}
	public void setBindOrderStatus(Integer bindOrderStatus) {
		this.bindOrderStatus = bindOrderStatus;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
