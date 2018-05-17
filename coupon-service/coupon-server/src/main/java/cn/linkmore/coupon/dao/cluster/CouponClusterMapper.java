package cn.linkmore.coupon.dao.cluster;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import cn.linkmore.coupon.entity.Coupon;
import cn.linkmore.coupon.response.ResCoupon;

@Mapper
public interface CouponClusterMapper {
	/**
	 * 根据主键查询优惠券信息	 * @param id Long
	 * @return
	 */
    Coupon findById(Long id);

    /**
     * 根据用户ID查询其可用券
     * @param userId
     * @return
     */
	List<ResCoupon> findEnabledList(Long userId);
 

}