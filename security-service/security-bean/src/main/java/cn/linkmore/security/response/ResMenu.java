package cn.linkmore.security.response;

import java.util.Date;
import java.util.List;
/**
 * 请求菜单
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResMenu implements Comparable<ResMenu>{
	/**
	 * 主键
	 */
    private Long id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 父类id
     */
    private Long parentId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 图标
     */
    private String icon;
    /**
     * 页面id
     */
    private Long pageId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 排序
     */
    private Integer orderIndex;
    /**
     * 层级
     */
    private Integer level;
    /**
     * 子节点
     */
    private List<ResMenu> children	;
    
    private String path;

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<ResMenu> getChildren() {
		return children;
	}

	public void setChildren(List<ResMenu> children) {
		this.children = children;
	}

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

	@Override
	public int compareTo(ResMenu menu) {
		 return this.orderIndex.compareTo(menu.orderIndex); 
	}
}