package cn.linkmore.enterprise.entity;

import java.util.Date;
/**
 * 企业品牌申请人
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class EntBrandApplicant {
	//主键
    private Long id;
    //企业id
    private Long entId;
    //企业名称
    private String entName;
    //用户id
    private Long userId;
    //用户名称
    private String username;
    //手机号
    private String mobile;
    //车牌号
    private String plateNo;
    //创建时间
    private Date createTime;
    //车区id
    private Long preId;
    //车区名称
    private String preName;
    
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

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

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

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName == null ? null : entName.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}