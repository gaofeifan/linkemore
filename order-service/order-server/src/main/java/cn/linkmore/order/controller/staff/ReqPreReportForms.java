package cn.linkmore.order.controller.staff;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import cn.linkmore.bean.common.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("车场报表")
public class ReqPreReportForms {

	@ApiModelProperty("车场id")
	@NotNull(message="车场id不能为空")
	private Long preId;
	
	@ApiModelProperty(value="停车场层数",required=false)
	private String floor;
	
	@ApiModelProperty("当前时间")
	@NotNull(message="当前时间不能为空")
	private Date date;

	@ApiModelProperty("类型 0日 1周 2月")
	@NotNull(message="类型不能为空")
	private short type = 0;

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}
	
	public String getFloor() {
		if(StringUtils.isNotBlank(floor)) {
			if(floor.equals(Constants.FLOOR_ALL)) {
				floor = null;
			}
		}
		return floor;
	}


	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}
	
}
