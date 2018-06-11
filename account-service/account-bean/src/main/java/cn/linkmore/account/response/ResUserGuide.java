package cn.linkmore.account.response;

import java.util.List;

/**
 * 用户指南响应bean
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
public class ResUserGuide {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 4907174843003273108L;
	private Long id;
	private String title;
	private Integer level;
	private Integer type;
	private String url;
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
