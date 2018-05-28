package cn.linkmore.ops.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 区域请求bean
 * @author   GFF
 * @Date     2018年5月28日
 * @Version  v2.0
 */
@ApiModel("区域请求bean")
public class ReqDistrict {
	
	@ApiModelProperty(value="主键",required = false)
	private Long id;

	@ApiModelProperty(value="区域名称",required = true)
	private String districtName;

	@ApiModelProperty(value="区域代码",required = true)
	private String code;

	@ApiModelProperty(value="城市id",required = true)
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
