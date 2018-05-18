package cn.linkmore.third.response;

/**
 * 响应-定位信息
 * 
 * @author liwenlong
 * @version 2.0
 *
 */
public class ResLocate {
	/**
	 * 国家
	 */
	private String nation;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区，可能为空字串
	 */
	private String district;
	/**
	 * 街道
	 */
	private String street;
	/**
	 * 行政区划代码
	 */
	private String adcode;
	/**
	 * 行政区划名称
	 */
	private String name;
	/**
	 *  纬度
	 */
	private Double locationLat;
	
	/**
	 *  纬度
	 */
	private Double locationLng;

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAdcode() {
		return adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLocationLat() {
		return locationLat;
	}

	public void setLocationLat(Double locationLat) {
		this.locationLat = locationLat;
	}

	public Double getLocationLng() {
		return locationLng;
	}

	public void setLocationLng(Double locationLng) {
		this.locationLng = locationLng;
	}
}
