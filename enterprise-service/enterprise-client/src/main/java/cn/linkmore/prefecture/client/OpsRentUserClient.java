package cn.linkmore.prefecture.client;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.enterprise.response.ResEntRentUser;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.RentUserClientHystrix;
/**
 *  远程调用 - 长租用户
 * @author   GFF
 * @Date     2018年8月1日
 * @Version  v2.0
 */
@FeignClient(value = "enterprise-server", path = "/ops/rent", fallback=RentUserClientHystrix.class,configuration = FeignConfiguration.class)
public interface OpsRentUserClient {

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findList(@RequestBody ViewPageable pageable);

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqRentUser user);
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqRentUser user);
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids);

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck);
	
	@RequestMapping(value = "/used-stall-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResEntRentUser> usedStallList();
	
}
