package cn.linkmore.prefecture.response;

import java.util.List;

public class ResStrategyGroupArea {
	
	/**
	 * 分区名称
	 */
    private String areaName;
    /**
     * 车位
     */
    private List<ResStrategyGroupDetail> strategyGroupDetail;
    
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public List<ResStrategyGroupDetail> getStrategyGroupDetail() {
		return strategyGroupDetail;
	}
	public void setStrategyGroupDetail(List<ResStrategyGroupDetail> strategyGroupDetail) {
		this.strategyGroupDetail = strategyGroupDetail;
	}
    
    
    
}