package cn.linkmore.ops.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 请求接口
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("请求接口")
public class ReqInterface {
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键", required = false)
    private Long id;
	@ApiModelProperty(value = "类id", required = true)
    private Long clazzId;
	@ApiModelProperty(value = "路径", required = true)
    private String path;
	@ApiModelProperty(value = "日志", required = true)
    private Short logStatus;
	@ApiModelProperty(value = "权限", required = true)
    private Integer authorize;
	@ApiModelProperty(value = "创建时间", required = false)
    private Date createTime;
	@ApiModelProperty(value = "名称", required = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClazzId() {
        return clazzId;
    }

    public void setClazzId(Long clazzId) {
        this.clazzId = clazzId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Short getLogStatus() {
        return logStatus;
    }

    public void setLogStatus(Short logStatus) {
        this.logStatus = logStatus;
    }

    public Integer getAuthorize() {
        return authorize;
    }

    public void setAuthorize(Integer authorize) {
        this.authorize = authorize;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}
