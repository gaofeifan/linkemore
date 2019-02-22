package cn.linkmore.prefecture.controller.staff.response;

import org.apache.commons.lang.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询锁关联的网关")
public class ResLockGatewayList {

	@ApiModelProperty("网关名称")
	private String gatewayName;
	
	@ApiModelProperty("网关序列号(编号)")
	private String serialNumber;

	@ApiModelProperty("绑定状态0未绑定1已绑定")
	private String bindFlag = "0";
	
	public ResLockGatewayList(String serialNumber) {
		super();
		this.serialNumber = serialNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getBindFlag() {
		return bindFlag;
	}

	public void setBindFlag(String bindFlag) {
		this.bindFlag = bindFlag;
	}

	public String getGatewayName() {
		if(StringUtils.isNotBlank(serialNumber)) {
			gatewayName = serialNumber.substring(serialNumber.length()-4);
		}
		return gatewayName;
	}
	
	
}
