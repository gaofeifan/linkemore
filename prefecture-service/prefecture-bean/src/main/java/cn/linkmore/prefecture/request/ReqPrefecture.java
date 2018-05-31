package cn.linkmore.prefecture.request;
/**
 * APP请求车区参数
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ReqPrefecture {

	/**
	 * 经度
	 */
    private String latitude;

    /**
     * 纬度
     */
    private String longitude;
    /**
     * 城市id
     */
    private Long cityId;
    /**
     * 用户id
     */
    private Long userId;

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
}
