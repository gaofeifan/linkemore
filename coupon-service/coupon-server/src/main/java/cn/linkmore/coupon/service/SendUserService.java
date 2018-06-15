package cn.linkmore.coupon.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

public interface SendUserService {

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * 回滚企业套餐金额
	 */
	void rollbackEnterpriseCoupon();
}
