package cn.linkmore.order.controller.app.response;

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
	private String parkingTime;
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
	public String getParkingTime() {
		return parkingTime;
	}
	public void setParkingTime(String parkingTime) {
		this.parkingTime = parkingTime;
	} 
	public void copy(ResUserOrder ruo) {
		SimpleDateFormat sdf = new SimpleDateFormat("M月d日 HH:mm");
		this.setOrderTime(sdf.format(ruo.getCreateTime()));
		this.setId(ruo.getId());
		Date start = ruo.getCreateTime();
		Date end = ruo.getEndTime();
		long day = 0;
		long hour = 0;
		long min = 0;
		long time = (end.getTime()-start.getTime())/(60*1000L);
		day = time / (24*60);
		hour =( time % (24*60) ) / 60;
		min = time % 60;
		StringBuffer parkingTime = new StringBuffer();
		if(day!=0) {
			parkingTime.append(day);
			parkingTime.append("天");
			parkingTime.append(hour);
			parkingTime.append("时"); 
		}else if(hour!=0) {
			parkingTime.append(hour);
			parkingTime.append("时");
		} 
		parkingTime.append(min);
		parkingTime.append("分");
		this.setParkingTime(parkingTime.toString()); 
		this.setStatus(ruo.getStatus().shortValue());
		this.setStallName(ruo.getStallName());
		this.setPrefectureName(ruo.getPreName());   
	}
}
