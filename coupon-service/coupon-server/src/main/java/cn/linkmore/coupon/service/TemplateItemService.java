package cn.linkmore.coupon.service;

import java.util.List;
import cn.linkmore.coupon.request.ReqTemplateItem;
import cn.linkmore.coupon.response.ResTemplateItem;

public interface TemplateItemService {

	/**
	 * 更新信息
	 * @param record
	 * @return
	 */
	int update(ReqTemplateItem record);

	/**
	 * 保存信息
	 * @param record
	 * @return
	 */
	int save(ReqTemplateItem record);
	
	/**
	 * 根据停车券套餐id查找停车券列表
	 * @param templdateId
	 * @return
	 */
	List<ResTemplateItem> findList(Long templdateId);
}
