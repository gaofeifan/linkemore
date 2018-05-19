package cn.linkmore.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 请求城市id
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("根据城市id请求车区")
public class ReqPreCity {

	@ApiModelProperty(value = "城市id", required = true)
    private Long cityId;

	@ApiModelProperty(value = "用户id", required = false)
    private Long userId;

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
}
