package cn.linkmore.ops.admin.response;
/**
 * 响应-后台车区列表
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResPreList {
	/**
	 * 专区id
	 */
	private Long id;
	/**
	 * 专区名称
	 */
	private String name;
	/**
	 * 状态
	 */
	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
