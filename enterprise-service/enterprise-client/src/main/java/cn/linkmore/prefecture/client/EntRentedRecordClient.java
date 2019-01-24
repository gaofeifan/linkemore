package cn.linkmore.prefecture.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.enterprise.response.ResEntRentedRecord;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.EntRentedRecordClientHystrix;
@FeignClient(value = "enterprise-server", path = "/feign/ent-rented", fallback=EntRentedRecordClientHystrix.class,configuration = FeignConfiguration.class)
public interface EntRentedRecordClient {
	
	@RequestMapping(value = "/find-user-id", method = RequestMethod.GET)
	@ResponseBody
	public ResEntRentedRecord findByUserId(@RequestParam("userId") Long userId);

	@RequestMapping(value = "/down-time", method = RequestMethod.GET)
	@ResponseBody
	public void updateDownTime(@RequestParam("stallId") Long stallId);
	
	@RequestMapping(value = "/last-plate-number", method = RequestMethod.GET)
	@ResponseBody
	public ResEntRentedRecord findLastPlateNumber(@RequestParam("stallId") Long stallId);

	@RequestMapping(value = "/last-plate-number-by-pre", method = RequestMethod.GET)
	@ResponseBody
	public List<ResEntRentedRecord> findLastPlateNumberByPreId(@RequestParam("preId") Long preId);
}
