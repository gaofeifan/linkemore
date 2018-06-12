package cn.linkmore.account.controller.app.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户指南响应bean
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
@ApiModel("用户指南响应bean")
public class ResUserGuide {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 4907174843003273108L;
	@ApiModelProperty(value="主键")
	private Long id;
	@ApiModelProperty(value="标题")
	private String title;
	@ApiModelProperty(value="级别")
	private Integer level;
	@ApiModelProperty(value="类型")
	private Integer type;
	@ApiModelProperty(value="路径")
	private String url;
	@ApiModelProperty(value="子集合")
	private List<ResUserGuide> children;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<ResUserGuide> getChildren() {
		return children;
	}
	public void setChildren(List<ResUserGuide> children) {
		this.children = children;
	}
	
	
}
