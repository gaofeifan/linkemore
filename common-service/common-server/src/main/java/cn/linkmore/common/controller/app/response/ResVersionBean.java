package cn.linkmore.common.controller.app.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * @Description  版本响应bean  
 * @author  GFF
 * @Date     2018年5月11日
 */
@ApiModel("当前版本响应bean")
public class ResVersionBean implements Serializable{
	/**
	 * serial id
	 */
	private static final long serialVersionUID = -2937247643771685452L;

	@ApiModelProperty(value="app版本号")
	private String version;

	@ApiModelProperty(value="版本代号")
    private Long versionCode;

	@ApiModelProperty(value="版本名")
    private String versionName; 

	@ApiModelProperty(value="适用客户端：1安卓；2ios；")
    private String downloadUrl; 
    
	@ApiModelProperty(value="是否必须升级；1是")
    private Integer mustUpdate; 

	@ApiModelProperty(value="详情")
    private String description;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Long versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public Integer getMustUpdate() {
		return mustUpdate;
	}

	public void setMustUpdate(Integer mustUpdate) {
		this.mustUpdate = mustUpdate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ResVersionBean [version=" + version + ", versionCode=" + versionCode + ", versionName=" + versionName
				+ ", downloadUrl=" + downloadUrl + ", mustUpdate=" + mustUpdate + ", description=" + description + "]";
	} 
    
    
}
