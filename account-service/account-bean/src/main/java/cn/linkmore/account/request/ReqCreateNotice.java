package cn.linkmore.account.request;


/**
 * 消息管理
 * @author   GFF
 * @Date     2018年6月22日
 * @Version  v2.0
 */
public class ReqCreateNotice {


	private Long id;
	private Integer type;
	private String title;
	private String description;
	private String content;
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
