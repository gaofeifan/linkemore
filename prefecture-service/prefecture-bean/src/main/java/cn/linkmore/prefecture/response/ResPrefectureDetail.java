package cn.linkmore.prefecture.response;

import java.util.Map;

public class ResPrefectureDetail {
	/**
	 * 专区id
	 */
	private Long id;

	/**
	 * 专区名称
	 */
	private String name;

	/**
	 * 空闲车位数量
	 */
	private Integer leisureStall;

	/**
	 * 车区地址
	 */
	private String address;

	/**
	 * 首小时价格
	 */
	private String firstHour;

	/**
	 * 时间基数 15分钟等
	 */
	private String timelyLong;
	
	/**
	 * 时间基数单位
	 */
	private String timelyUnit;

	/**
	 * 免费时长
	 */
	private Long freeMins;
	
	/**
	 * 专区信息[key:id,value:count]
	 */
	private Map<String,Object> preMap;

	/**
	 * 专区类型(0普通，1专区内部定制专区)
	 */
	private Integer type;
	
	/**
	 * 车位总数
	 */
	private Integer stallCount;
	
	private short limitStatus;

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
		this.name = name;
	}

	public Integer getLeisureStall() {
		return leisureStall;
	}

	public void setLeisureStall(Integer leisureStall) {
		this.leisureStall = leisureStall;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirstHour() {
		return firstHour;
	}

	public void setFirstHour(String firstHour) {
		this.firstHour = firstHour;
	}

	public String getTimelyLong() {
		return timelyLong;
	}

	public void setTimelyLong(String timelyLong) {
		this.timelyLong = timelyLong;
	}

	public String getTimelyUnit() {
		return timelyUnit;
	}

	public void setTimelyUnit(String timelyUnit) {
		this.timelyUnit = timelyUnit;
	}

	public Long getFreeMins() {
		return freeMins;
	}

	public void setFreeMins(Long freeMins) {
		this.freeMins = freeMins;
	}

	public Map<String, Object> getPreMap() {
		return preMap;
	}

	public void setPreMap(Map<String, Object> preMap) {
		this.preMap = preMap;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStallCount() {
		return stallCount;
	}

	public void setStallCount(Integer stallCount) {
		this.stallCount = stallCount;
	}

	public short getLimitStatus() {
		return limitStatus;
	}

	public void setLimitStatus(short limitStatus) {
		this.limitStatus = limitStatus;
	}
}
