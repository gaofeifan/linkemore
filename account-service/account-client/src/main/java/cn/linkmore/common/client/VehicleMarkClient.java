package cn.linkmore.common.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;

import cn.linkmore.common.client.hystrix.VehicleMarkClientHystrix;
import cn.linkmore.feign.FeignConfiguration;

@Controller
@FeignClient(value = "account-server", path = "/account/vehicle_mark", fallback=VehicleMarkClientHystrix.class,configuration = FeignConfiguration.class)
public interface VehicleMarkClient {

}
