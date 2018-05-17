package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.WechatClientHystrix;

/**
 * Client - 微信服务号
 * @author liwenlong
 * @version 2.0
 */
@FeignClient(value = "third-party-server", path = "/wechat", fallback=WechatClientHystrix.class,configuration = FeignConfiguration.class)
public interface WechatClient {

}
