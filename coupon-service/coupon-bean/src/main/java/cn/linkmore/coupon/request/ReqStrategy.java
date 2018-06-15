package cn.linkmore.coupon.request;

import java.util.Date;

public class ReqStrategy {
    private Long id;

    private String name;

    private Long enterpriseId;

    private String enterpriseName;

    private Long preId;

    private String preName;

    private String strategyJson;

    private String strategyRemark;

    private Date createTime;

    private Long operatorId;

    private String operatorName;

    private Short status;

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
        this.name = name == null ? null : name.trim();
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
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

    public String getStrategyJson() {
        return strategyJson;
    }

    public void setStrategyJson(String strategyJson) {
        this.strategyJson = strategyJson == null ? null : strategyJson.trim();
    }

    public String getStrategyRemark() {
        return strategyRemark;
    }

    public void setStrategyRemark(String strategyRemark) {
        this.strategyRemark = strategyRemark == null ? null : strategyRemark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}