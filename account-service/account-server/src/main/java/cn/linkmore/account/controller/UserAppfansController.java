package cn.linkmore.account.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.response.ResUserAppfans;
import cn.linkmore.account.service.UserAppfansService;

@RestController
@RequestMapping("/account/user_appfans")
public class UserAppfansController {

	@Resource
	private UserAppfansService userAppfansService;
	
	@RequestMapping(value="/v2.0/{userId}",method = RequestMethod.GET)
	public ResUserAppfans selectByUserId(@PathVariable Long userId) {
		return userAppfansService.selectByUserId(userId);
	}
}
