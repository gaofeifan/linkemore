package cn.linkmore.enterprise.request;

/**
 * 请求校验bean
 * @author   GFF
 * @Date     2018年5月28日
 * @Version  v2.0
 */
public class ReqCheck {

	private String property;
	
	private String value;
	
	private Long id;

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
