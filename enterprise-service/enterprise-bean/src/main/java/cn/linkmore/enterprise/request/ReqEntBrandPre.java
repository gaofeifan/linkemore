package cn.linkmore.enterprise.request;

import java.util.Date;
/**
 * 企业品牌车区
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ReqEntBrandPre {
	//主键
    private Long id;
    //品牌车区名称
    private String name;
    //联系方式
    private String contract;
    //企业id
    private Long entId;
    //企业名称
    private String entName;
    //logo 
    private String logoUrl;
    //车区id
    private Long preId;
    //车区名称
    private String preName;
    //运营周期
    private Short period;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //状态0未启用，1启用，2禁用
    private Short status;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //计费策略id
    private Long strategyId;
    //计费策略名称
    private String strategyName;
    //计费价格
    private String chargePrice;
    //计费时间
    private Integer chargeTime;
    //计费策略描述
    private String strategyDescription;
    //0不受限，1受限[仅品牌用户可约]
    private Short limitStatus = 0;
    
    public Short getLimitStatus() {
		return limitStatus;
	}

	public void setLimitStatus(Short limitStatus) {
		this.limitStatus = limitStatus;
	}

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
	
	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Long getEntId() {
        return entId;
    }

    public void setEntId(Long entId) {
        this.entId = entId;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName == null ? null : entName.trim();
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl == null ? null : logoUrl.trim();
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public String getPreName() {
        return preName;
    }

    public void setPreName(String preName) {
        this.preName = preName == null ? null : preName.trim();
    }

    public Short getPeriod() {
        return period;
    }

    public void setPeriod(Short period) {
        this.period = period;
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName == null ? null : strategyName.trim();
    }

	public String getChargePrice() {
		return chargePrice;
	}

	public void setChargePrice(String chargePrice) {
		this.chargePrice = chargePrice;
	}

	public Integer getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(Integer chargeTime) {
		this.chargeTime = chargeTime;
	}

	public String getStrategyDescription() {
		return strategyDescription;
	}

	public void setStrategyDescription(String strategyDescription) {
		this.strategyDescription = strategyDescription;
	}
}