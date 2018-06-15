package cn.linkmore.ops.coupon.service;

import java.util.List;
import cn.linkmore.coupon.request.ReqTemplateItem;
import cn.linkmore.coupon.response.ResTemplateItem;

public interface TemplateItemEnService {

	/**
	 * 更新信息
	 * @param record
	 * @return
	 */
	int update(ReqTemplateItem record);

	/**
	 * 保存信息
	 * @param record
	 */
	int save(ReqTemplateItem record);
	
	/**
	 * 根据停车券套餐id查找停车券列表
	 * @param templdateId
	 * @return
	 */
	List<ResTemplateItem> findList(Long templdateId);

	/**
	 * @Description  根据合同编号查询项
	 * @Author   GFF 
	 * @Date       2018年3月30日
	 * @Param    CouponTemplateItemEnService
	 * @Return   List<CouponTemplateItem>
	 */
	List<ResTemplateItem> selectBuDealNumber(String dealNumber);

	/**
	 * @Description  根据企业查询
	 * @Author   GFF 
	 * @Date       2018年4月3日
	 * @Param    CouponTemplateItemEnService
	 * @Return   List<CouponTemplateItem>
	 */
	List<ResTemplateItem> selectBuEnterpriseId(Long id);
	
	void updateCouponStatus();
}
