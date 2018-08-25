package cn.linkmore.enterprise.controller.app.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("操作车位车锁")
public class ReqConStall {

	@ApiModelProperty("车位id")
	@NotNull(message="车位不能为空")
	private Long stallId;
	
	@ApiModelProperty("操作动作( 1:下降 2 升起 )")
	@NotNull(message="状态不能为空")
	@Range(max=2,min=1,message="操作动作为1或2")
	private Integer state;
	
	
	@ApiModelProperty("到达状态 (0未知1到达2未到达) ")
	@NotNull(message="不能为空")
	private Long parkingStatus;


	public Long getStallId() {
		return stallId;
	}


	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	public Long getParkingStatus() {
		return parkingStatus;
	}


	public void setParkingStatus(Long parkingStatus) {
		this.parkingStatus = parkingStatus;
	}
	
	
	
}
