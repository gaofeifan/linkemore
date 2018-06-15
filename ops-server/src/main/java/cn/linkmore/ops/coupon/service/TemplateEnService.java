package cn.linkmore.ops.coupon.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplate;
import cn.linkmore.coupon.response.ResTemplate;

public interface TemplateEnService {
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
	int update(ReqTemplate record);

	/**
	 * 保存信息
	 * @param record
	 */
	int save(ReqTemplate record);

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * 开启
	 * @param id
	 */
	int start(Long id);
	/**
	 * 暂停
	 * @param id
	 */
	int stop(Long id);
	
	ResTemplate findById(Long id);
	
	List<ResTemplate> findByEnterpriseId(Long entId);
	
	/**
	 * @Description 商家自定义修改
	 * @Author   GFF 
	 * @Date       2018年3月28日
	 * @Param    CouponTemplateEnService
	 * @Return   void
	 */
	int saveBusiness(ReqTemplate record);
	
}
