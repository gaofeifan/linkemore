package cn.linkmore.prefecture.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEnterprise;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.enterprise.request.ReqVipUser;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.EnterpriseClientHystrix;
import cn.linkmore.prefecture.client.hystrix.OpsEntVipUserClientHystrix;
import cn.linkmore.prefecture.client.hystrix.PrefectrueClientHystrix;

/**
 * 企业车区远程调用
 * 
 * @author cl
 * @Date 2018年7月27日
 * @Version v2.0
 */
@FeignClient(value = "enterprise-server", path = "/ops/vip", fallback = OpsEntVipUserClientHystrix.class, configuration = FeignConfiguration.class)
public interface OpsEntVipUserClient {


	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	ViewPage findPage(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqVipUser user);
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqVipUser user);
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids);

}
