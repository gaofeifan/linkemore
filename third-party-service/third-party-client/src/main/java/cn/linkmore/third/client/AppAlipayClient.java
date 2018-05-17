package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.AppAlipayClientHystrix;
/**
 * Client - 支付宝支付服务[集成App]
 * @author liwenlong
 * @version 2.0
 *
 */
@FeignClient(value = "third-party-server", path = "/alipay", fallback=AppAlipayClientHystrix.class,configuration = FeignConfiguration.class)
public interface AppAlipayClient {

}
