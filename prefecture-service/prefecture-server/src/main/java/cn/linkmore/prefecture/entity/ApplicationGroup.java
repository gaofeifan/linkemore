package cn.linkmore.prefecture.entity;

import java.util.Date;

public class ApplicationGroup {
    private Long id;

    private String name;

    private Long userGroupId;

    private Short controlAttribute;

    private Short controlArea;

    private Long preGroupId;

    private Long preId;

    private Short cycleTime;

    private String timeSlot;

    private Long operatorId;

    private Short probability;

    private Date createTime;

    private Date updateTime;

    private Short status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Short getControlAttribute() {
        return controlAttribute;
    }

    public void setControlAttribute(Short controlAttribute) {
        this.controlAttribute = controlAttribute;
    }

    public Short getControlArea() {
        return controlArea;
    }

    public void setControlArea(Short controlArea) {
        this.controlArea = controlArea;
    }

    public Long getPreGroupId() {
        return preGroupId;
    }

    public void setPreGroupId(Long preGroupId) {
        this.preGroupId = preGroupId;
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public Short getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(Short cycleTime) {
        this.cycleTime = cycleTime;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot == null ? null : timeSlot.trim();
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Short getProbability() {
        return probability;
    }

    public void setProbability(Short probability) {
        this.probability = probability;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}