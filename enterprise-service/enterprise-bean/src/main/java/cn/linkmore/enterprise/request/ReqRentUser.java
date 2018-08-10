package cn.linkmore.enterprise.request;

import java.util.Date;

/**
 * @author   GFF
 * @Date     2018年8月1日
 * @Version  v2.0
 */
public class ReqRentUser {

	private Long id;
	
	private Long entId;
	
	private Long entPreId;
	
	private Long preId;

	private Long StallId;
	
	private String entName;
	
	private String preName;
	
	private String stallName;
	
	private String mobile;
	
	private String realname;
	
	private String plate;
	
	private Long userId;
	
	private Date startTime;
	
	private Date endTime;

	public Long getEntId() {
		return entId;
	}

	public void setEntId(Long entId) {
		this.entId = entId;
	}

	public Long getEntPreId() {
		return entPreId;
	}

	public void setEntPreId(Long entPreId) {
		this.entPreId = entPreId;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public Long getStallId() {
		return StallId;
	}

	public void setStallId(Long stallId) {
		StallId = stallId;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
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

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
