package cn.linkmore.prefecture.entity;

import java.util.Date;

public class StrategyBase {
	
	public static final int TYPE_TOP_NONE=1;//无封顶计费
	public static final int TYPE_TOP_DAILY=2;//按天封顶计费
	public static final int TYPE_TOP_SECTION=3;//按照时段封顶计费
	public static final int TYPE_TOP_DAILY_24H=4;//按照从计费时刻起以24小时（一天）为计费周期，
	public static final int TYPE_TOP_DAILY_24H_FRIST_XHORS=5;
	public static final int TYPE_PERIOD = 6;
	public static final int TYPE_HUBIN = 7;
	public static final int TYPE_XICHENG = 8;
	/**
	 * 主键
	 */
    private Long id;
    /**
     * 计费策略类型：无封顶计费-1；每日累计封顶-2；分时段封顶-3
     */
    private Integer type;
    /**
     * 计费策略名称
     */
    private String name;
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 免费停车时长：分钟
     */
    private Integer freeMins;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 首小时内
     */
    private Double firstHour;
    /**
     * 基础价格
     */
    private Double basePrice;
    /**
     * 夜间价格
     */
    private Double nightPrice;
    /**
     * 时间基数 15
     */
    private Integer timelyLong;
    /**
     * 夜时间基数 以分钟为单位
     */
    private Integer nightTimelyLong;
    /**
     * 计时单位 分钟
     */
    private String timelyUnit;
    /**
     * 每日封顶时长
     */
    private Integer topDaily;
    /**
     * 白天封顶时长冗余字段不做设置
     */
    private Integer topDay;
    /**
     * 夜间封顶时长
     */
    private Integer topNight;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 状态：0禁用：1可用；
     */
    private Integer status;
    /**
     * 是否预支付
     */
    private Integer isPrepaidPay;
    
    /** 
     * 首段时间与单位时间的比值 
     */
    private Integer firstHourLong;
    /**
     * 
     */ 
	private String flexDetail;
    
    /**
	 * 返回夜间计费单价 
	 * @return
	 */
	public String getNightFeePrice(){
		if(null != nightTimelyLong  && null != nightPrice && null != timelyUnit ){
			String nightPriceDisplay = this.nightPrice + "元/" + nightTimelyLong + "分钟";
			if(nightTimelyLong >= 60 && 0 == nightTimelyLong%15){
				nightPriceDisplay = this.nightPrice + "元/" + ((double) nightTimelyLong / 60) + "小时";
			}
			return nightPriceDisplay;
		}
		return this.nightPrice+"元/"+this.nightTimelyLong+this.timelyUnit;
	}
	
	/**
	 * 返回首小时计费单价 
	 * @return
	 */
	public String getFirstFeePrice(){
		if(null != firstHour && null !=timelyLong && null != timelyUnit ){
			String firstPriceDisplay = this.firstHour + "元/" + timelyLong + timelyUnit;
			if(timelyLong >= 60 && 0 == timelyLong%15){
				firstPriceDisplay = this.firstHour + "元/" + ((double) timelyLong / 60) + "小时";
			}
			return firstPriceDisplay;
		}
		return this.firstHour+"元/"+this.timelyLong+this.timelyUnit;
	}
	
	/**
	 * 基本计费单价
	 * @return
	 */
	public String getBaseFeePrice(){
		if(null != basePrice && null !=timelyLong && null != timelyUnit ){
			String basePriceDisplay = this.basePrice + "元/" + timelyLong + timelyUnit;
			if(timelyLong >= 60 && 0 == timelyLong%15){
				basePriceDisplay = this.basePrice + "元/" + ((double) timelyLong / 60) + "小时";
			}
			return basePriceDisplay;
		}
		return this.basePrice+"元/"+this.timelyLong+this.timelyUnit;
	}
	

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        this.name = name == null ? null : name.trim();
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    public Integer getFreeMins() {
        return freeMins;
    }

    public void setFreeMins(Integer freeMins) {
        this.freeMins = freeMins;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Double getFirstHour() {
        return firstHour;
    }

    public void setFirstHour(Double firstHour) {
        this.firstHour = firstHour;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getNightPrice() {
        return nightPrice;
    }

    public void setNightPrice(Double nightPrice) {
        this.nightPrice = nightPrice;
    }

    public Integer getTimelyLong() {
        return timelyLong;
    }

    public void setTimelyLong(Integer timelyLong) {
        this.timelyLong = timelyLong;
    }

    public Integer getNightTimelyLong() {
        return nightTimelyLong;
    }

    public void setNightTimelyLong(Integer nightTimelyLong) {
        this.nightTimelyLong = nightTimelyLong;
    }

    public String getTimelyUnit() {
        return timelyUnit;
    }

    public void setTimelyUnit(String timelyUnit) {
        this.timelyUnit = timelyUnit == null ? null : timelyUnit.trim();
    }

    public Integer getTopDaily() {
        return topDaily;
    }

    public void setTopDaily(Integer topDaily) {
        this.topDaily = topDaily;
    }

    public Integer getTopDay() {
        return topDay;
    }

    public void setTopDay(Integer topDay) {
        this.topDay = topDay;
    }

    public Integer getTopNight() {
        return topNight;
    }

    public void setTopNight(Integer topNight) {
        this.topNight = topNight;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsPrepaidPay() {
        return isPrepaidPay;
    }

    public void setIsPrepaidPay(Integer isPrepaidPay) {
        this.isPrepaidPay = isPrepaidPay;
    }

	public Integer getFirstHourLong() {
		return firstHourLong;
	}

	public void setFirstHourLong(Integer firstHourLong) {
		this.firstHourLong = firstHourLong;
	}

	public String getFlexDetail() {
		return flexDetail;
	}

	public void setFlexDetail(String flexDetail) {
		this.flexDetail = flexDetail;
	}
    
}