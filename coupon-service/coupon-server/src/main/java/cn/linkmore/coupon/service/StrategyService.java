package cn.linkmore.coupon.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqStrategy;

public interface StrategyService {
	/**
	 * 检查属性可用性
	 * @param reqCheck
	 * @return
	 */
	Integer check(ReqCheck reqCheck);
	/**
	 * 删除信息
	 * @param id
	 * @return
	 */
	int delete(Long id);

	/**
	 * 更新信息
	 * @param record
	 * @return
	 */
	int update(ReqStrategy record);

	/**
	 * 保存信息
	 * @param record
	 */
	int save(ReqStrategy record);

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable); 

}
