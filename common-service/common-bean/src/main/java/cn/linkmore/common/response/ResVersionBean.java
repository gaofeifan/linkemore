package cn.linkmore.common.response;

import java.io.Serializable;

import javax.annotation.Generated;
/**
 * @Description  版本响应bean  
 * @author  GFF
 * @Date     2018年5月11日
 */
public class ResVersionBean implements Serializable{
	/**
	 * serial id
	 */
	private static final long serialVersionUID = -2937247643771685452L;

	private String version;

    private Long versionCode;

    private String versionName; 

    private String downloadUrl; 
    
    private Integer mustUpdate; 

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
