package cn.linkmore.prefecture.entity;
/**
 * entity 授权车位
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class AdminAuthStall {
    private Long id;

    private Long stallId;

    private Long authId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStallId() {
        return stallId;
    }

    public void setStallId(Long stallId) {
        this.stallId = stallId;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }
}