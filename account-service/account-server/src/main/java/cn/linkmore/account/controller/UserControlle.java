package cn.linkmore.account.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

	@RequestMapping(method = RequestMethod.GET)
	public void logout(Long userId) {
		try {
			this.userService.logout(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/update_nickname/{nickname}", method = RequestMethod.PUT)
	@ResponseBody
	public void updRateNickname(Long userId,@PathVariable("nickname")String nickname) {
		this.userService.updateNickname(nickname,userId);
	}
	
	@RequestMapping(value = "/update_sex/{sex}", method = RequestMethod.PUT)
	@ResponseBody
	public void updRateNickname(Long userId,@PathVariable("sex")Integer sex) {
		this.userService.updateSex(sex,userId);
	}
	
	@RequestMapping(value = "/update_vehicle", method = RequestMethod.PUT)
	@ResponseBody
	public void updateVehicle(Long userId,@RequestBody ReqVehicle req) {
			this.userService.updateVehicle(req,userId);
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ResponseBody
	public ResUserDetails detail(Long userId) { 
		ResUserDetails res = this.userService.detail(userId); 
		return res;
	}
	
	@RequestMapping(value = "/send_code", method = RequestMethod.GET)
	@ResponseBody
	public void sendCode(Long userId, @Valid @RequestBody ReqBind bean) {
		/*this.messageService.sendLoginCode(bean.getMobile());*/
	}

	@RequestMapping(value = "/update_mobile", method = RequestMethod.PUT)
	@ResponseBody
	public void updateMobile(Long userId, @Valid @RequestBody ReqLogin bean) {
		this.userService.updateMobile(bean,userId);
	}
	
	@RequestMapping(value = "/update_wechat", method = RequestMethod.PUT)
	@ResponseBody
	public void updateWechat(Long userId, @Valid @RequestBody ReqWxLogin bean) {
		this.userService.updateWechat(bean,userId);
	}
	
	@RequestMapping(value = "/remove_wechat", method = RequestMethod.DELETE)
	@ResponseBody
	public void removeWechat(Long userId) {
		this.userService.removeWechat(userId);
	}
	
	
	
}
