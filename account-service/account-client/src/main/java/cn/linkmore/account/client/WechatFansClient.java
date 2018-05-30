package cn.linkmore.account.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.client.hystrix.WechatFansClientHystrix;
import cn.linkmore.account.request.ReqWechatFans;
import cn.linkmore.account.request.ReqWechatFansExcel;
import cn.linkmore.account.response.ResWechatFans;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;

/**
 * 微信粉---远程连接
 * 
 * @author GFF
 * @Date 2018年5月29日
 * @Version v2.0
 */
@FeignClient(value = "account-server", path = "/wechat_fans", fallback = WechatFansClientHystrix.class, configuration = FeignConfiguration.class)
public interface WechatFansClient {
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public List<ResWechatFans> exportList(@RequestBody ReqWechatFansExcel bean);

	@RequestMapping(method = RequestMethod.POST)
	public void save(@RequestBody ReqWechatFans bean);

	@RequestMapping(method = RequestMethod.PUT)
	public void update(@RequestBody ReqWechatFans bean);
}
