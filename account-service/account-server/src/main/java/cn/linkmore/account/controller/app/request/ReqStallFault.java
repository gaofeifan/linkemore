package cn.linkmore.account.controller.app.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("故障车位")
public class ReqStallFault {

	@ApiModelProperty(value="车位id")
	private Long stallId;
	
	@ApiModelProperty(value="故障原因id")
	private Long dictId;
	
	@ApiModelProperty(value="故障原因名称")
	private String dictName;
	
	@ApiModelProperty(value="其他")
	private String extra;

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public Long getDictId() {
		return dictId;
	}

	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
}
