package cn.linkmore.coupon.dao.cluster;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.coupon.entity.QrcReceive;
@Mapper
public interface QrcReceiveClusterMapper {

	QrcReceive findById(Long id);

}