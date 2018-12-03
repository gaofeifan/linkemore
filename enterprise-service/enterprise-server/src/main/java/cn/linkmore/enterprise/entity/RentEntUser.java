package cn.linkmore.enterprise.entity;

import java.util.Date;

public class RentEntUser {
	
	private Long id;
	
    private Long rentEntId;

    private String username;

    private String mobile;

    private String plate;
    
    private Date startTime;
    
    private Date endTime;
    
    private String companyName;
    
    public Long getRentEntId() {
        return rentEntId;
    }

    public void setRentEntId(Long rentEntId) {
        this.rentEntId = rentEntId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate == null ? null : plate.trim();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}