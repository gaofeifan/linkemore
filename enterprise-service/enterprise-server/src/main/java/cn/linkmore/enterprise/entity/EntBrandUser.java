package cn.linkmore.enterprise.entity;
/**
 * 授权品牌用户
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class EntBrandUser {
	//主键
    private Long id;
    //企业id
    private Long entId;
    //品牌车企id
    private Long entPreId;
    //车区id
    private Long preId;
    //手机号
    private String mobile;
    //真实姓名
    private String realname;
    //车牌号
    private String plateNo;
    //用户id
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

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo == null ? null : plateNo.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}