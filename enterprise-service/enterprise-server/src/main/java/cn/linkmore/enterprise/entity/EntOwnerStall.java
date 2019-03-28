package cn.linkmore.enterprise.entity;


public class EntOwnerStall {
	//1代表自有车位 2代表授权车位
	private String stallType;
	
	private Long preId;
	
	private Long stallId;
	
	private String preName;
	
	private String stallName;
	
	private String mobile;
	
	private String plate;

	private String startTime;

	private String endTime;

	private String stallLocal;
	
	private String routeGuidance;
	
	private String imageUrl;
	
	private Long status;
	
	private Long lockStatus;
	
	private String lockSn;
	
	private Long userId;
	
	private Short rentOmType = 0;
	
	private Short rentMoType = 0;
	
	public String getStallType() {
		return stallType;
	}

	public void setStallType(String stallType) {
		this.stallType = stallType;
	}

	public Short getRentOmType() {
		return rentOmType;
	}

	public void setRentOmType(Short rentOmType) {
		this.rentOmType = rentOmType;
	}

	public Short getRentMoType() {
		return rentMoType;
	}

	public void setRentMoType(Short rentMoType) {
		this.rentMoType = rentMoType;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public String getLockSn() {
		return lockSn;
	}

	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Long lockStatus) {
		this.lockStatus = lockStatus;
	}

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public String getStallLocal() {
		return stallLocal;
	}

	public void setStallLocal(String stallLocal) {
		this.stallLocal = stallLocal;
	}

	public String getRouteGuidance() {
		return routeGuidance;
	}

	public void setRouteGuidance(String routeGuidance) {
		this.routeGuidance = routeGuidance;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
