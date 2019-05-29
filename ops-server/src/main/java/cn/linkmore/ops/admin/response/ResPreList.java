package cn.linkmore.ops.admin.response;


public class ResPreList {

	private Long preId;
	private String preName;
	private ResPreListType listType = new ResPreListType();
	public Long getPreId() {
		return preId;
	}
	public void setPreId(Long preId) {
		this.preId = preId;
	}
	public String getPreName() {
		return preName;
	}
	public void setPreName(String preName) {
		this.preName = preName;
	}
	public ResPreListType getListType() {
		return listType;
	}
	public void setListType(ResPreListType listType) {
		this.listType = listType;
	}
	
	
}
