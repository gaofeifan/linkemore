package cn.linkmore.prefecture.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntUser;
import cn.linkmore.enterprise.request.ReqRentEntUser;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.OpsRentEntUserClientHystrix;

@FeignClient(value = "enterprise-server", path = "/ops/rent-ent-user", fallback=OpsRentEntUserClientHystrix.class,configuration = FeignConfiguration.class)
public interface OpsRentEntUserClient {

	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value = "/v2.0", method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqRentEntUser ent);
	
	@RequestMapping(value = "/v2.0", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqRentEntUser ent);
	
	@RequestMapping(value = "/v2.0/ids", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids);

	@RequestMapping(value = "/v2.0/i-list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage listI(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value = "/v2.0/i", method = RequestMethod.POST)
	@ResponseBody
	public void saveI(@RequestBody ReqRentUser ent);
	
	@RequestMapping(value = "/v2.0/i", method = RequestMethod.PUT)
	@ResponseBody
	public void updateI(@RequestBody ReqRentUser ent);
	
	@RequestMapping(value = "/v2.0/i-ids", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteI(@RequestBody List<Long> ids);


}
