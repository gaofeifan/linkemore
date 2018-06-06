package cn.linkmore.user.response;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.linkmore.order.response.ResUserOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("已结订单")
public class ResCheckedOrder {
	@ApiModelProperty(value = "主键")
	private Long id;
	@ApiModelProperty(value = "车区名")
	private String prefectureName;
	@ApiModelProperty(value = "车位名")
	private String stallName;
	@ApiModelProperty(value = "状态[3已完成]")
	private Short status;
	@ApiModelProperty(value = "创建时间")
	private String orderTime; 
	@ApiModelProperty(value = "停车时长")
	private Integer parkingTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	} 
	public String getPrefectureName() {
		return prefectureName;
	}
	public void setPrefectureName(String prefectureName) {
		this.prefectureName = prefectureName;
	}
	public String getStallName() {
		return stallName;
	}
	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public Integer getParkingTime() {
		return parkingTime;
	}
	public void setParkingTime(Integer parkingTime) {
		this.parkingTime = parkingTime;
	} 
	public void copy(ResUserOrder ruo) {
		SimpleDateFormat sdf = new SimpleDateFormat("M月d日  HH:mm");
		this.setOrderTime(sdf.format(ruo.getCreateTime()));
		this.setId(ruo.getId());
		Date start = ruo.getCreateTime();
		Date end = ruo.getEndTime();
		this.setParkingTime(new Long((end.getTime()-start.getTime())/(60*1000L)).intValue()); 
		this.setStatus(ruo.getStatus().shortValue());
		this.setStallName(ruo.getStallName());
		this.setPrefectureName(ruo.getPreName());   
	}
}
