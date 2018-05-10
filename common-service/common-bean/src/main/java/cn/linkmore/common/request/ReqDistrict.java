package cn.linkmore.common.request;

/**
 * 请求 - 区域信息
 * @author jiaohanbin
 * @version 2。0
 *
 */
public class ReqDistrict {
	
	/**
	 * 区域id
	 */
	private Long id;
	/**
	 * 区域名称
	 */
	private String districtName;
	/**
	 * 区域编码
	 */
	private String code;
	/**
	 * 城市id
	 */
	private Long cityId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}  
	
	
}
