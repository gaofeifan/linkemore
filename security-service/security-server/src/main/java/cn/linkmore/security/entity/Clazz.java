package cn.linkmore.security.entity;

import java.util.Date;
/**
 * class类
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class Clazz {
	/**
	 * 主键
	 */
    private Long id;
    /**
     * 类名
     */
    private String name;
    /**
     * 包id
     */
    private Long packageId;
    /**
     * 路径
     */
    private String path;
    /**
     * 创建时间
     */
    private Date createTime;

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

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}