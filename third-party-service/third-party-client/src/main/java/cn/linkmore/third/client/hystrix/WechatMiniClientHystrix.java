package cn.linkmore.third.client.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.third.client.WechatMiniClient;
import cn.linkmore.third.request.ReqWechatMiniOrder;
import cn.linkmore.third.response.ResMiniSession;
import cn.linkmore.third.response.ResWechatMiniOrder;
@Component
public class WechatMiniClientHystrix implements WechatMiniClient {
	 
	public ResMiniSession getSession(@PathVariable("code") String code) {
		return null;
	}
	 
	public ResWechatMiniOrder order(@RequestBody ReqWechatMiniOrder wechat) { 
		return null;
	}
	 
	public Boolean verify(@RequestParam("json")String json) {
		return false;
	}
}
