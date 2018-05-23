package cn.linkmore.bean.view;

import java.io.Serializable;
import java.util.List;
/**
 * 通用类  - ZTree封装Bean
 * @author liwenlong
 * @version
 *
 */
public class Tree implements Serializable {
    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 4532867265209984782L;
    /**
     * id标识
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 标题
     */
    private String t;
    /**
     * 父标识pid
     */
    private String pId;
    /**
     * 节点默认打开状态 true为打开 false为关闭
     */
    private Boolean open;
    /**
     * 无子节点的父节点设置为true
     */
    private Boolean isParent;
    /**
     * 自定义图标
     */
    private String icon ;
    /**
     * 关闭图标
     */
    private String iconClose;
    /**
     * 开启图标
     */
    private String iconOpen;
    /**
     * m标识
     */
    private String mId;
    /**
     * 编码
     */
    private String code;
    /**
     * 设置可选
     */
    private Boolean chkDisabled;
    /**
     * 子节点信息
     */
    private List<Tree> children;

    private Boolean checked = false;
    /**
     * 控制点击
     */
    private Boolean click;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getT() {
        return t;
    }
    public void setT(String t) {
        this.t = t;
    }
    public String getpId() {
        return pId;
    }
    public void setpId(String pId) {
        this.pId = pId;
    }
    public Boolean getOpen() {
        return open;
    }
    public void setOpen(Boolean open) {
        this.open = open;
    }
    public Boolean getIsParent() {
        return isParent;
    }
    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }
    public List<Tree> getChildren() {
        return children;
    }
    public void setChildren(List<Tree> children) {
        this.children = children;
    }
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIconClose() {
		return iconClose;
	}
	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}
	public String getIconOpen() {
		return iconOpen;
	}
	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getChkDisabled() {
		return chkDisabled;
	}
	public void setChkDisabled(Boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
	public Boolean getClick() {
		return click;
	}
	public void setClick(Boolean click) {
		this.click = click;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

}
