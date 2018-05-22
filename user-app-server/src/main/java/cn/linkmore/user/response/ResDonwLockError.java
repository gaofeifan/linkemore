package cn.linkmore.user.response;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("降锁失败异常数据")
public class ResDonwLockError {
	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "异常名称")
	private String name;

	@ApiModelProperty(value = "值")
	private String code;

	@ApiModelProperty(value = "其他")
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
