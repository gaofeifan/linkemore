package cn.linkmore.enterprise.entity;

public class RentEntStall {
    private Long rentEntId;

    private Long stallId;

    private String stallName;
    
    private Long preId;
    
    private String preName;

    public Long getRentEntId() {
        return rentEntId;
    }

    public void setRentEntId(Long rentEntId) {
        this.rentEntId = rentEntId;
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
        this.stallName = stallName == null ? null : stallName.trim();
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
}