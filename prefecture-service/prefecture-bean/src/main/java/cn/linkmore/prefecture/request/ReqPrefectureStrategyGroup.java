package cn.linkmore.prefecture.request;
/**
 * 车区策略-分时分组费用配置
 * @author kobe
 *
 */
public class ReqPrefectureStrategyGroup {
	/**
	 * 主键id
	 */
    private Long id;
    /**
     * 车区策略id
     */
    private Long prefectureStrategyId;
    /**
     * 分组策略id
     */
    private Long strategyGroupId;
    /**
     * 分期策略id
     */
    private Long strategyDateId;
    /**
     * 费用策略code
     */
    private String parkCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrefectureStrategyId() {
        return prefectureStrategyId;
    }

    public void setPrefectureStrategyId(Long prefectureStrategyId) {
        this.prefectureStrategyId = prefectureStrategyId;
    }

    public Long getStrategyGroupId() {
        return strategyGroupId;
    }

    public void setStrategyGroupId(Long strategyGroupId) {
        this.strategyGroupId = strategyGroupId;
    }

    public Long getStrategyDateId() {
        return strategyDateId;
    }

    public void setStrategyDateId(Long strategyDateId) {
        this.strategyDateId = strategyDateId;
    }

	public String getParkCode() {
		return parkCode;
	}

	public void setParkCode(String parkCode) {
		this.parkCode = parkCode;
	}

}