package cn.linkmore.coupon.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqSubject;
import cn.linkmore.coupon.response.ResSubject;
import cn.linkmore.coupon.response.ResTemplate;

public interface SubjectService {
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
	int update(ReqSubject record);

	/**
	 * 保存信息
	 * @param record
	 */
	int save(ReqSubject record);

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	/**
	 * 查看详情	
	 * @param id
	 * @return
	 */
	ResSubject findById(Long id);
	/**
	 * 上线
	 * @param id
	 */
	int start(Long id);
	/**
	 * 下线
	 * @param id
	 */
	int stop(Long id);
	
}
