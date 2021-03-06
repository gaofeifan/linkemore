package cn.linkmore.enterprise.entity;


public class EntOwnerPre {

	
	private Long preId;
	
	private String preName;
	
	private String address;
	
	private Double longitude;
	
	private Double latitude;
	
	private String gateway;
	
	private String underLayer;
	
	private String guideImage;
	
	private String guideRemark;
	
	private Short pathFlag = 0;

	public Short getPathFlag() {
		return pathFlag;
	}

	public void setPathFlag(Short pathFlag) {
		this.pathFlag = pathFlag;
	}

	public String getGuideImage() {
		return guideImage;
	}

	public void setGuideImage(String guideImage) {
		this.guideImage = guideImage;
	}

	public String getGuideRemark() {
		return guideRemark;
	}

	public void setGuideRemark(String guideRemark) {
		this.guideRemark = guideRemark;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getUnderLayer() {
		return underLayer;
	}

	public void setUnderLayer(String underLayer) {
		this.underLayer = underLayer;
	}
	
	
}
