package cn.linkmore.enterprise.controller.ent.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("修改企业车区")
public class ReqUpdateEntPreture {
	
	@ApiModelProperty(value = "企业车区id", required = true)
	private Long id ;
	
	@ApiModelProperty(value = "车区id", required = true)
	private Long preId;

	@ApiModelProperty(value = "车区名称", required = true)
    private String preName;
	@ApiModelProperty(value = "企业id", required = true)
    private Long entId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public Long getEntId() {
		return entId;
	}

	public void setEntId(Long entId) {
		this.entId = entId;
	}

    
}
