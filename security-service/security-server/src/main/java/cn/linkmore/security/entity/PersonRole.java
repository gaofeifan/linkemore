package cn.linkmore.security.entity;
/**
 * 账户角色
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class PersonRole {
	/**
	 * 主键
	 */
    private Long id;
    /**
	 * 角色id
	 */
    private Long roleId;
    /**
	 * 用户id
	 */
    private Long personId;

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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }
}