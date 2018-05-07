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
    private String cityName;

    /**
     * 行政编号
     */
    private String adcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode == null ? null : adcode.trim();
    }
}