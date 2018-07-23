package cn.linkmore.prefecture.response;

/**
 * Result Bean - 车区网关
 * @author liwenlong
 * @version 2.0
 *
 */
public class ResPreGateway {
	private Long preId;
	private String number;
	private Short category;
	public Long getPreId() {
		return preId;
	}
	public void setPreId(Long preId) {
		this.preId = preId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Short getCategory() {
		return category;
	}
	public void setCategory(Short category) {
		this.category = category;
	}
	
}
