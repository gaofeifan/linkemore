package cn.linkmore.coupon.request;

import java.math.BigDecimal;
import java.util.Date;

public class ReqPreSubject {
    private Long id;

    private Long preId;

    private Long subjectId;

    private Integer oneselfType;

    private Integer oneselfMin;

    private Integer oneselfMax;

    private Integer oneselfFixe;

    private Integer oneselfValidity;

    private Integer otherType;

    private Integer otherMin;

    private Integer otherMax;

    private Integer otherFixe;

    private Integer otherValidity;

    private String content;

    private Long operatorId;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private String payTitle;

    private Integer payValidity;

    private BigDecimal denomainOneMin;

    private BigDecimal denomainOneMax;

    private Integer denomainOneProp;

    private BigDecimal denomainTwoMin;

    private BigDecimal denomainTwoMax;

    private Integer denomainTwoProp;

    private BigDecimal denomainThreeMin;

    private BigDecimal denomainThreeMax;

    private Integer denomainThreeProp;

    private String payContent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getOneselfType() {
        return oneselfType;
    }

    public void setOneselfType(Integer oneselfType) {
        this.oneselfType = oneselfType;
    }

    public Integer getOneselfMin() {
        return oneselfMin;
    }

    public void setOneselfMin(Integer oneselfMin) {
        this.oneselfMin = oneselfMin;
    }

    public Integer getOneselfMax() {
        return oneselfMax;
    }

    public void setOneselfMax(Integer oneselfMax) {
        this.oneselfMax = oneselfMax;
    }

    public Integer getOneselfFixe() {
        return oneselfFixe;
    }

    public void setOneselfFixe(Integer oneselfFixe) {
        this.oneselfFixe = oneselfFixe;
    }

    public Integer getOneselfValidity() {
        return oneselfValidity;
    }

    public void setOneselfValidity(Integer oneselfValidity) {
        this.oneselfValidity = oneselfValidity;
    }

    public Integer getOtherType() {
        return otherType;
    }

    public void setOtherType(Integer otherType) {
        this.otherType = otherType;
    }

    public Integer getOtherMin() {
        return otherMin;
    }

    public void setOtherMin(Integer otherMin) {
        this.otherMin = otherMin;
    }

    public Integer getOtherMax() {
        return otherMax;
    }

    public void setOtherMax(Integer otherMax) {
        this.otherMax = otherMax;
    }

    public Integer getOtherFixe() {
        return otherFixe;
    }

    public void setOtherFixe(Integer otherFixe) {
        this.otherFixe = otherFixe;
    }

    public Integer getOtherValidity() {
        return otherValidity;
    }

    public void setOtherValidity(Integer otherValidity) {
        this.otherValidity = otherValidity;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
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

    public String getPayTitle() {
        return payTitle;
    }

    public void setPayTitle(String payTitle) {
        this.payTitle = payTitle == null ? null : payTitle.trim();
    }

    public Integer getPayValidity() {
        return payValidity;
    }

    public void setPayValidity(Integer payValidity) {
        this.payValidity = payValidity;
    }

    public BigDecimal getDenomainOneMin() {
        return denomainOneMin;
    }

    public void setDenomainOneMin(BigDecimal denomainOneMin) {
        this.denomainOneMin = denomainOneMin;
    }

    public BigDecimal getDenomainOneMax() {
        return denomainOneMax;
    }

    public void setDenomainOneMax(BigDecimal denomainOneMax) {
        this.denomainOneMax = denomainOneMax;
    }

    public Integer getDenomainOneProp() {
        return denomainOneProp;
    }

    public void setDenomainOneProp(Integer denomainOneProp) {
        this.denomainOneProp = denomainOneProp;
    }

    public BigDecimal getDenomainTwoMin() {
        return denomainTwoMin;
    }

    public void setDenomainTwoMin(BigDecimal denomainTwoMin) {
        this.denomainTwoMin = denomainTwoMin;
    }

    public BigDecimal getDenomainTwoMax() {
        return denomainTwoMax;
    }

    public void setDenomainTwoMax(BigDecimal denomainTwoMax) {
        this.denomainTwoMax = denomainTwoMax;
    }

    public Integer getDenomainTwoProp() {
        return denomainTwoProp;
    }

    public void setDenomainTwoProp(Integer denomainTwoProp) {
        this.denomainTwoProp = denomainTwoProp;
    }

    public BigDecimal getDenomainThreeMin() {
        return denomainThreeMin;
    }

    public void setDenomainThreeMin(BigDecimal denomainThreeMin) {
        this.denomainThreeMin = denomainThreeMin;
    }

    public BigDecimal getDenomainThreeMax() {
        return denomainThreeMax;
    }

    public void setDenomainThreeMax(BigDecimal denomainThreeMax) {
        this.denomainThreeMax = denomainThreeMax;
    }

    public Integer getDenomainThreeProp() {
        return denomainThreeProp;
    }

    public void setDenomainThreeProp(Integer denomainThreeProp) {
        this.denomainThreeProp = denomainThreeProp;
    }

    public String getPayContent() {
        return payContent;
    }

    public void setPayContent(String payContent) {
        this.payContent = payContent == null ? null : payContent.trim();
    }
}