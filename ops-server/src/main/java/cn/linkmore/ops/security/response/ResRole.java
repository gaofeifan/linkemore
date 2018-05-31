package cn.linkmore.ops.security.response;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
/**
 * 响应角色
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResRole {
	
	@ApiModelProperty(value = "主键", required = true)
    private Long id;
	@ApiModelProperty(value = "角色名称", required = true)
    private String name;
	@ApiModelProperty(value = "状态", required = true)
    private Integer status;
	@ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
	@ApiModelProperty(value = "角色编码", required = true)
    private String code;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}