package cn.linkmore.prefecture.response;
/**
 * 响应-APP车区列表
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResPrefectureList {
	/**
	 * 专区id
	 */
	private Long id;
	
	/**
	 * 空闲车位数量
	 */
	private Integer leisureStall;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLeisureStall() {
		return leisureStall;
	}

	public void setLeisureStall(Integer leisureStall) {
		this.leisureStall = leisureStall;
	}
}
