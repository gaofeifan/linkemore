package cn.linkmore.ops.security.request;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("请求页面")
public class ReqPage {
	@ApiModelProperty(value = "主键", required = true)
    private Long id;
	@ApiModelProperty(value = "姓名", required = true)
    private String name;
	@ApiModelProperty(value = "路径", required = true)
    private String path;
	@ApiModelProperty(value = "状态", required = true)
    private Integer status;
	@ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;
	@ApiModelProperty(value = "类别", required = true)
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