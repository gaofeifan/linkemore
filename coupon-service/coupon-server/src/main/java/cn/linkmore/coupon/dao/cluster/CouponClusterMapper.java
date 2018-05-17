package cn.linkmore.coupon.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.entity.Coupon;
@Mapper
public interface CouponClusterMapper {
	/**
	 * 根据主键查询优惠券信息	 * @param id Long
	 * @return
	 */
    Coupon findById(Long id);

}