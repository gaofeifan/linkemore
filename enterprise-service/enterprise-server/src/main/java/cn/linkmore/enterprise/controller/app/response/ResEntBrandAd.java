package cn.linkmore.enterprise.controller.app.response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 企业品牌广告
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("品牌广告")
public class ResEntBrandAd {
	
	@ApiModelProperty(value = "主键")
    private Long id;
	
	@ApiModelProperty(value = "企业主键")
    private Long entId;
    
	@ApiModelProperty(value = "企业名称")
    private String entName;
    
	@ApiModelProperty(value = "车区id")
    private Long preId;
    
	@ApiModelProperty(value = "车区名称")
    private String preName;
	
	@ApiModelProperty(value = "视图image")
    private String viewImage;

	@ApiModelProperty(value = "视图url")
    private String viewUrl;
	
	@ApiModelProperty(value = "是否品牌用户")
    private Boolean brandUserFlag = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntId() {
        return entId;
    }

    public void setEntId(Long entId) {
        this.entId = entId;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName == null ? null : entName.trim();
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public String getPreName() {
        return preName;
    }

    public void setPreName(String preName) {
        this.preName = preName == null ? null : preName.trim();
    }

    public String getViewImage() {
        return viewImage;
    }

    public void setViewImage(String viewImage) {
        this.viewImage = viewImage == null ? null : viewImage.trim();
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl == null ? null : viewUrl.trim();
    }

	public Boolean getBrandUserFlag() {
		return brandUserFlag;
	}

	public void setBrandUserFlag(Boolean brandUserFlag) {
		this.brandUserFlag = brandUserFlag;
	}
    
}