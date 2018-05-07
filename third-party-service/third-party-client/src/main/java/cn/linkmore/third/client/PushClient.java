package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import cn.linkmore.third.client.hystrix.OssClientHystrix;

/**
 * Client - 推送
 * @author liwenlong
 * @version 2.0
 * 
 */
@FeignClient(value = "third-party-server", path = "/third/push", fallback=OssClientHystrix.class,configuration = FeignConfiguration.class)
public interface PushClient {

}
