package cn.linkmore.ops.security.request;

import org.hibernate.validator.constraints.NotBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 请求校验字段
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("请求校验字段")
public class ReqCheck {
	
	@ApiModelProperty(value = "属性", required = true)
	@NotBlank(message = "属性不能为空")
	private String property;
	
	@ApiModelProperty(value = "数值", required = true)
	@NotBlank(message = "数值不能为空")
	private String value;
	
	@ApiModelProperty(value = "主键", required = true)
	@NotBlank(message = "主键不能为空")
	private Long id;
	@ApiModelProperty(value = "页面id", required = false)
	private Long pageId;
	
	public Long getPageId() {
		return pageId;
	}
	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
