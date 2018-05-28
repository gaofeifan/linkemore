package cn.linkmore.ops.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 请求版本bean
 * 
 * @author GFF
 * @Date 2018年5月28日
 * @Version v2.0
 */
@ApiModel("请求版本bean")
public class ReqBaseVersion {

	@ApiModelProperty(value="主键",required = false)
	private Long id;

	@ApiModelProperty(value="版本",required = false)
	private String version;

	@ApiModelProperty(value="版本代号",required = false)
	private Long code;

	@ApiModelProperty(value="名称",required = false)
	private String name;

	@ApiModelProperty(value="此版本是否为有效的，1有效",required = false)
	private Integer status = 0;

	@ApiModelProperty(value="下载地址",required = false)
	private String url;

	@ApiModelProperty(value="适用客户端：1安卓；2ios；",required = false)
	private Integer type;

	@ApiModelProperty(value="是否必须升级；1是",required = false)
	private Integer updateStatus = 0;

	@ApiModelProperty(value="版本创建时间",required = false)
	private Date createTime;

	@ApiModelProperty(value="详情",required = false)
	private String description;

	@ApiModelProperty(value="版本更新时间",required = false)
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version == null ? null : version.trim();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(Integer updateStatus) {
		this.updateStatus = updateStatus;
	}

}
