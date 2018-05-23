package cn.linkmore.prefecture.response;
/**
 * 响应-车区
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResPrefecture {
	/**
	 * 专区id
	 */
	private Long id;
	/**
	 * 专区名称
	 */
	private String name;
	/**
	 * 车区地址
	 */
	private String address;
	/**
	 * 城市id
	 */
	private Long cityId;
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
	 * 专区类型(0普通，1奥迪内部定制专区
	 */
	private Integer type;
	/**
	 * 是否受限
	 */
	private short limitStatus;
	
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
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
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
