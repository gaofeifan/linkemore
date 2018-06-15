package cn.linkmore.ops.coupon.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqPreSubject;
import cn.linkmore.coupon.response.ResBizSubjectBean;

public interface PreSubjectService {
	/**
	 * 检查属性可用性
	 * @param reqCheck
	 * @return
	 */
	Boolean check(ReqCheck reqCheck);
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
	
	public List<ResBizSubjectBean> subjectList(Long type);

}
