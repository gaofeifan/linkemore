package cn.linkmore.third.request;

/**
 * 请求 - 定位请求
 * @author liwenlong
 * @version 2.0
 *
 */
public class ReqLocate {
 
	/**
	 * 经度
	 */
	private String longitude;
	 
	/**
	 * 纬度
	 */
	private String latitude;

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
	
	
}
