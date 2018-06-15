package cn.linkmore.coupon.response;

import java.util.Date;
/**
 * 响应活动的Bean
 * @author PPYX
 */
public class ResSubjectBean {

	private String type; //类型: 1 分享领卷；2 新注册用户
    private Long id; //主键
    private String title; //  标题
    private String shortTitle ;//  短标题
    private String intro; //  摘要
    private String tempName; // 优惠套餐
    private String operatorName; // 创建人
    private Integer status; //状态：0 下架；1 使用中
    private Date createTime; //  创建时间
    private Date updateTime; //  更新时间
	private String url; // url
	private String imgUrl; // 图url
	private String totalAmount; // 套餐总金额
	private Long templateId;// 停车券ID
	private Long operatorId;// 创建人ID
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getShortTitle() {
		return shortTitle;
	}
	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getTempName() {
		return tempName;
	}
	public void setTempName(String tempName) {
		this.tempName = tempName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
}
