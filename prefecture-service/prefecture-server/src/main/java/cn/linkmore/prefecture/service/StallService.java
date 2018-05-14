package cn.linkmore.prefecture.service;

import cn.linkmore.prefecture.entity.Stall;

/**
 * Service接口 - 车位信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
public interface StallService {
	/**
	 * 预约订单时，根据车位锁序列号查询车位
	 * @param lockSn
	 */
	void order(String lockSn);
	/**
	 * 取消订单释放车位
	 * @param stallId
	 */
	void cancel(Long stallId);
	/**
	 * 降锁操作
	 * @param stallId
	 */
	void lockdown(Long stallId);
	/**
	 * 结账立场释放车位
	 * @param stallId
	 */
	void pay(Long stallId);
	
}
