package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import cn.linkmore.third.client.hystrix.AppWechatClientHystrix;

/**
 * Client - 微信支付[集成App]
 * @author liwenlong
 * @version 2.0
 */
@FeignClient(value = "third-party-server", path = "/third/wechat-pay", fallback=AppWechatClientHystrix.class,configuration = FeignConfiguration.class)
public interface AppWechatClient {
	
}
