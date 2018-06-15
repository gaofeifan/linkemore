package cn.linkmore.coupon.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.hystrix.SendRecordClientHystrix;
import cn.linkmore.coupon.request.ReqCouponPay;
import cn.linkmore.coupon.request.ReqSendRecord;
import cn.linkmore.coupon.response.ResCoupon;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.feign.FeignConfiguration;
/**
 * Client  - 发送记录
 * @author jiaohanbin
 * @version 2.0
 *
 */
@FeignClient(value = "coupon-server", path = "/coupon_send_record", fallback=SendRecordClientHystrix.class,configuration = FeignConfiguration.class)
public interface SendRecordClient {
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqSendRecord record);

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);

	@RequestMapping(value = "/v2.0/saveBusiness", method = RequestMethod.POST)
	@ResponseBody
	public int saveBusiness(@RequestBody ReqSendRecord record) ;
}
