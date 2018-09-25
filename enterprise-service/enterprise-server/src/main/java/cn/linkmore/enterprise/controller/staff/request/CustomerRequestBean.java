package cn.linkmore.enterprise.controller.staff.request;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户信息请求封装
 * 
 * @author liwl
 * @version 1.0
 *
 */
@ApiModel("客户信息")
public class CustomerRequestBean implements Serializable {
	/**
	 * serial id
	 */
	private static final long serialVersionUID = -8723606481027384283L;
	@ApiModelProperty(value = "客户性别", required = false)
	private Long sexId;
	@ApiModelProperty(value = "客户年龄段")
	private Long ageId;
	@ApiModelProperty(value = "小孩年龄")
	private String childAgeIds;
	@ApiModelProperty(value = "汽车色")
	private Long colorId;
	@ApiModelProperty(value = "汽车排量")
	private Long displacementId;
	@ApiModelProperty(value = "停车原因")
	private Long causeId;
	@ApiModelProperty(value = "车辆类型")
	private Integer model;
	@ApiModelProperty(value = "车辆品牌")
	private Integer brandId;
	@ApiModelProperty(value = "订单Id")
	private Long orderId;

	private Long adminId;

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getSexId() {
		return sexId;
	}

	public void setSexId(Long sexId) {
		this.sexId = sexId;
	}

	public Long getAgeId() {
		return ageId;
	}

	public void setAgeId(Long ageId) {
		this.ageId = ageId;
	}

	public String getChildAgeIds() {
		return childAgeIds;
	}

	public void setChildAgeIds(String childAgeIds) {
		this.childAgeIds = childAgeIds;
	}

	public Long getColorId() {
		return colorId;
	}

	public void setColorId(Long colorId) {
		this.colorId = colorId;
	}

	public Long getDisplacementId() {
		return displacementId;
	}

	public void setDisplacementId(Long displacementId) {
		this.displacementId = displacementId;
	}

	public Long getCauseId() {
		return causeId;
	}

	public void setCauseId(Long causeId) {
		this.causeId = causeId;
	}

	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
}
