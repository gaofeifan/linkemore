package cn.linkmore.account.entity;

import java.util.Date;

/**
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
public class NoticeRead {
    private Long id;

    private Long noticeId;

    private Long userId;

    private Date createTime;

    private Long deleteStatus;

    private Long readStatus;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Long deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Long getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Long readStatus) {
        this.readStatus = readStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}