package cn.linkmore.coupon.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import cn.linkmore.coupon.client.hystrix.CouponClientHystrix;
import cn.linkmore.feign.FeignConfiguration;
/**
 * Client  - 停车券
 * @author liwenlong
 * @version 2.0
 *
 */
@FeignClient(value = "coupon-server", path = "/coupon", fallback=CouponClientHystrix.class,configuration = FeignConfiguration.class)
public interface CouponClient {

}
