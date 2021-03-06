package cn.linkmore.common.controller.app.response;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("与被授权人关系数据")
public class ResRelationCode {
	@ApiModelProperty(value = "主键",required=false)
	private Long id;

	@ApiModelProperty(value = "名称",required=true)
	@NotBlank(message="关系不能为空") 
	private String name;

	@ApiModelProperty(value = "值",required=true)
	@NotBlank(message="code值不能为空") 
	private String code;

	@ApiModelProperty(value = "其他",required=false)
	private Integer extra;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getExtra() {
		return extra;
	}

	public void setExtra(Integer extra) {
		this.extra = extra;
	}
	
	

}
