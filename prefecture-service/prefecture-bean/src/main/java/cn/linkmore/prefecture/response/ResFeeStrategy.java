package cn.linkmore.prefecture.response;

/**
 * 后台响应-计费策略下拉框数据
 * @author jiaohanbin
 * @version 2.0
 */
public class ResFeeStrategy {
	/**
	 * 主键
	 */
    private Long id;
    /**
     * 计费策略名称
     */
    private String name;
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
}