package cn.linkmore.prefecture.response;

import java.util.Date;
/**
 * 每月目标
 * @author jiaohanbin
 * @version 2.0
 */
public class ResTargetMounth {
    private Long id;

    private String mounth;

    private Long settingId;

    private Long prefectureId;

    private Integer currentOrderCount;

    private Integer currentUserCount;

    private Integer targetUserCount;

    private Integer targetOrderCount;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMounth() {
        return mounth;
    }

    public void setMounth(String mounth) {
        this.mounth = mounth == null ? null : mounth.trim();
    }

    public Long getSettingId() {
        return settingId;
    }

    public void setSettingId(Long settingId) {
        this.settingId = settingId;
    }

    public Long getPrefectureId() {
        return prefectureId;
    }

    public void setPrefectureId(Long prefectureId) {
        this.prefectureId = prefectureId;
    }

    public Integer getCurrentOrderCount() {
        return currentOrderCount;
    }

    public void setCurrentOrderCount(Integer currentOrderCount) {
        this.currentOrderCount = currentOrderCount;
    }

    public Integer getCurrentUserCount() {
        return currentUserCount;
    }

    public void setCurrentUserCount(Integer currentUserCount) {
        this.currentUserCount = currentUserCount;
    }

    public Integer getTargetUserCount() {
        return targetUserCount;
    }

    public void setTargetUserCount(Integer targetUserCount) {
        this.targetUserCount = targetUserCount;
    }

    public Integer getTargetOrderCount() {
        return targetOrderCount;
    }

    public void setTargetOrderCount(Integer targetOrderCount) {
        this.targetOrderCount = targetOrderCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}