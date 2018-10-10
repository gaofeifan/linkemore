package cn.linkmore.enterprise.controller.staff.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel("操作原因备注的Bean")
public class CauseReponseBean implements Serializable {

    private static final long serialVersionUID = 6333509164676661331L;
    @ApiModelProperty(value = "主键ID")
    private Long id ;
    @ApiModelProperty(value = "内容")
    private String name;

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
}

