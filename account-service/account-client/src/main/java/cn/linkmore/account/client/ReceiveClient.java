package cn.linkmore.account.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.client.hystrix.ReceiveClientHystrix;
import cn.linkmore.account.client.hystrix.ShareClientHystrix;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
 
/**
 * 领券记录
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "account-server", path = "/receive", fallback=ReceiveClientHystrix.class,configuration = FeignConfiguration.class)
public interface ReceiveClient {
	
	/**
	 * @Description	分页查询  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/page", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	/**
	 * @Description  详情分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/detail_page", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage detailList(@RequestBody ViewPageable pageable);	
}
