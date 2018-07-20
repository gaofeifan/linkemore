package cn.linkmore.enterprise.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 响应-车区列表
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("品牌车区空闲车位")
public class ResEntBrandPreLeisure {
	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "空闲车位数量")
	private Integer leisureStall;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLeisureStall() {
		return leisureStall;
	}

	public void setLeisureStall(Integer leisureStall) {
		this.leisureStall = leisureStall;
	}
	
}
