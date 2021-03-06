package cn.linkmore.account.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.client.hystrix.AccountClientHystrix;
import cn.linkmore.account.client.hystrix.CustomerInfoClientHystrix;
import cn.linkmore.account.request.ReqCustomerInfoExport;
import cn.linkmore.account.response.ResCustomerInfoExport;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
 
/**
 * 账户--远程调用
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "account-server", path = "/account", fallback=AccountClientHystrix.class,configuration = FeignConfiguration.class)
public interface AccountClient {

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(ViewPageable pageable); 
	
}
