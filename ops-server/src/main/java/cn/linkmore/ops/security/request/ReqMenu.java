package cn.linkmore.ops.security.request;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 请求菜单
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("请求菜单")
public class ReqMenu implements Comparable<ReqMenu>{
	@ApiModelProperty(value = "主键", required = true)
    private Long id;
	@ApiModelProperty(value = "菜单名称", required = true)
    private String name;
	@ApiModelProperty(value = "父类id", required = true)
    private Long parentId;
	@ApiModelProperty(value = "状态", required = true)
    private Integer status;
	@ApiModelProperty(value = "图标", required = true)
    private String icon;
	@ApiModelProperty(value = "页面id", required = true)
    private Long pageId;
	@ApiModelProperty(value = "创建时间", required = false)
    private Date createTime;
	@ApiModelProperty(value = "排序", required = true)
    private Integer orderIndex;
	@ApiModelProperty(value = "层级", required = false)
    private Integer level;
	@ApiModelProperty(value = "子节点", required = false)
    private List<ReqMenu> children	;
    
    private String path;

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<ReqMenu> getChildren() {
		return children;
	}

	public void setChildren(List<ReqMenu> children) {
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
	public int compareTo(ReqMenu menu) {
		 return this.orderIndex.compareTo(menu.orderIndex); 
	}
}