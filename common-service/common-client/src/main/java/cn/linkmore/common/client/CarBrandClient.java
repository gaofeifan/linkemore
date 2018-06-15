package cn.linkmore.common.client;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.common.client.hystrix.CarBrandClientHystrix;
import cn.linkmore.feign.FeignConfiguration;
/**
 * 车辆品牌
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "common-server", path = "/feign/car_brand", fallback=CarBrandClientHystrix.class,configuration = FeignConfiguration.class)
public interface CarBrandClient {
	
	@RequestMapping(method = RequestMethod.GET)
	public Object list();

	@RequestMapping(method = RequestMethod.GET)
	public Map<String,Object> load();
}
