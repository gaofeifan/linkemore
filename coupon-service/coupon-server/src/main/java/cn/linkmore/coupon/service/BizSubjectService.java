package cn.linkmore.coupon.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqBizSubject;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.response.ResBizSubjectBean;

public interface BizSubjectService {
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
	int update(ReqBizSubject record);

	/**
	 * 保存信息
	 * @param record
	 */
	int save(ReqBizSubject record);

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
	ResBizSubjectBean findById(Long id);
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
	/**
	 * 查询专题下拉列表
	 * @param type
	 * @return
	 */
	List<ResBizSubjectBean> selectSubjectListByType(Long type);

}
