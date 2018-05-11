package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.PushClientHystrix;

/**
 * Client - 推送
 * @author liwenlong
 * @version 2.0
 * 
 */
@FeignClient(value = "third-party-server", path = "/third/push", fallback=PushClientHystrix.class,configuration = FeignConfiguration.class)
public interface PushClient {

}
