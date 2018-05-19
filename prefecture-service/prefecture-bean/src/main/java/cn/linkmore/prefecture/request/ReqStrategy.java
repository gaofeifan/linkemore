package cn.linkmore.prefecture.request;

/**
 * 请求城市id
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ReqStrategy {

	/**
	 * 策略id
	 */
    private Long strategyId;

    /**
     * 开始时间
     */
    private Long beginTime;
    /**
     * 结束时间
     */
    private Long endTime;
	public Long getStrategyId() {
		return strategyId;
	}
	public void setStrategyId(Long strategyId) {
		this.strategyId = strategyId;
	}
	public Long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
    
}
