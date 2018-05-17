package cn.linkmore.account.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.request.ReqLogin;
import cn.linkmore.account.request.ReqNickname;
import cn.linkmore.account.request.ReqSex;
import cn.linkmore.account.request.ReqVehicle;
import cn.linkmore.account.request.ReqWxLogin;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.service.UserService;

/**
 * 用户
 * @author   GFF
 * @Date     2018年5月16日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/account/user")
public class UserControlle {
	
	@Resource
	private UserService userService;

	/**
	 * @Description  退出登录
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/v2.0/logout/{userId}" , method = RequestMethod.GET)
	public void logout(@PathVariable Long userId) {
		try {
			this.userService.logout(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description  更新昵称
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/nickname", method = RequestMethod.PUT)
	@ResponseBody
	public void updateNickname(@RequestBody ReqNickname nickname) {
		this.userService.updateNickname(nickname);
	}
	
	/**
	 * @Description  更新性别
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/sex", method = RequestMethod.PUT)
	@ResponseBody
	public void updateSex(@RequestBody ReqSex sex) {
		this.userService.updateSex(sex);
	}
	
	/**
	 * @Description  更新车牌
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/vehicle", method = RequestMethod.PUT)
	@ResponseBody
	public void updateVehicle(@RequestBody ReqVehicle req) {
		this.userService.updateVehicle(req);
	}
	
	
	/**
	 * @Description  查询详情
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/detail/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public ResUserDetails detail(@PathVariable Long userId) {
		ResUserDetails res = this.userService.detail(userId); 
		return res;
	}
	
	
	/**
	 * @Description  更新手机号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/mobile", method = RequestMethod.PUT)
	@ResponseBody
	public void updateMobile( @RequestBody ReqLogin bean) {
		this.userService.updateMobile(bean);
	}
	
	/**
	 * @Description  更新微信号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/wechat", method = RequestMethod.PUT)
	@ResponseBody
	public void updateWechat(@RequestBody ReqWxLogin bean) {
		this.userService.updateWechat(bean);
	}
	
	/**
	 * @Description  删除微信号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/wechat/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public void removeWechat(@PathVariable Long userId) {
		this.userService.removeWechat(userId);
	}
	
	@RequestMapping(value = "/v2.0/mobile/{mobile}", method = RequestMethod.GET)
	public void selectByMobile(@PathVariable String mobile) {
		this.userService.selectByMobile(mobile);
	}
	
	@RequestMapping(value = "/v2.0/cache/{userId}", method = RequestMethod.GET)
	public ResUser getUserCacheKey(@PathVariable Long userId) {
		ResUser user = this.userService.getUserCacheKey(userId);
		return user;
	}
	
	
	
	
}
