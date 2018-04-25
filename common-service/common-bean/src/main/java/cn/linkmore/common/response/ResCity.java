package cn.linkmore.common.response;

import java.util.Date;

/**
 * 请求响应 - 城市信息
 * @author liwenlong
 * @version 2.0
 */
public class ResCity {
	/**
	 * id 主键
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
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
