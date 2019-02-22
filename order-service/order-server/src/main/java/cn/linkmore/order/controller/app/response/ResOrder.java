package cn.linkmore.order.controller.app.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.linkmore.bean.common.Constants.OrderStatus;
import cn.linkmore.order.response.ResUserOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("订单信息")
public class ResOrder {
	@ApiModelProperty(value = "订单ID")
	private Long id;
	@ApiModelProperty(value = "车位ID")
	private Long stallId;
	@ApiModelProperty(value = "车区ID")
	private Long prefectureId;
	@ApiModelProperty(value = "企业ID")
	private Long entId;	
	@ApiModelProperty(value = "车位名称")
	private String stallName;
	@ApiModelProperty(value = "车位导航图")
	private String guideImage;
	@ApiModelProperty(value = "车位导航说明")
	private String guideRemark;
	@ApiModelProperty(value = "车区经度")
	private BigDecimal preLongitude;
	@ApiModelProperty(value = "车区纬度")
	private BigDecimal preLatitude; 
	
	@ApiModelProperty(value = "车区经度[高德、腾讯]")
	private double tencentLongitude;
	
	@ApiModelProperty(value = "车区纬度[高德、腾讯]")
	private double tencentLatitude; 
	
	@ApiModelProperty(value = "车区名称")
	private String prefectureName;
	@ApiModelProperty(value = "车区地址")
	private String prefectureAddress;
	@ApiModelProperty(value = "车牌号")
	private String plateNumber;
	@ApiModelProperty(value = "预约时间")
	private Date startTime;
	@ApiModelProperty(value = "停车时长[分钟]")
	private Integer parkingTime;
	@ApiModelProperty(value = "结束时间")
	private Date endTime;
	@ApiModelProperty(value = "停车费用")
	private BigDecimal totalAmount;
	@ApiModelProperty(value = "支付类型")
	private Short payType;
	@ApiModelProperty(value = "实际费用")
	private BigDecimal actualAmount; 
	@ApiModelProperty(value = "订单状态[1预约中,3已结账,6已挂起]")
	private Short status;  
	@ApiModelProperty(value = "蓝牙地址串用")
	private String bluetooth;
	@ApiModelProperty(value = "金额[字符串]")
	private String amount;
	@ApiModelProperty(value = "订单状态[1取消预约,2结账离场]")
	private Short cancelFlag = 1;
	@ApiModelProperty(value = "免费时长")
	private int freeMins;
	@ApiModelProperty(value = "剩余时长")
	private int remainMins;
	@ApiModelProperty(value = "订单类型[1普通订单,2扫码降锁订单]")
	private Short orderSource = 1;
	@ApiModelProperty(value = "降锁状态[0已预约,1已降锁,2已挂起去结账]")
	private Short downFlag = 0;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStallId() {
		return stallId;
	}
	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}
	public Long getPrefectureId() {
		return prefectureId;
	}
	public void setPrefectureId(Long prefectureId) {
		this.prefectureId = prefectureId;
	}
	public Long getEntId() {
		return entId;
	}
	public void setEntId(Long entId) {
		this.entId = entId;
	}
	public String getStallName() {
		return stallName;
	}
	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
	public String getPrefectureName() {
		return prefectureName;
	}
	public void setPrefectureName(String prefectureName) {
		this.prefectureName = prefectureName;
	}
	public String getPrefectureAddress() {
		return prefectureAddress;
	}
	public void setPrefectureAddress(String prefectureAddress) {
		this.prefectureAddress = prefectureAddress;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	 
	public Integer getParkingTime() {
		return parkingTime;
	}
	public void setParkingTime(Integer parkingTime) {
		this.parkingTime = parkingTime;
	}
	public BigDecimal getTotalAmount() {
		if(this.totalAmount==null) {
			this.totalAmount = new BigDecimal(0d);
		}
		return this.totalAmount.setScale(2,   BigDecimal.ROUND_HALF_UP);
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAmount() {
		if(this.totalAmount==null) {
			this.totalAmount = new BigDecimal(0d);
		}
		return this.totalAmount.setScale(2,   BigDecimal.ROUND_HALF_UP).toString();
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}   
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Short getPayType() {
		return payType;
	}
	public void setPayType(Short payType) {
		this.payType = payType;
	}
	public BigDecimal getActualAmount() {
		if(this.actualAmount==null) {
			this.actualAmount = new BigDecimal(0d);
		}
		return this.actualAmount.setScale(2,   BigDecimal.ROUND_HALF_UP);
	}
	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	} 
	public String getGuideImage() {
		return guideImage;
	}
	public void setGuideImage(String guideImage) {
		this.guideImage = guideImage;
	}
	public String getGuideRemark() {
		return guideRemark;
	}
	public void setGuideRemark(String guideRemark) {
		this.guideRemark = guideRemark;
	}
	public BigDecimal getPreLongitude() {
		return preLongitude;
	}
	public void setPreLongitude(BigDecimal preLongitude) {
		this.preLongitude = preLongitude;
	}
	public BigDecimal getPreLatitude() {
		return preLatitude;
	}
	public void setPreLatitude(BigDecimal preLatitude) {
		this.preLatitude = preLatitude;
	}
	private final static double PI = 3.14159265358979324;
	
	public double getTencentLongitude() { 
		double tx_lon; 
		double x = this.getPreLongitude().doubleValue() - 0.0065, y = this.getPreLatitude().doubleValue() - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
		tx_lon = z * Math.cos(theta); 
		return tx_lon; 
	}
	
	public double  getTencentLatitude() {
		double tx_lat;  
		double x = this.getPreLongitude().doubleValue() - 0.0065, y = this.getPreLatitude().doubleValue() - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI); 
		tx_lat = z * Math.sin(theta); 
		return tx_lat;
	}
	 
	public void setTencentLongitude(double tencentLongitude) {
		this.tencentLongitude = tencentLongitude;
	}
	 
	public void setTencentLatitude(double tencentLatitude) {
		this.tencentLatitude = tencentLatitude;
	}
	@JsonIgnore
	public void copy(ResUserOrder ruo) {
		this.setStartTime(ruo.getCreateTime());
		this.setId(ruo.getId());
		Date start = ruo.getCreateTime();
		Date end = new Date(); 
		if(ruo.getStatus()==OrderStatus.SUSPENDED.value) {
			end = ruo.getStatusTime(); 
		}
		this.setEndTime(end);
		this.setParkingTime(new Long((end.getTime()-start.getTime())/(60*1000L)).intValue());
		this.setPlateNumber(ruo.getPlateNo());
		this.setEntId(ruo.getEntId());
		this.setPrefectureId(ruo.getPreId());
		this.setStallId(ruo.getStallId());
		this.setStatus(ruo.getStatus().shortValue());
		this.setStallName(ruo.getStallName());
		this.setPrefectureName(ruo.getPreName());  
		this.setTotalAmount(ruo.getTotalAmount());
		this.setOrderSource(ruo.getOrderSource());
	}
	public String getBluetooth() {
		return bluetooth;
	}
	public void setBluetooth(String bluetooth) {
		this.bluetooth = bluetooth;
	}
	public Short getCancelFlag() {
		return cancelFlag;
	}
	public void setCancelFlag(Short cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	public int getFreeMins() {
		return freeMins;
	}
	public void setFreeMins(int freeMins) {
		this.freeMins = freeMins;
	}
	public int getRemainMins() {
		return remainMins;
	}
	public void setRemainMins(int remainMins) {
		this.remainMins = remainMins;
	}
	public Short getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(Short orderSource) {
		this.orderSource = orderSource;
	}
	public Short getDownFlag() {
		return downFlag;
	}
	public void setDownFlag(Short downFlag) {
		this.downFlag = downFlag;
	}
	
}
