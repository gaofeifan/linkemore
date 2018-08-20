package cn.linkmore.notice.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.notice.socket.UserSocketServer;


/**
 * web-socket通知
 * @author   GFF
 * @Date     2018年8月20日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/feign/ent-socket")
public class FeignEntSocketController { 
		
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/v2.0/send", method = RequestMethod.POST) 
	@ResponseBody
	public Boolean push(@RequestParam("message")String message,@RequestParam("openid")String openid) { 
		log.info("push openid:{} ws message:{}",openid,message);
		Boolean success = false;
		try {
			UserSocketServer.send(openid, message);
			success = true;
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return success;
	}
} 