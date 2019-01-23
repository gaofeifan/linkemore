package cn.linkmore.enterprise.request;

import java.util.Date;

/**
 * @author   GFF
 * @Date     2018年8月1日
 * @Version  v2.0
 * @table t_ent_rent_user
 */
public class ReqRentUser {

	private Long id;
	
	private Long entId;
	
	private Long entPreId;
	
	private Long preId;

	private Long StallId;
	
	private String entName;
	
	private String preName;
	
	private String stallName;
	
	private String mobile;
	
	private String realname;
	
	private String plate;
	
	private Long userId;
	
	private Date startTime;
	
	private Date endTime;
	
	private String startDate;
	
	private String endDate;
	
	private Short type;
	
	private Long companyId;
	
	private String companyName;

	/**
     * 创建用户Id
     */
    private Long createUserId;
    /**
     * 创建用户名称
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新用户Id
     */
    private Long updateUserId;
    /**
     * 更新用户名称
     */
    private String updateUserName;
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 创建企业Id
     */
    private Long createEntId;
    /**
     * 创建企业名称
     */
    private String createEntName;
    
    public Long getCreateEntId() {
		return createEntId;
	}

	public void setCreateEntId(Long createEntId) {
		this.createEntId = createEntId;
	}

	public String getCreateEntName() {
		return createEntName;
	}

	public void setCreateEntName(String createEntName) {
		this.createEntName = createEntName;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public Long getStallId() {
		return StallId;
	}

	public void setStallId(Long stallId) {
		StallId = stallId;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
