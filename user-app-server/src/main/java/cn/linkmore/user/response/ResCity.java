package cn.linkmore.user.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("城市信息")
public class ResCity {
	
	public final static int STATUS_CHECKED = 1;
	public final static int STATUS_UNCHECK = 0;
	
	@ApiModelProperty(value = "主键")
	private Long id;
	
	@ApiModelProperty(value = "名称")
	private String name;
	
	@ApiModelProperty(value = "默认状态1选中，0未选中")
	private Integer status = 0;
	
	@ApiModelProperty(value = "行政编码")
	private String adcode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAdcode() {
		return adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode;
	} 
}
