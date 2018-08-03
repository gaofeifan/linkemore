package cn.linkmore.prefecture.client;

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

	/**
	 * @Description 分页查询
	 * @Author cl
	 * @Version v2.0
	 */
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	ViewPage findPage(@RequestBody ViewPageable pageable);

}
