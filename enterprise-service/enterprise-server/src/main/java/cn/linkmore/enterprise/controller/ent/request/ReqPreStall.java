/**
 * 
 */
package cn.linkmore.enterprise.controller.ent.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author luzhishen
 * @Date 2018年7月21日
 * @Version v1.0
 */
@ApiModel("车场车位")
public class ReqPreStall {
	
	@ApiModelProperty("车区ID")
	private Long preId;
	@ApiModelProperty("车位类型")
	private Short type;

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
}
