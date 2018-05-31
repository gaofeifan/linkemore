package cn.linkmore.ops.security.request;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 请求类
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("请求类")
public class ReqClazz {
	@ApiModelProperty(value = "主键", required = false)
    private Long id;
	
	@ApiModelProperty(value = "类名", required = true)
	@NotBlank(message="类名不能为空")
    private String name;
	
	@ApiModelProperty(value = "包id", required = true)
	@NotBlank(message="包id不能为空")
    private Long packageId;
	
	@ApiModelProperty(value = "路径", required = true)
	@NotBlank(message="路径不能为空")
    private String path;
	@ApiModelProperty(value = "创建时间", required = false)
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
