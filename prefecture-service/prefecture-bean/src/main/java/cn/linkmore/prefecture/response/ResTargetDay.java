package cn.linkmore.prefecture.response;

import java.util.Date;
/**
 * 每日目标
 * @author jiaohanbin
 * @version 2.0
 */
public class ResTargetDay {
    private Long id;

    private String day;

    private Long settingId;

    private Long mounthId;

    private Long prefectureId;

    private Integer targetOrderCount;

    private Integer currentOrderCount;

    private Integer targetUserCount;

    private Integer currentUserCount;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day == null ? null : day.trim();
    }

    public Long getSettingId() {
        return settingId;
    }

    public void setSettingId(Long settingId) {
        this.settingId = settingId;
    }

    public Long getMounthId() {
        return mounthId;
    }

    public void setMounthId(Long mounthId) {
        this.mounthId = mounthId;
    }

    public Long getPrefectureId() {
        return prefectureId;
    }

    public void setPrefectureId(Long prefectureId) {
        this.prefectureId = prefectureId;
    }

    public Integer getTargetOrderCount() {
        return targetOrderCount;
    }

    public void setTargetOrderCount(Integer targetOrderCount) {
        this.targetOrderCount = targetOrderCount;
    }

    public Integer getCurrentOrderCount() {
        return currentOrderCount;
    }

    public void setCurrentOrderCount(Integer currentOrderCount) {
        this.currentOrderCount = currentOrderCount;
    }

    public Integer getTargetUserCount() {
        return targetUserCount;
    }

    public void setTargetUserCount(Integer targetUserCount) {
        this.targetUserCount = targetUserCount;
    }

    public Integer getCurrentUserCount() {
        return currentUserCount;
    }

    public void setCurrentUserCount(Integer currentUserCount) {
        this.currentUserCount = currentUserCount;
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