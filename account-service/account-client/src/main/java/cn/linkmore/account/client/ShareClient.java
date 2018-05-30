package cn.linkmore.account.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.account.client.hystrix.ShareClientHystrix;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
 
/**
 * 分享记录
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "account-server", path = "/share", fallback=ShareClientHystrix.class,configuration = FeignConfiguration.class)
public interface ShareClient {
	
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ViewPage list(@RequestBody ViewPageable pageable);
	
}
