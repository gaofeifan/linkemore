package cn.linkmore.order.controller.app.request;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("选择车位预约请求")
public class ReqStallBooking {
	@ApiModelProperty(value = "车区ID", required = true)
	@Min(value=0,message="车区ID为大于0的长整数")
	@NotBlank(message="专区ID不能为空") 
	private Long prefectureId;
	
	@ApiModelProperty(value = "车牌ID", required = true)
	@Min(value=0,message="车牌ID为大于0的长整数")
	@NotBlank(message="车牌不能为空") 
	private Long plateId;
	
	@ApiModelProperty(value = "车位ID", required = true)
	@Min(value=0,message="车位为大于0的长整数")
	@NotBlank(message="车位ID不能为空") 
	private Long stallId;
	
	@ApiModelProperty(value = "车位锁编号", required = true)
	@NotBlank(message="车位锁编号不能为空") 
	private String lockSn;
	
	public String getLockSn() {
		return lockSn;
	}
	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}
	public Long getPrefectureId() {
		return prefectureId;
	}
	public void setPrefectureId(Long prefectureId) {
		this.prefectureId = prefectureId;
	}
	public Long getPlateId() {
		return plateId;
	}
	public void setPlateId(Long plateId) {
		this.plateId = plateId;
	}
	public Long getStallId() {
		return stallId;
	}
	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}
}
