package cn.linkmore.coupon.dao.master;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.coupon.entity.Coupon;
@Mapper
public interface CouponMasterMapper {
	/**
	 * 删除id对应的信息
	 * @param id 主键
	 * @return  删除条数
	 */
	int delete(Long id);

	/**
	 * 保存信息
	 * @param record 优惠券信息
	 * @return 保存条数
	 */
	int save(Coupon record);  

	/**
	 * 更新信息
	 * @param record 优惠券信息
	 * @return 更新条数
	 */
	int update(Coupon record); }