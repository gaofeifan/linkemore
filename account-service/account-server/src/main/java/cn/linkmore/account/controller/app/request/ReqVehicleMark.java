package cn.linkmore.account.controller.app.request;

import org.hibernate.validator.constraints.NotBlank;

import cn.linkmore.annotation.VehicleMarkCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("修改车牌号")
public class ReqVehicleMark {
	
	@ApiModelProperty(value="车牌号",required=true)
	@NotBlank(message="车牌号不能为空")
	@VehicleMarkCheck(message="请输入正确车牌号",values= {"^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}[A-HJ-Z]{1}(([0-9]{5}[DF]{1}$)|([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4}$))"
			                                            ,"^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}[A-HJ-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1}$|(使[0-9]{6}$)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领$)"}
														,lengths= {8,7})
	private String vehMark;
	
	@ApiModelProperty(value="车区id",required=false)
	private Long preId = 0L;
	
	public String getVehMark() {
		return vehMark;
	}

	public void setVehMark(String vehMark) {
		this.vehMark = vehMark;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}
}

