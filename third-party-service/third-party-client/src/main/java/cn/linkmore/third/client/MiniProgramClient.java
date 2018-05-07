package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import cn.linkmore.third.client.hystrix.MiniProgramClientHystrix;
/**
 * Client - 微信小程序
 * @author liwenlong
 * @version 2.0
 */
@FeignClient(value = "third-party-server", path = "/third/mini-program", fallback=MiniProgramClientHystrix.class,configuration = FeignConfiguration.class)
public interface MiniProgramClient {

}
