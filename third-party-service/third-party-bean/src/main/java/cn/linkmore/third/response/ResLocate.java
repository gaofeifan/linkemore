package cn.linkmore.third.response;
/**
 * 响应-定位信息
 * @author liwenlong
 * @version 2.0
 *
 */
public class ResLocate { 
	private String nation;///国家
	private String province;//是	省
	private String city;//	是	市
	private String district;//	否	区，可能为空字串
	private String street;
	private String adcode;  //行政区划代码
	private String name; //行政区划名称
	private Double locationLat;//纬度
	private Double locationLng;//纬度
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
