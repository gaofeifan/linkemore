package cn.linkmore.ops.security.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 请求页面元素
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("请求页面元素")
public class ReqPageElement {
	@ApiModelProperty(value = "主键", required = true)
    private Long id;
	@ApiModelProperty(value = "元素名称", required = true)
    private String name;
	@ApiModelProperty(value = "接口id", required = true)
    private Long interfaceId;
	@ApiModelProperty(value = "状态", required = true)
    private Integer status;
	@ApiModelProperty(value = "页面id", required = true)
    private Long pageId;
	@ApiModelProperty(value = "标签名称", required = true)
    private String labelName;
	@ApiModelProperty(value = "标签id", required = true)
    private String labelId;
	@ApiModelProperty(value = "创建时间", required = true)
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