package cn.linkmore.notice.client.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.notice.client.EntSocketClient;
import cn.linkmore.notice.client.UserSocketClient;

/**
 * 容错处理 - WebSocket处理消息
 * @author liwenlong
 * @version 2。0
 */
@Component
public class EntSocketClientHystrix implements EntSocketClient {
	public Boolean push(@RequestParam("message")String message,@RequestParam("openid")String openid) {
		return false;
	}
}
