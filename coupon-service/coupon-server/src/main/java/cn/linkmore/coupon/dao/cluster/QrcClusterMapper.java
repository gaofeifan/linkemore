package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.coupon.response.ResQrc;

@Mapper
public interface QrcClusterMapper {

    ResQrc findById(Long id);

	List<ResQrc> findCouponQrcList(Long tempId);
}