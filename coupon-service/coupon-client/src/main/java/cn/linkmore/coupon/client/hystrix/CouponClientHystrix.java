package cn.linkmore.coupon.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.coupon.client.CouponClient;
import cn.linkmore.coupon.request.ReqCouponPay;
import cn.linkmore.coupon.response.ResCoupon;
import cn.linkmore.coupon.response.ResTemplate;

@Component
public class CouponClientHystrix implements CouponClient { 
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public void pay(@RequestBody ReqCouponPay rcp){
		
	}

	@Override
	public List<ResCoupon> enable(Long userId) {
		return null;
	}

	@Override
	public List<ResCoupon> order(Long userId, Long orderId) {
		return null;
	}

	@Override
	public ResCoupon get(Long id) {
		return null;
	}


	@Override
	public boolean send(Long userId) {
		log.info("coupon client hystrix send userId {}",userId);
		return false;
	}

	@Override
	public boolean sendBrandCoupon(Boolean isBrandUser, Long entId, Long userId) {
		log.info("coupon client hystrix sendBrandCoupon userId {}",userId);
		return false;
	}

	@Override
	public List<ResCoupon> findBrandCouponList(Long entId, Long userId) {
		log.info("coupon client hystrix findBrandCouponList userId {}",userId);
		return null;
	}

	@Override
	public List<ResTemplate> findEntTemplateList(Long entId) {
		log.info("coupon client hystrix findEntTemplateList entId {}",entId);
		return null;
	}

}
