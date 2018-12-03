package cn.linkmore.enterprise.request;

public class ReqRentEntUser {
    private Long rentEntId;

    private String username;

    private String mobile;

    private String plate;
    
    private String entId;
    
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

	public String getEntId() {
		return entId;
	}

	public void setEntId(String entId) {
		this.entId = entId;
	}

    
}