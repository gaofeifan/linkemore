package cn.linkmore.ops.coupon.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplateCondition;
import cn.linkmore.coupon.response.ResTemplateCondition;

public interface TemplateConditionService {
	/**
	 * 检查属性可用性
	 * @param property
	 * @param value
	 * @param id
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
	int update(ReqTemplateCondition record);

	/**
	 * 保存信息
	 * @param record
	 * @return
	 */
	int save(ReqTemplateCondition record);

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * 根据停车券id查找发放条件列表
	 */
	List<ResTemplateCondition> findConditionList(Long tempId);
	/**
	 * 设置setDefault
	 * @param id
	 */
	int setDefault(Long id);
	/**
	 * 查看详情
	 * @param id
	 * @return
	 */
	ResTemplateCondition detail(Long id);
	
}
