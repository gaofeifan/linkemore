package cn.linkmore.prefecture.controller.staff.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("网关信息")
public class ResGateway {

	@ApiModelProperty("网关名称(网关编号后四位)")
	private String gatewayName;
	
	@ApiModelProperty("网关编号")
	private String serialNumber;
	
	@ApiModelProperty(value="网关在线状态0离线1在线")
	private int gatewayState = 0;
	
//	@ApiModelProperty("分组编号")
//	private String groupCode;

	public String getGatewayName() {
		if(serialNumber != null && serialNumber.length() >= 4) {
			gatewayName = serialNumber.substring(serialNumber.length()-4);
		}
		return gatewayName;
	}


	public ResGateway(String serialNumber, int gatewayState) {
		super();
		this.serialNumber = serialNumber;
		this.gatewayState = gatewayState;
	}




	public void setGatewayName(String gatewayName) {
		this.gatewayName = gatewayName;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}


	public int getGatewayState() {
		return gatewayState;
	}


	public void setGatewayState(int gatewayState) {
		this.gatewayState = gatewayState;
	}

//	public String getGroupCode() {
//		return groupCode;
//	}
//
//	public void setGroupCode(String groupCode) {
//		this.groupCode = groupCode;
//	}
	
}
