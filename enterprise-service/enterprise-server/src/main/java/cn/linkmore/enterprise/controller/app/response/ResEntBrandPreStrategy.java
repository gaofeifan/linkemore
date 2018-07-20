package cn.linkmore.enterprise.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 响应-计费策略详情
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("企业品牌车区计费策略")
public class ResEntBrandPreStrategy {
	
	@ApiModelProperty(value = "免费时长")
	private String freeMins;

	@ApiModelProperty(value = "内容描述")
	private String content;
	
	public String getFreeMins() {
		return freeMins;
	}

	public void setFreeMins(String freeMins) {
		this.freeMins = freeMins;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
