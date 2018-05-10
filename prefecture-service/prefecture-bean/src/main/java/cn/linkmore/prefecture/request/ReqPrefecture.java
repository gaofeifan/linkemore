package cn.linkmore.prefecture.request;

public class ReqPrefecture {

	/**
	 * 经度
	 */
    private Double latitude;

    /**
     * 纬度
     */
    private Double longitude;
    /**
     * 比例尺
     */
    private Double scale;

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

	public Double getScale() {
		return scale;
	}

	public void setScale(Double scale) {
		this.scale = scale;
	}
}
