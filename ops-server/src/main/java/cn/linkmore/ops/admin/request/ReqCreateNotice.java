package cn.linkmore.ops.admin.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 消息管理
 * @author   GFF
 * @Date     2018年6月22日
 * @Version  v2.0
 */
@ApiModel
public class ReqCreateNotice {


	@ApiModelProperty(value = "id")
	private Long id;
	@ApiModelProperty(value = "分类(0 文本,1 H5页面)")
	private Integer type;
	@ApiModelProperty(value = "标题名称")
	private String title;
	@ApiModelProperty(value = "描述")
	private String description;
	@ApiModelProperty(value = "内容")
	private String content;
	@ApiModelProperty(value = "URL")
	private String url;


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
