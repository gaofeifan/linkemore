package cn.linkmore.prefecture.response;
/**
 * 车区策略-车位锁时段配置
 * @author llh
 *
 */
public class ResPrefectureLockTime {
	/**
	 * 主键id
	 */
    private Long id;
    /**
     * 车区策略id
     */
    private Long prefectureStrategyId;
    /**
     * 锁状态
     */
    private Byte lockStatus;
    /**
     * 锁时段配置id
     */
    private Long strategyTimeId;

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

    public Byte getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Byte lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Long getStrategyTimeId() {
        return strategyTimeId;
    }

    public void setStrategyTimeId(Long strategyTimeId) {
        this.strategyTimeId = strategyTimeId;
    }
}