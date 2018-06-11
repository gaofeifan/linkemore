package cn.linkmore.account.client;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.account.client.hystrix.UserGuideClientHystrix;
import cn.linkmore.account.response.ResUserGuide;
import cn.linkmore.feign.FeignConfiguration;
 
/**
 * 用户指南--远程调用
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "account-server", path = "/user_guide", fallback=UserGuideClientHystrix.class,configuration = FeignConfiguration.class)
public interface UserGuideClient {
	
	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/detail/{language}", method = RequestMethod.GET)
	public List<ResUserGuide> list(@PathVariable("language") String language);

	
}
