package cn.linkmore.third.response;

import java.util.Date;
/**
 * 响应 - 微信用户
 * @author liwenlong
 * @version 2.0
 */
public class ResFans {
	/**
	 * openId
	 */
    private String id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    private String headurl;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 状态
     */
    private Short status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 注册状态
     */
    private Short registerStatus;

    /**
     * unionid
     */
    private String unionid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl == null ? null : headurl.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Short getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(Short registerStatus) {
        this.registerStatus = registerStatus;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid == null ? null : unionid.trim();
    }
}
