package cn.linkmore.prefecture.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentedRecord;
import cn.linkmore.enterprise.response.ResRentedRecord;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.RentUserClientHystrix;
import cn.linkmore.prefecture.client.hystrix.RentedRecordClientHystrix;
/**
 * 远程调用 - 长租用户使用记录
 * @author   GFF
 * @Date     2018年8月1日
 * @Version  v2.0
 */
@FeignClient(value = "enterprise-server", path = "/rented-record", fallback=RentedRecordClientHystrix.class,configuration = FeignConfiguration.class)
public interface OpsRentedRecordClient {

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findList(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	public List<ResRentedRecord> exportList(@RequestBody ReqRentedRecord bean);
	
}
