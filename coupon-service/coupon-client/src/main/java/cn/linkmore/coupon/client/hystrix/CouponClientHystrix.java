package cn.linkmore.coupon.client.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.coupon.client.CouponClient;
import cn.linkmore.coupon.request.ReqCouponPay;
import cn.linkmore.coupon.response.ResCoupon;

@Component
public class CouponClientHystrix implements CouponClient { 
	@Override
	public void pay(@RequestBody ReqCouponPay rcp){
		
	}
	

	@Override
	public List<ResCoupon> enable(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResCoupon> order(Long userId, Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResCoupon get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
