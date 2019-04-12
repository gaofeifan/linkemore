package cn.linkmore.enterprise.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("分享车位")
public class ResShareStall {

	private String title = "凌猫停车";
	
	@ApiModelProperty(value="描述")
	private String describe = "您的朋友分享给您一个长租车位，查看一下";
	
	@ApiModelProperty(value="路径")
	private String image="https://oss.pabeitech.com/image/2019/04/10/275_max.png";

	@ApiModelProperty(value="点击事件")
	private String clickEvent;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getClickEvent() {
		return clickEvent;
	}

	public void setClickEvent(String clickEvent) {
		this.clickEvent = clickEvent;
	}
}
