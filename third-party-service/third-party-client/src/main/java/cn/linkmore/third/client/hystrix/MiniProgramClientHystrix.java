package cn.linkmore.third.client.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import cn.linkmore.third.client.MiniProgramClient;
import cn.linkmore.third.response.ResMiniSession;
@Component
public class MiniProgramClientHystrix implements MiniProgramClient {
	 
	public ResMiniSession getSession(@PathVariable("code") String code) {
		return null;
	}
}
