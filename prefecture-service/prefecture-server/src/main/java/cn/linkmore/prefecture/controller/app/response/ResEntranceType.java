package cn.linkmore.prefecture.controller.app.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车区入口分类")
public class ResEntranceType {
	
	@ApiModelProperty(value = "分类名称")
	private String entranceType;
	
	@ApiModelProperty(value = "分类列表详情")
	private List<String> entranceDetail;

	public String getEntranceType() {
		return entranceType;
	}

	public void setEntranceType(String entranceType) {
		this.entranceType = entranceType;
	}

	public List<String> getEntranceDetail() {
		return entranceDetail;
	}

	public void setEntranceDetail(List<String> entranceDetail) {
		this.entranceDetail = entranceDetail;
	}
}
