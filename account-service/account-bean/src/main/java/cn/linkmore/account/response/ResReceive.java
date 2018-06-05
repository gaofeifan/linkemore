package cn.linkmore.account.response;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 分享领券
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
public class ResReceive {

    private Long id;
    
    private Long shareId;
    //用户名
    private String username;
    //活动标题
    private String title;
    //类型
    private Integer type;
    //使用数量
    private Integer usedCount;
    //总数量
    private Integer totalNum;
    //创建时间
    private Date createTime;
    //使用金额
    private BigDecimal usedAmount;
    
    //套餐名
  	private String comboName;
  	//面额
  	private Double faceAmount;
  	//过期时间
  	private Date validTime;
  	//状态：0未使用，1已使用，2已过期
  	private Integer status;

	public String getComboName() {
		return comboName;
	}

	public void setComboName(String comboName) {
		this.comboName = comboName;
	}

	public Double getFaceAmount() {
		return faceAmount;
	}

	public void setFaceAmount(Double faceAmount) {
		this.faceAmount = faceAmount;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getShareId() {
		return shareId;
	}

	public void setShareId(Long shareId) {
		this.shareId = shareId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUsedCount() {
		return usedCount;
	}

	public void setUsedCount(Integer usedCount) {
		this.usedCount = usedCount;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getUsedAmount() {
		return usedAmount;
	}

	public void setUsedAmount(BigDecimal usedAmount) {
		this.usedAmount = usedAmount;
	}
    
    
}