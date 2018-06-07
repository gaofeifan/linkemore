package cn.linkmore.order.request;

/**
 * 更新数据请求bean
 * @author   GFF
 * @Date     2018年5月31日
 * @Version  v2.0
 */
public class RequpdateColumnValue {

	private String properties;
	
	private String value;
	
	private int status;

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
