package cn.linkmore.common.controller.app.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("版本")
public class ReqVersion {

	@ApiModelProperty(value="客户端",required=true)
	@NotNull(message="客户端不能为空")
	private Short client;

	@ApiModelProperty(value="型号",required=true)
	@NotBlank(message="型号不能为空")
	private String model;

	@ApiModelProperty(value="os版本",required=true)
	@NotBlank(message="os版本不能为空")
	private String osVersion; 

	@ApiModelProperty(value="版本",required=true)
	@NotBlank(message="版本不能为空")
	private String version;

	public Short getClient() {
		return client;
	}

	public void setClient(Short client) {
		this.client = client;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	} 
	
	
	
}
