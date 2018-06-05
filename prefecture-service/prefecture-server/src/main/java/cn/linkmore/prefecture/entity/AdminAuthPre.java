package cn.linkmore.prefecture.entity;
/**
 * entity 授权车区
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class AdminAuthPre {
    private Long id;

    private Long authId;

    private Long preId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }
}