package cn.linkmore.coupon.controller.app.response;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("停车券")
public class ResCoupon {
	
	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "面额")
	private BigDecimal faceAmount;
	
	@ApiModelProperty(value = "分类")
	private Integer type;
	
	@ApiModelProperty(value = "名称")
	private String name;  
	
	@ApiModelProperty(value = "发放时间")
	private Date createTime;
	
	@ApiModelProperty(value = "专区")
	private String preName;
	
	@ApiModelProperty(value = "专区列表")
	private String preNameList;
	
	@ApiModelProperty(value = "专区是否受限")
	private Short preLimit; 
	
	@ApiModelProperty(value = "过期时间")
	private Date  validTime; 
	
	@ApiModelProperty(value = "条件ID")
	private Long conditionId;
	
	@ApiModelProperty(value = "折扣")
	private Integer discount;
	
	@ApiModelProperty(value = "满减条件")
	private BigDecimal conditionAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getFaceAmount() {
		return faceAmount;
	}

	public void setFaceAmount(BigDecimal faceAmount) {
		this.faceAmount = faceAmount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public String getPreNameList() {
		return preNameList;
	}

	public void setPreNameList(String preNameList) {
		this.preNameList = preNameList;
	}

	public Short getPreLimit() {
		return preLimit;
	}

	public void setPreLimit(Short preLimit) {
		this.preLimit = preLimit;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public Long getConditionId() {
		return conditionId;
	}

	public void setConditionId(Long conditionId) {
		this.conditionId = conditionId;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public BigDecimal getConditionAmount() {
		return conditionAmount;
	}

	public void setConditionAmount(BigDecimal conditionAmount) {
		this.conditionAmount = conditionAmount;
	}  
	
	public void copy(cn.linkmore.coupon.response.ResCoupon res) {
		this.setConditionAmount(res.getConditionAmount());
		this.setConditionId(res.getConditionId());
		this.setCreateTime(res.getCreateTime());
		this.setValidTime(res.getValidTime());
		this.setDiscount(res.getDiscount());
		this.setFaceAmount(res.getFaceAmount());
		this.setName(res.getName());
		this.setPreLimit(res.getPreLimit());
		this.setPreName(res.getPreName());
		this.setPreNameList(res.getPreNameList());
		this.setId(res.getId());
		this.setType(res.getType());
	}
	
}
