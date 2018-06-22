package cn.linkmore.report.response;
  
/**
 * 请求响应 - 城市下拉信息
 * @author liwenlong
 * @version 2.0
 */
public class ResCity {
	/**
	 * id 主键
	 */
	private Long id;
	
	/**
	 * 名称
	 */
	private String cityName;
	
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
		this.cityName = cityName;
	}
	
}
