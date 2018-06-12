package cn.linkmore.common.controller.app.request;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * app异常日志上报请求bean
 * 
 * @author GFF
 * @Date 2018年5月23日
 * @Version v2.0
 */
@ApiModel("app异常日志上报请求bean")
public class ReqUnusualLog {

	@ApiModelProperty(value="主键 非必填",required=false)
	private Long id;

	/**
	 * app版本
	 */
	@ApiModelProperty(value="app版本 必填",required=true)
	@NotBlank(message="app版本不能为空") 
	private String appVersion;

	/**
	 * os版本
	 */
	@ApiModelProperty(value="os版本（系统版本号） 必填",required=true)
	@NotBlank(message="os版本（系统版本号）不能为空") 
	private String osVersion;

	/**
	 * 型号
	 */
	@ApiModelProperty(value="型号 必填",required=true)
	@NotBlank(message="型号不能为空") 
	private String model;

	/**
	 * 客户端类型
	 */
	@ApiModelProperty(value="客户端类型0:微信小程序,1:android,2:ios 必填 ",required=true)
	@NotNull(message="客户端类型不能为空") 
	@Range(min=0,max=2,message="客户端类型有误")
	private Integer clientType;

	/**
	 * 日志级别
	 */
	@ApiModelProperty(value="日志级别 必填",required=false)
//	@NotBlank(message="日志级别不能为空") 
	private String level;

	/**
	 * 品牌
	 */
	@ApiModelProperty(value="品牌 必填",required=true)
	@NotBlank(message="品牌不能为空") 
	private String brand;
	
	/**
	 *  内容
	 */ 
	@ApiModelProperty(value="内容 必填",required=true)
	@NotBlank(message="内容不能为空") 
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion == null ? null : appVersion.trim();
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion == null ? null : osVersion.trim();
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model == null ? null : model.trim();
	}

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level == null ? null : level.trim();
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand == null ? null : brand.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
