package cn.linkmore.ops.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 响应-授权元素
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("响应-授权元素")
public class ResAuthElement {
	@ApiModelProperty(value = "主键", required = false)
	private Long id;
	@ApiModelProperty(value = "路径", required = false)
	private String path;
	@ApiModelProperty(value = "标签id", required = false)
	private String labelId;
	@ApiModelProperty(value = "标签名", required = false)
	private String labelName;
	@ApiModelProperty(value = "接口id", required = false)
	private Long interfaceId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getLabelId() {
		return labelId;
	}
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public Long getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(Long interfaceId) {
		this.interfaceId = interfaceId;
	}
	

}
