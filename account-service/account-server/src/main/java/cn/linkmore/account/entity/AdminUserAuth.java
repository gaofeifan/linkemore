package cn.linkmore.account.entity;

/**
 * 管理员用户权限
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
public class AdminUserAuth {
    private Long id;

    private Long userId;

    private Long authId;

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

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }
}