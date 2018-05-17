package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.OssClientHystrix;
 

/**
 * Client - Oss文件服务
 * @author liwenlong
 * @version 2.0
 *
 */
@FeignClient(value = "third-party-server", path = "/oss", fallback=OssClientHystrix.class,configuration = FeignConfiguration.class)
public interface OssClient {

}
