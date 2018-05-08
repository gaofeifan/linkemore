package cn.linkmore.common.request;
/**
 * 请求 - 城市信息
 * @author liwenlong
 * @version 2。0
 *
 */
public class ReqCity {
	
	/**
	 * 城市ID
	 */
	private Long id;
	
	/**
	 * 行政编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name; 
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
