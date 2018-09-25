package cn.linkmore.enterprise.controller.staff.request;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车位上线")
public class StallOnLineRequest  implements Serializable{
	
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1319498234254480661L;

	@ApiModelProperty(value = "车位ID", required = true)
	private Long stallId;
	
	public Long getStallId() {
		return stallId;
	}
	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	
}
