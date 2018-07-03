package cn.linkmore.prefecture.request;

public class ReqApplicationGroup {

    private String name;

    private Long userGroupId;
    
    private Integer controlAttribute;

    private Integer controlArea;
    
    private Long preGroupId;
    
    private Long preId;
    
    private Integer cycleTime;
    
    //时间段
    private String timeSlot;
    
    private Integer probability;
    
    private Long operatorId;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

	public Integer getControlAttribute() {
		return controlAttribute;
	}

	public void setControlAttribute(Integer controlAttribute) {
		this.controlAttribute = controlAttribute;
	}

	public Integer getControlArea() {
		return controlArea;
	}

	public void setControlArea(Integer controlArea) {
		this.controlArea = controlArea;
	}

	public Long getPreGroupId() {
		return preGroupId;
	}

	public void setPreGroupId(Long preGroupId) {
		this.preGroupId = preGroupId;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public Integer getCycleTime() {
		return cycleTime;
	}

	public void setCycleTime(Integer cycleTime) {
		this.cycleTime = cycleTime;
	}

	public String getTimeSlot() {
		return timeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}

	public Integer getProbability() {
		return probability;
	}

	public void setProbability(Integer probability) {
		this.probability = probability;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
	
}
