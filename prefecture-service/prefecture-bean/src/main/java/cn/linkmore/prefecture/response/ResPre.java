package cn.linkmore.prefecture.response;
/**
 * 响应-APP车区名称
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResPre {
	/**
	 * 专区id
	 */
	private Long id;
	/**
	 * 专区名称
	 */
	private String name;
	
	private Long cityId;
	
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
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
}
