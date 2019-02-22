package cn.linkmore.prefecture.controller.staff.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("网关详情")
public class ResGatewayDetails {
	@ApiModelProperty(value="车区名称")
	private String preName;
	@ApiModelProperty(value="车区Id")
	private Long preId;
	@ApiModelProperty(value="网关编号")
	private String serialNumber;
	@ApiModelProperty(value="分组编号")
	private String groupCode;
	@ApiModelProperty(value="版本")
	private String version;
	@ApiModelProperty(value="服务器名称")
	private String agentName;
	@ApiModelProperty(value="绑定状态 0 未绑定 1已绑定")
	private int bindStatus = 0;
	@ApiModelProperty(value="网关在线状态0离线1在线")
	private int gatewayState = 0;
	@ApiModelProperty(value="sim类型")
	private String simType;
	@ApiModelProperty(value="车位锁信息")
	private List<cn.linkmore.prefecture.controller.staff.response.ResStallLock> stallLocks = new ArrayList<>();

	@ApiModelProperty(value="车位锁数量")
	private int lockNumber = 0;
	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}


	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getSimType() {
		return simType;
	}

	public void setSimType(String simType) {
		this.simType = simType;
	}

	public List<cn.linkmore.prefecture.controller.staff.response.ResStallLock> getStallLocks() {
		return stallLocks;
	}

	public void setStallLocks(List<cn.linkmore.prefecture.controller.staff.response.ResStallLock> stallLocks) {
		this.stallLocks = stallLocks;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public int getBindStatus() {
		if(StringUtils.isNotBlank(groupCode)) {
			bindStatus = 1;
		}
		return bindStatus;
	}

	public void setBindStatus(int bindStatus) {
		this.bindStatus = bindStatus;
	}

	public int getLockNumber() {
		lockNumber = getStallLocks() != null ? getStallLocks().size() : lockNumber;
		return lockNumber;
	}

	public int getGatewayState() {
		return gatewayState;
	}

	public void setGatewayState(int gatewayState) {
		this.gatewayState = gatewayState;
	}
	
	
}
