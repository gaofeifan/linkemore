package cn.linkmore.security.entity;
/**
 * 角色元素
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class RoleElement {
	/**
	 * 主键
	 */
    private Long id;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 元素id
     */
    private Long elementId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getElementId() {
        return elementId;
    }

    public void setElementId(Long elementId) {
        this.elementId = elementId;
    }
}