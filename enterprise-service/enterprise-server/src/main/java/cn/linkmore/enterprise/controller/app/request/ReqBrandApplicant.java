package cn.linkmore.enterprise.controller.app.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import cn.linkmore.annotation.VehicleMarkCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("品牌申请人")
public class ReqBrandApplicant {
	
	@ApiModelProperty(value = "企业ID", required = true)
	@Min(value=0,message="企业ID为大于0的长整数")
	private Long entId;
	
	@ApiModelProperty(value = "手机号，必填", required = true)
	@NotBlank(message="手机号不能为空") 
	@Pattern(regexp="^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\\d{8})$", message="无效手机号") 
	private String mobile;
	
	@ApiModelProperty(value = "车牌号，选填", required = false)
	@VehicleMarkCheck(message="请输入正确车牌号",values= {"^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF]$)|([DF][A-HJ-NP-Z0-9][0-9]{4}$))"
            ,"^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1}$"}
			,lengths= {8,7})
	private String plateNo;
	
	@ApiModelProperty(value = "车区ID", required = false)
	private Long preId;

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public Long getEntId() {
		return entId;
	}

	public void setEntId(Long entId) {
		this.entId = entId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	
}
