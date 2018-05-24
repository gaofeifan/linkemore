package cn.linkmore.security.request;
/**
 * 请求校验字段
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ReqCheck {
	/**
	 * 属性
	 */
	private String property;
	/**
	 * 数值
	 */
	private String value;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 页面id
	 */
	private Long pageId;
	
	public Long getPageId() {
		return pageId;
	}
	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
