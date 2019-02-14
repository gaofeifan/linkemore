package cn.linkmore.enterprise.entity;

public class FixedPlate {
    private Long id;

    private String plateNo;

    private Long fixedId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo == null ? null : plateNo.trim();
    }

    public Long getFixedId() {
        return fixedId;
    }

    public void setFixedId(Long fixedId) {
        this.fixedId = fixedId;
    }
}