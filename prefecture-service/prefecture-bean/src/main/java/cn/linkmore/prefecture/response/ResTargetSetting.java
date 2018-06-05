package cn.linkmore.prefecture.response;

import java.util.Date;
/**
 * 目标设定
 * @author jiaohanbin
 * @version 2.0
 */
public class ResTargetSetting {
    private Long id;

    private Long prefectureId;

    private Long enterpriseId;

    private Integer dayUserCount;

    private Integer dayOrderCount;

    private Integer mounthOrderCount;

    private Integer mounthUserCount;

    private Long operatorId;

    private String operatorName;

    private Date createTime;

    private String remark;

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

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getDayUserCount() {
        return dayUserCount;
    }

    public void setDayUserCount(Integer dayUserCount) {
        this.dayUserCount = dayUserCount;
    }

    public Integer getDayOrderCount() {
        return dayOrderCount;
    }

    public void setDayOrderCount(Integer dayOrderCount) {
        this.dayOrderCount = dayOrderCount;
    }

    public Integer getMounthOrderCount() {
        return mounthOrderCount;
    }

    public void setMounthOrderCount(Integer mounthOrderCount) {
        this.mounthOrderCount = mounthOrderCount;
    }

    public Integer getMounthUserCount() {
        return mounthUserCount;
    }

    public void setMounthUserCount(Integer mounthUserCount) {
        this.mounthUserCount = mounthUserCount;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}