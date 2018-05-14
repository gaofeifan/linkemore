package cn.linkmore.prefecture.response;

public class ResPrefecture {
	/**
	 * 专区id
	 */
	private Long id;
	/**
	 * 专区名称
	 */
	private String preName;
	/**
	 * 空闲车位数量
	 */
	private Integer leisureStall;
	/**
	 * 专区经度
	 */
	private Double latitude;
	/**
	 * 专区纬度
	 */
	private Double longitude;
	/**
	 * 专区类型(0普通，1奥迪内部定制专区)
	 */
	private Integer type;
	/**
	 * 是否受限
	 */
	private short limitStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPreName() {
		return preName;
	}
	public void setPreName(String preName) {
		this.preName = preName;
	}
	public Integer getLeisureStall() {
		return leisureStall;
	}
	public void setLeisureStall(Integer leisureStall) {
		this.leisureStall = leisureStall;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public short getLimitStatus() {
		return limitStatus;
	}
	public void setLimitStatus(short limitStatus) {
		this.limitStatus = limitStatus;
	}
}
