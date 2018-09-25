package cn.linkmore.prefecture.response;

public class ResStrategyDateDetail {
    private Long id;

    private String beginDate;

    private String endDate;

    private Long strategyDateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public Long getStrategyDateId() {
        return strategyDateId;
    }

    public void setStrategyDateId(Long strategyDateId) {
        this.strategyDateId = strategyDateId;
    }
}