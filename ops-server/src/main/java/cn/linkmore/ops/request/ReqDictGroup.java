package cn.linkmore.ops.request;

import java.util.Date;
import org.hibernate.validator.constraints.NotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 请求字典分组
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("请求字典分组")
public class ReqDictGroup {
	@ApiModelProperty(value = "主键", required = false)
    private Long id;
	@ApiModelProperty(value = "分组名称", required = true)
	@NotBlank(message = "分组名称不能为空")
    private String name;
	@ApiModelProperty(value = "分组编号", required = true)
	@NotBlank(message = "分组编号不能为空")
    private String code;
	@ApiModelProperty(value = "排序", required = true)
    private Integer orderIndex;
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
}
