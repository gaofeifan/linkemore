package cn.linkmore.third.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.third.ws.WebSocketServer;

@RestController
@RequestMapping("/feign/ws")
public class WebsocketController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/v2.0/send", method = RequestMethod.POST) 
	@ResponseBody
	public Boolean push(@RequestParam("message")String message,@RequestParam("token")String token) { 
		log.info("push token:{} ws message:{}",token,message);
		Boolean success = false;
		try {
			WebSocketServer.send(token, message);
			success = true;
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return success;
	}
}
