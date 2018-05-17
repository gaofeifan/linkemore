package cn.linkmore.account.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.account.client.hystrix.VehicleMarkClientHystrix;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.feign.FeignConfiguration;

@Controller
@FeignClient(value = "account-server", path = "/account/vehicle_mark", fallback=VehicleMarkClientHystrix.class,configuration = FeignConfiguration.class)
public interface VehicleMarkClient {
	@RequestMapping(value = "/v2.0/{userId}", method = RequestMethod.GET)
	public List<ResVechicleMark> list(@PathVariable ("userId")Long userId);
	
	@RequestMapping(value = "/v2.0", method = RequestMethod.POST)
	public void create( @RequestBody ReqVehicleMark bean);
	
	@RequestMapping(value = "/v2.0/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id);
}
