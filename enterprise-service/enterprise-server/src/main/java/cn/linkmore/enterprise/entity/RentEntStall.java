package cn.linkmore.enterprise.entity;

public class RentEntStall {
    private Long rentEntId;

    private Long stallId;

    private String stallName;

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
}