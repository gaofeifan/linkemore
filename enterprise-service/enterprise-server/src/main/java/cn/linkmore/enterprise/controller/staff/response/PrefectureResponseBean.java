package cn.linkmore.enterprise.controller.staff.response;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 专区列表响应
 * 
 * @author changlei
 * @date 2018年09月12日
 */

@ApiModel("专区列表响应封装Bean")
public class PrefectureResponseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "专区id")
	private Long id;

	@ApiModelProperty(value = "专区名称")
	private String name;

	@ApiModelProperty(value = "车位总数")
	private int stallTotal;

	@ApiModelProperty(value = "预约车位数")
	private int occupyStall;

	@ApiModelProperty(value = "空闲车位数")
	private int freeStall;

	@ApiModelProperty(value = "异常车位数")
	private int exceptionStall;
	
	@ApiModelProperty(value = "故障车位数")
	private int faultStall;
	
	@ApiModelProperty(value = "车位列表")
	private List<StallStateResponseBean> list;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStallTotal() {
		return stallTotal;
	}

	public void setStallTotal(int stallTotal) {
		this.stallTotal = stallTotal;
	}

	public int getOccupyStall() {
		return occupyStall;
	}

	public void setOccupyStall(int occupyStall) {
		this.occupyStall = occupyStall;
	}

	public int getFreeStall() {
		return freeStall;
	}

	public void setFreeStall(int freeStall) {
		this.freeStall = freeStall;
	}

	public int getExceptionStall() {
		return exceptionStall;
	}

	public void setExceptionStall(int exceptionStall) {
		this.exceptionStall = exceptionStall;
	}

	public List<StallStateResponseBean> getList() {
		return list;
	}

	public void setList(List<StallStateResponseBean> list) {
		this.list = list;
	}

	public int getFaultStall() {
		return faultStall;
	}

	public void setFaultStall(int faultStall) {
		this.faultStall = faultStall;
	}
	
	
	
}
