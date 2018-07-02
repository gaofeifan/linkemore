package cn.linkmore.third.client.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.third.client.WebsocketClient;

/**
 * hystrix - websocket
 * @author liwenlong
 * @version 2.0
 *
 */
@Component
public class WebsocketClientHystrix implements WebsocketClient {
	
	public Boolean push(@RequestParam("message")String message,@RequestParam("token")String token) {
		return false;
	}

}
