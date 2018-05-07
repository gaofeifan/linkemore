package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import cn.linkmore.third.client.hystrix.OssClientHystrix;

/**
 * Client - 短信服务
 * @author liwenlong
 *
 */
@FeignClient(value = "third-party-server", path = "/third/sms", fallback=OssClientHystrix.class,configuration = FeignConfiguration.class)
public interface SmsClient {

}
