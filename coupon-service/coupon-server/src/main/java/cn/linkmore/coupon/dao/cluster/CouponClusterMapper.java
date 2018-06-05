package cn.linkmore.coupon.dao.cluster;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.coupon.response.ResCoupon;

@Mapper
public interface CouponClusterMapper {
	/**
	 * 根据主键查询优惠券信息	 
	 * @param id Long
	 * @return
	 */
	ResCoupon findById(Long id);

    /**
     * 根据用户ID查询其可用券
     * @param userId
     * @return
     */
	List<ResCoupon> findEnabledList(Long userId);
 

}