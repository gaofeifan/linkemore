package cn.linkmore.security.response;

import java.util.Date;
/**
 * 角色
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResRole {
	/**
	 * 主键
	 */
    private Long id;
    /**
	 * 角色名称
	 */
    private String name;
    /**
	 * 状态
	 */
    private Integer status;
    /**
	 * 创建时间
	 */
    private Date createTime;
    /**
	 * 角色编码
	 */
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