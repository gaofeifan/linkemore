package cn.linkmore.security.request;

import java.util.Date;
/**
 * 请求页面元素
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ReqPageElement {
	/**
	 * 主键
	 */
    private Long id;
    /**
     * 元素名称
     */
    private String name;
    /**
     * 接口id
     */
    private Long interfaceId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 页面id
     */
    private Long pageId;
    /**
     * 标签名称
     */
    private String labelName;
    /**
     * 标签id
     */
    private String labelId;
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

    public Long getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName == null ? null : labelName.trim();
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId == null ? null : labelId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}