package cn.linkmore.account.client;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.client.hystrix.NoticeClientHystrix;
import cn.linkmore.account.client.hystrix.UserGuideClientHystrix;
import cn.linkmore.account.request.ReqNotice;
import cn.linkmore.account.request.ReqPageNotice;
import cn.linkmore.account.response.ResNotice;
import cn.linkmore.account.response.ResPage;
import cn.linkmore.account.response.ResUserGuide;
import cn.linkmore.feign.FeignConfiguration;
 
/**
 * 消息管理--远程调用
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "account-server", path = "/notice", fallback=NoticeClientHystrix.class,configuration = FeignConfiguration.class)
public interface NoticeClient {
	
	/**
	 * 消息分页列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ResPage listNotice(@RequestBody ReqPageNotice bean);

	/**
     *阅读
     */
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    @ResponseBody
    public ResNotice read(@RequestBody ReqNotice notice);

	/**
	 * 删除
	 */
	@RequestMapping( method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody ReqNotice notice);
	
}
