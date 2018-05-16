package cn.linkmore.account.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.request.ReqBind;
import cn.linkmore.account.request.ReqLogin;
import cn.linkmore.account.request.ReqVehicle;
import cn.linkmore.account.request.ReqWxLogin;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.service.UserService;

@RestController
@RequestMapping("/account/user")
public class UserControlle {
	
	@Resource
	private UserService userService;

	@RequestMapping(value="/v2.0/logout/{userId}" , method = RequestMethod.GET)
	public void logout(@PathVariable Long userId) {
		try {
			this.userService.logout(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/v2.0/update_nickname/{userId}/{nickname}", method = RequestMethod.PUT)
	@ResponseBody
	public void updRateNickname(@PathVariable Long userId,@PathVariable("nickname")String nickname) {
		this.userService.updateNickname(nickname,userId);
	}
	
	@RequestMapping(value = "/v2.0/update_sex/{sex}/{userId}", method = RequestMethod.PUT)
	@ResponseBody
	public void updRateNickname(@PathVariable Long userId,@PathVariable("sex")Integer sex) {
		this.userService.updateSex(sex,userId);
	}
	
	@RequestMapping(value = "/v2.0/update_vehicle", method = RequestMethod.PUT)
	@ResponseBody
	public void updateVehicle(@RequestBody ReqVehicle req) {
			this.userService.updateVehicle(req);
	}
	
	@RequestMapping(value = "/v2.0/detail/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public ResUserDetails detail(@PathVariable Long userId) {
		ResUserDetails res = this.userService.detail(userId); 
		return res;
	}
	
	@RequestMapping(value = "/v2.0/send_code", method = RequestMethod.GET)
	@ResponseBody
	public void sendCode( @RequestBody ReqBind bean) {
		/*this.messageService.sendLoginCode(bean.getMobile());*/
	}

	@RequestMapping(value = "/v2.0/update_mobile", method = RequestMethod.PUT)
	@ResponseBody
	public void updateMobile( @RequestBody ReqLogin bean) {
		this.userService.updateMobile(bean);
	}
	
	@RequestMapping(value = "/v2.0/update_wechat", method = RequestMethod.PUT)
	@ResponseBody
	public void updateWechat(@RequestBody ReqWxLogin bean) {
		this.userService.updateWechat(bean);
	}
	
	@RequestMapping(value = "/v2.0/remove_wechat/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public void removeWechat(@PathVariable Long userId) {
		this.userService.removeWechat(userId);
	}
	
	
	
}
