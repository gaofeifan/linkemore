package cn.linkmore.prefecture.request;

public class ReqStrategyTimeDetail {
    private Long id;

    private String beginTime;

    private String endTime;

    private Long strategyTimeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime == null ? null : beginTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Long getStrategyTimeId() {
        return strategyTimeId;
    }

    public void setStrategyTimeId(Long strategyTimeId) {
        this.strategyTimeId = strategyTimeId;
    }
}