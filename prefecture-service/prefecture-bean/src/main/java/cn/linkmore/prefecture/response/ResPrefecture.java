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
	 * 计费价格
	 */
	private String chargePrice;
	/**
	 * 计费时间
	 */
	private String chargeTime;
	
	/**
	 * 专区类型(0普通，1奥迪内部定制专区)
	 */
	private Integer type;
	/**
	 * 是否受限
	 */
	private short limitStatus;
	/**
	 * 网关分组编号
	 */
	private String gateway;
	/**
	 * 车区地图
	 */
	private String imageUrl;
	
	public String getChargePrice() {
		return chargePrice;
	}
	public void setChargePrice(String chargePrice) {
		this.chargePrice = chargePrice;
	}
	public String getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(String chargeTime) {
		this.chargeTime = chargeTime;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
