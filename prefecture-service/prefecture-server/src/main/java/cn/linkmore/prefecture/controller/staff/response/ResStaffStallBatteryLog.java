package cn.linkmore.prefecture.controller.staff.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询管理版使用记录")
public class ResStaffStallBatteryLog {

	@ApiModelProperty(value="id")
	private Long id;

	@ApiModelProperty(value="车位id")
	private Long stallId;

	@ApiModelProperty(value="剩余电压")
	private Double voltage;

	@ApiModelProperty(value="操作次数（升降)")
	private Integer totalNum;

	@ApiModelProperty(value="管理员ID")
	private Long adminId;

	@ApiModelProperty(value="管理员名称")
	private String adminName;

	@ApiModelProperty(value="创建时间")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public Double getVoltage() {
		return voltage;
	}

	public void setVoltage(Double voltage) {
		this.voltage = voltage;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
