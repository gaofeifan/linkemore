package cn.linkmore.enterprise.service;

import cn.linkmore.enterprise.response.ResFixedPlate;

public interface FixedPlateService {
	/**
	 * 根据车位id获取车牌数据信息
	 * @param stallId
	 * @return
	 */
	ResFixedPlate findPlateNosByStallId(Long stallId);
}
