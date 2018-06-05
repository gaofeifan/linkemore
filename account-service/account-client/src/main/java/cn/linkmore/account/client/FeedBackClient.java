package cn.linkmore.account.client;

import java.util.List;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.client.hystrix.FeedBackClientHystrix;
import cn.linkmore.account.request.ReqFeedBack;
import cn.linkmore.account.response.ResFeedBack;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
 
/**
 * 用户
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "account-server", path = "/feedback", fallback=FeedBackClientHystrix.class,configuration = FeignConfiguration.class)
public interface FeedBackClient {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/page", method = RequestMethod.POST)
	@ResponseBody
	ViewPage findPage(@RequestBody ViewPageable pageable);

	/**
	 * @Description  导出
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/export", method = RequestMethod.POST)
	@ResponseBody
	List<ResFeedBack> exportList(@RequestBody ReqFeedBack bean);
	
	
}
