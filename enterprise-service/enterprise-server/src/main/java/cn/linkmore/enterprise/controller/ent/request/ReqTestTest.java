package cn.linkmore.enterprise.controller.ent.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("操作车位1111")
public class ReqTestTest {

	
	@ApiModelProperty("车位id")
	@NotNull(message="车位不能为空")
	private String stallId;
	
	@ApiModelProperty("清除Key")
	@NotNull(message="车位不能为空")
	private String key;
	
	@ApiModelProperty("操作动作(1:下降 2 升起)")
	@NotNull(message="状态不能为空")
	@Range(max=2,min=1,message="操作动作为1或2")
	private Integer state;

	
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStallId() {
		return stallId;
	}

	public void setStallId(String stallId) {
		this.stallId = stallId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	
	
}
