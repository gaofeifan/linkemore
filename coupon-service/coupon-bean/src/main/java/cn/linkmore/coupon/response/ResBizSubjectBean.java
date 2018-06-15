package cn.linkmore.coupon.response;

import java.util.Date;
/**
 * 响应活动的Bean
 * @author jiaohanbin
 * @version 2.0
 */
public class ResBizSubjectBean {

	private String type; //类型: 1 分享领卷；2 新注册用户
    private Long id; //主键
    private String title; //  标题
    private String shortTitle ;//  短标题
    private String intro; //  摘要
    private String comboName; // 优惠套餐
    private String packName; // 优惠包
    private String operatorName; // 创建人
    private Integer status; //状态：0 下架；1 使用中
    private Date createTime; //  创建时间
    private Date updateTime; //  更新时间
	private String url; // url
	private String imgUrl; // 图url
	private String totalAmount; // 套餐总金额
	private Long comboId;// 优惠套餐ID
	private Long packId;// 优惠包ID
	private Long operatorId;// 创建人ID

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getComboId() {
		return comboId;
	}

	public void setComboId(Long comboId) {
		this.comboId = comboId;
	}

	public Long getPackId() {
		return packId;
	}

	public void setPackId(Long packId) {
		this.packId = packId;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
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

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public String getComboName() {
		return comboName;
	}
	public void setComboName(String comboName) {
		this.comboName = comboName;
	}
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
