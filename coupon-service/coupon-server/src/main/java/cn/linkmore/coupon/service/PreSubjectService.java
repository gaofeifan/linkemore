package cn.linkmore.coupon.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqPreSubject;

public interface PreSubjectService {
	/**
	 * 检查属性可用性
	 * @param property
	 * @param value
	 * @param id
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
	int update(ReqPreSubject record);

	/**
	 * 保存信息
	 * @param record
	 */
	int save(ReqPreSubject record);

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

}
