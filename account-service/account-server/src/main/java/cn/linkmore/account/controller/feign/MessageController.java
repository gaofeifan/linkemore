package cn.linkmore.account.controller.feign;

import javax.annotation.Resource;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.request.ReqMessage;
import cn.linkmore.account.service.MessageService;

/**
 * 验证码
 * @author   GFF
 * @Date     2018年10月24日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/feign/message")
public class MessageController {

	@Resource
	private MessageService messageService;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqMessage message) {
		this.messageService.save(message);
	}
}
