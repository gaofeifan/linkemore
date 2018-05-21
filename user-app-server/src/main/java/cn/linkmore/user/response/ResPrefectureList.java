package cn.linkmore.user.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 响应-车区列表
 * @author jiaohanbin
 * @version 2.0
 *
 */
@ApiModel("车区列表")
public class ResPrefectureList {
	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "车区名称")
	private String name;

	@ApiModelProperty(value = "空闲车位数量")
	private Integer leisureStall;

	@ApiModelProperty(value = "车区地址")
	private String address;
	
	@ApiModelProperty(value = "首小时价格")
	private String firstHour;

	@ApiModelProperty(value = "时间基数 15分钟")
	private String timelyLong;
	
	@ApiModelProperty(value = "计时单位 分钟")
	private String timelyUnit;
	
	@ApiModelProperty(value = "免费时长")
	private Long freeMins;

	@ApiModelProperty(value = "专区类型(0普通，1专区内部定制专区)")
	private Integer type;
	
	@ApiModelProperty(value = "车位总数")
	private Integer stallCount;
	
	@ApiModelProperty(value = "是否受限")
	private short limitStatus;

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

	public Integer getLeisureStall() {
		return leisureStall;
	}

	public void setLeisureStall(Integer leisureStall) {
		this.leisureStall = leisureStall;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFirstHour() {
		return firstHour;
	}

	public void setFirstHour(String firstHour) {
		this.firstHour = firstHour;
	}

	public String getTimelyLong() {
		return timelyLong;
	}

	public void setTimelyLong(String timelyLong) {
		this.timelyLong = timelyLong;
	}

	public String getTimelyUnit() {
		return timelyUnit;
	}

	public void setTimelyUnit(String timelyUnit) {
		this.timelyUnit = timelyUnit;
	}

	public Long getFreeMins() {
		return freeMins;
	}

	public void setFreeMins(Long freeMins) {
		this.freeMins = freeMins;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStallCount() {
		return stallCount;
	}

	public void setStallCount(Integer stallCount) {
		this.stallCount = stallCount;
	}

	public short getLimitStatus() {
		return limitStatus;
	}

	public void setLimitStatus(short limitStatus) {
		this.limitStatus = limitStatus;
	}
}
