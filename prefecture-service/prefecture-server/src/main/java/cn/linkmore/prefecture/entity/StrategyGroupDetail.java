package cn.linkmore.prefecture.entity;

public class StrategyGroupDetail {
    private Long id;

    private Long strategyGroupId;

    private Long stallId;

    private String stallName;

    private Long areaId;

    private String areaName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStrategyGroupId() {
        return strategyGroupId;
    }

    public void setStrategyGroupId(Long strategyGroupId) {
        this.strategyGroupId = strategyGroupId;
    }

    public Long getStallId() {
        return stallId;
    }

    public void setStallId(Long stallId) {
        this.stallId = stallId;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName == null ? null : stallName.trim();
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }
}