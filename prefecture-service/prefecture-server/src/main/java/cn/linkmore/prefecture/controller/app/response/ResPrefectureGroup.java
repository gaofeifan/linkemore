package cn.linkmore.prefecture.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车区分组信息")
public class ResPrefectureGroup { 
	@ApiModelProperty(value = "车区主键")
	private Long preId; 
	
	@ApiModelProperty(value = "车区分组主键")
	private Long groupId; 
	
	@ApiModelProperty(value = "车区分组名称")
	private String groupName;
	
	@ApiModelProperty(value = "空闲车位数量")
	private Integer leisureStall;
	
	@ApiModelProperty(value = "计价规则")
	private String desc;
	
	@ApiModelProperty(value = "是否启用： 1禁用2启用")
	private Integer status;
	
	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getLeisureStall() {
		return leisureStall;
	}

	public void setLeisureStall(Integer leisureStall) {
		this.leisureStall = leisureStall;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
 }
