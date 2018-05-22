package cn.linkmore.security.entity;
/**
 * 角色页
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class RolePage {
	/**
	 * 主键
	 */
    private Long id;
    /**
     * 页面id
     */
    private Long pageId;
    /**
     * 角色id
     */
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}