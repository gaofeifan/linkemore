package cn.linkmore.common.response;

import java.util.Date;
  
/**
 * 请求响应 - 城市信息
 * @author liwenlong
 * @version 2.0
 */
public class ResCity {
	/**
	 * id 主键
	 */
	private Long id;
	/**
	 * 行政编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String cityName;
	/**
	 * 行政编码
	 */
	private String adcode;
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	 
	/**
	 * 经度
	 */
	private String longitude;
	 
	/**
	 * 纬度
	 */
	private String latitude;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAdcode() {
		return adcode;
	}
	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}
	
	
	
}
