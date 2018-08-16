package cn.linkmore.enterprise.entity;

import java.util.Date;
/**
 * 企业品牌广告
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class EntBrandAd {
	//主键
    private Long id;
    //企业id
    private Long entId;
    //企业名称
    private String entName;
    //视图image
    private String viewImage;
    //视图url
    private String viewUrl;
    //创建时间
    private Date createTime;
    //结束时间
    private Date endTime;
    //日申请量上限
    private Integer applyCount;
    //开屏展示[0否，1是]广告只有一张
    private Short screen = 0;
    //0未启用，1启用，2禁用
    private Short status;
    //城市id数组
    private String cityIds;
    //0不发广告 1发广告 近当limitStatus=0起作用
    private Short adStatus = 0;
    //优惠券套餐ID
    private Long templateId;

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Integer applyCount) {
        this.applyCount = applyCount;
    }

    public Short getScreen() {
        return screen;
    }

    public void setScreen(Short screen) {
        this.screen = screen;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds == null ? null : cityIds.trim();
    }

	public Short getAdStatus() {
		return adStatus;
	}

	public void setAdStatus(Short adStatus) {
		this.adStatus = adStatus;
	}
}