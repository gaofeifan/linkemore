package cn.linkmore.ops.security.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("请求账户角色")
public class ReqPersonRole {
	@ApiModelProperty(value = "主键", required = true)
    private Long id;
	@ApiModelProperty(value = "角色id", required = true)
    private Long roleId;
	@ApiModelProperty(value = "用户id", required = true)
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