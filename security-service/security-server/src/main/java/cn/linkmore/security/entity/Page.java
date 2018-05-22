package cn.linkmore.security.entity;

import java.util.Date;
/**
 * 页面
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class Page {
	/**
	 * 主键
	 */
    private Long id;
    /**
	 * 姓名
	 */
    private String name;
    /**
	 * 路径
	 */
    private String path;
    /**
     * 状态
     */
    private Integer status;
    /**
	 * 创建时间
	 */
    private Date createTime;
    /**
	 * 类别
	 */
    private Integer categoryId;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}