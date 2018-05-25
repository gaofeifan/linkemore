package cn.linkmore.ops.response;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 响应-字典
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("响应字典")
public class ResDict {
	@ApiModelProperty(value = "主键", required = false)
    private Long id;
	@ApiModelProperty(value = "分组id", required = false)
    private Long groupId;
	@ApiModelProperty(value = "名称", required = false)
    private String name;
	@ApiModelProperty(value = "编码", required = false)
    private String code;
	@ApiModelProperty(value = "排序", required = false)
    private Integer orderIndex;
	@ApiModelProperty(value = "创建时间", required = false)
    private Date createTime;
	@ApiModelProperty(value = "拓展", required = false)
    private Integer extra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getExtra() {
        return extra;
    }

    public void setExtra(Integer extra) {
        this.extra = extra;
    }
}
