package cn.linkmore.common.entity;
 

/**
 * Entity - 城市信息
 * @author liwenlong
 * @version 2.0
 *
 */
public class City {
	/**
	 * id主键
	 */
    private Long id;

    /**
     * 城市名称
     */
    private String name;

    /**
     * 行政编号
     */
    private String adcode;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode == null ? null : adcode.trim();
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
}