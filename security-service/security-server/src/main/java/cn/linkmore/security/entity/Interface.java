package cn.linkmore.security.entity;

import java.util.Date;
/**
 * 接口
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class Interface {
	/**
	 * 主键
	 */
    private Long id;
    /**
	 * 类id
	 */
    private Long clazzId;
    /**
	 * 路径
	 */
    private String path;
    /**
	 * 日志
	 */
    private Short logStatus;
    /**
	 * 权限
	 */
    private Integer authorize;
    /**
	 * 创建时间
	 */
    private Date createTime;
    /**
	 * 名称
	 */
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClazzId() {
        return clazzId;
    }

    public void setClazzId(Long clazzId) {
        this.clazzId = clazzId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Short getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Short logStatus) {
        this.logStatus = logStatus;
    }

    public Integer getAuthorize() {
        return authorize;
    }

    public void setAuthorize(Integer authorize) {
        this.authorize = authorize;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}