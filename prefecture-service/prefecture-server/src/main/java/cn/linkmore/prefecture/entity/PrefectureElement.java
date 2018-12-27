package cn.linkmore.prefecture.entity;

public class PrefectureElement {
    private Long id;

    private String eleType;

    private String eleName;

    private String eleSrc;

    private String eleX;

    private String eleY;

    private Short eleStatus;

    private Long elePreId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEleType() {
        return eleType;
    }

    public void setEleType(String eleType) {
        this.eleType = eleType == null ? null : eleType.trim();
    }

    public String getEleName() {
        return eleName;
    }

    public void setEleName(String eleName) {
        this.eleName = eleName == null ? null : eleName.trim();
    }

    public String getEleSrc() {
        return eleSrc;
    }

    public void setEleSrc(String eleSrc) {
        this.eleSrc = eleSrc == null ? null : eleSrc.trim();
    }

    public String getEleX() {
        return eleX;
    }

    public void setEleX(String eleX) {
        this.eleX = eleX == null ? null : eleX.trim();
    }

    public String getEleY() {
        return eleY;
    }

    public void setEleY(String eleY) {
        this.eleY = eleY == null ? null : eleY.trim();
    }

    public Short getEleStatus() {
        return eleStatus;
    }

    public void setEleStatus(Short eleStatus) {
        this.eleStatus = eleStatus;
    }

    public Long getElePreId() {
        return elePreId;
    }

    public void setElePreId(Long elePreId) {
        this.elePreId = elePreId;
    }
}