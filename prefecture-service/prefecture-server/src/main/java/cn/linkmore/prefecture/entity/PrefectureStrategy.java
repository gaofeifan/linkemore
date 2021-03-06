package cn.linkmore.prefecture.entity;

import java.util.Date;
import java.util.List;
/**
 * 车区策略
 * @author llh
 *
 */
public class PrefectureStrategy {
	/**
	 * 主键id
	 */
    private Long id;
	/**
	 * 车区id
	 */
    private Long prefectureId;
    /**
     * 车区名称
     */
    private String prefectureName;
    /**
     * 车区策略名称
     */
    private String name;
    /**
     * 车区策略简介
     */
    private String detail;
    /**
     * 创建用户id
     */
    private Long createUserId;
    /**
     * 创建用户名
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后修改人用户id
     */
    private Long updateUserId;
    /**
     * 最后修改人用户名
     */
    private String updateUserName;
    /**
     * 最后修改时间
     */
    private Date updateTime;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 车区分组,分时,费用策略
     */
    private List<PrefectureStrategyGroup> strategyGroup;
    /**
     * 车位锁时段配置
     */
    private List<PrefectureLockTime> lockTime;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrefectureId() {
        return prefectureId;
    }

    public void setPrefectureId(Long prefectureId) {
        this.prefectureId = prefectureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
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
        this.createUserName = createUserName == null ? null : createUserName.trim();
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
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

	public List<PrefectureStrategyGroup> getStrategyGroup() {
		return strategyGroup;
	}

	public void setStrategyGroup(List<PrefectureStrategyGroup> strategyGroup) {
		this.strategyGroup = strategyGroup;
	}

	public List<PrefectureLockTime> getLockTime() {
		return lockTime;
	}

	public void setLockTime(List<PrefectureLockTime> lockTime) {
		this.lockTime = lockTime;
	}

	public String getPrefectureName() {
		return prefectureName;
	}

	public void setPrefectureName(String prefectureName) {
		this.prefectureName = prefectureName;
	}


    
}