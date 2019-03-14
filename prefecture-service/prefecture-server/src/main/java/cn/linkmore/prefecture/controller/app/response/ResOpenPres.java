package cn.linkmore.prefecture.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("第三方车区")
public class ResOpenPres {

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "车区名称")
	private String name;

	@ApiModelProperty(value = "空闲车位数量")
	private Integer leisureStall;

	@ApiModelProperty(value = "车区地址")
	private String address;

	@ApiModelProperty(value = "计费时间")
	private String chargeTime;

	@ApiModelProperty(value = "计费价格")
	private String chargePrice;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the leisureStall
	 */
	public Integer getLeisureStall() {
		return leisureStall;
	}

	/**
	 * @param leisureStall the leisureStall to set
	 */
	public void setLeisureStall(Integer leisureStall) {
		this.leisureStall = leisureStall;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the chargeTime
	 */
	public String getChargeTime() {
		return chargeTime;
	}

	/**
	 * @param chargeTime the chargeTime to set
	 */
	public void setChargeTime(String chargeTime) {
		this.chargeTime = chargeTime;
	}

	/**
	 * @return the chargePrice
	 */
	public String getChargePrice() {
		return chargePrice;
	}

	/**
	 * @param chargePrice the chargePrice to set
	 */
	public void setChargePrice(String chargePrice) {
		this.chargePrice = chargePrice;
	}

	
}
