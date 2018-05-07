package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import cn.linkmore.third.client.hystrix.UnionPayClientHystrix;

/**
 * Client - 苹果支付[App]
 * @author liwenlong
 * @version 2.0
 */
@FeignClient(value = "third-party-server", path = "/third/apple-pay", fallback=UnionPayClientHystrix.class,configuration = FeignConfiguration.class)
public interface UnionPayClient {

}
