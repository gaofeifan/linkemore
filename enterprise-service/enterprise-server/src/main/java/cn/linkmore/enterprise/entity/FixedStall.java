package cn.linkmore.enterprise.entity;

public class FixedStall {
    private Long id;

    private Long fixedId;

    private Long stallId;

    private String stallName;

    private Short status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFixedId() {
        return fixedId;
    }

    public void setFixedId(Long fixedId) {
        this.fixedId = fixedId;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}