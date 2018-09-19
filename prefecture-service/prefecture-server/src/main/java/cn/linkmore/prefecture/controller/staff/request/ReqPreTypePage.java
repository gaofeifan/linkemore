package cn.linkmore.prefecture.controller.staff.request;

import java.util.Date;

import javax.validation.constraints.NotNull;


import cn.linkmore.annotation.Digits;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("查询车区运营")
public class ReqPreTypePage {

	@ApiModelProperty(required=true,value = "车区id")
	@NotNull(message="参数不能为空")
	@Digits(message="参数必须为整数")
	private Long preId;
	
	@ApiModelProperty(required=false,value = "2018-09-01 当前页的最后一条数据 默认为当前时间")
	@NotNull(message="参数不能为空")
	private Date now;
	
	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}
}
