package cn.linkmore.coupon.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.coupon.dao.cluster.QrcClusterMapper;
import cn.linkmore.coupon.response.ResQrc;
import cn.linkmore.coupon.service.QrcService;

@Service
public class QrcServiceImpl implements QrcService {
	
	@Autowired
	private QrcClusterMapper qrcClusterMapper;

	@Override
	public ResQrc findByTempId(Long tempId) {
		List<ResQrc> couponQrcList = this.qrcClusterMapper.findCouponQrcList(tempId);
		ResQrc couponQrc = null;
		if(couponQrcList.size()>0){
			couponQrc = couponQrcList.get(0);
		}
		return couponQrc;
	}

}
