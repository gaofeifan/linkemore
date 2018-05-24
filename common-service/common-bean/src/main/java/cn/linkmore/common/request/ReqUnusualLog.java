package cn.linkmore.common.request;

/**
 * app异常日志上报请求bean
 * 
 * @author GFF
 * @Date 2018年5月23日
 * @Version v2.0
 */
public class ReqUnusualLog {

	private Long id;

	/**
	 * app版本
	 */
	private String appVersion;

	/**
	 * os版本
	 */
	private String osVersion;

	/**
	 * 型号
	 */
	private String model;

	/**
	 * 客户端类型
	 */
	private Integer clientType;

	/**
	 * 日志级别
	 */
	private String level;

	/**
	 * 品牌
	 */
	private String brand;
	
	/**
	 *  内容
	 */ 
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
}
