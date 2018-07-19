package cn.linkmore.enterprise.entity;

public class EntRentUser {
    private Long id;

    private Long entId;

    private Long entPreId;

    private Long preId;

    private String mobile;

    private String realname;

    private String plate;

    private Long stalllId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate == null ? null : plate.trim();
    }

    public Long getStalllId() {
        return stalllId;
    }

    public void setStalllId(Long stalllId) {
        this.stalllId = stalllId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}