package cn.linkmore.prefecture.controller.staff.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import cn.linkmore.annotation.VehicleMarkCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("指定车位锁编号")
public class ReqAssignStall {

	@ApiModelProperty(value = "车位锁Sn码", required = false)
	@NotBlank(message="车位锁sn码不能为空")
	private String lockSn;

	@ApiModelProperty(value = "车牌号", required = false)
	@VehicleMarkCheck(message="请输入正确车牌号",values= {"^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF]$)|([DF][A-HJ-NP-Z0-9][0-9]{4}$))"
            ,"^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1}$"}
			,lengths= {8,7})
	@NotBlank(message="车牌号不能为空")
	private String plate;
	
	@ApiModelProperty(value = "专区ID", required = false)
	@NotNull(message="车区id不能为空")
	private Long preId;

	public String getLockSn() {
		return lockSn;
	}

	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	
}
