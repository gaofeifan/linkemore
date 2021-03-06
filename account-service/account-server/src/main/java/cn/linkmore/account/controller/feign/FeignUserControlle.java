package cn.linkmore.account.controller.feign;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.linkmore.account.request.ReqUpdateAccount;
import cn.linkmore.account.request.ReqUpdateMobile;
import cn.linkmore.account.request.ReqUpdateNickname;
import cn.linkmore.account.request.ReqUpdateSex;
import cn.linkmore.account.request.ReqUpdateVehicle;
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.request.ReqUserResetPW;
import cn.linkmore.account.response.ResPageUser;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.response.ResUserLogin;
import cn.linkmore.account.service.UserAppfansService;
import cn.linkmore.account.service.UserService;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 用户
 * @author   GFF
 * @Date     2018年5月16日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/feign/user")
public class FeignUserControlle {
	
	@Resource
	private UserService userService;
	@Resource
	private UserAppfansService userAppfansService;
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * @Description  更新昵称
	 * @Author   GFF 
	 * @Version  v2.0
	 */
/*	@RequestMapping(value = "/v2.0/nickname", method = RequestMethod.PUT)
	@ResponseBody
	public void updateNickname(@RequestBody ReqUpdateNickname nickname) {
		this.userService.updateNickname(nickname);
	}*/
	
	/**
	 * @Description  更新性别
	 * @Author   GFF 
	 * @Version  v2.0
	 */
/*	@RequestMapping(value = "/v2.0/sex", method = RequestMethod.PUT)
	@ResponseBody
	public void updateSex(@RequestBody ReqUpdateSex sex) {
		this.userService.updateSex(sex);
	}*/
	
	/**
	 * @Description  更新车牌
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	/*@RequestMapping(value = "/v2.0/vehicle", method = RequestMethod.PUT)
	@ResponseBody
	public void updateVehicle(@RequestBody ReqUpdateVehicle req) {
		this.userService.updateVehicle(req);
	}*/
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public ResUser save(@RequestBody ResUser user) {
		log.info("----------user---------"+JSON.toJSON(user));
		return userService.save(user);
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
	public void updateMobile( @RequestBody ReqUpdateMobile bean) {
		this.userService.updateMobile(bean);
	}
	
	/**
	 * @Description  更新微信号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/appfans", method = RequestMethod.PUT)
	@ResponseBody
	public void updateAppfans(@RequestBody ReqUserAppfans bean) {
		this.userService.updateAppfans(bean);
	}
	
	/**
	 * @Description  删除微信号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	/*@RequestMapping(value = "/v2.0/wechat/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public void removeWechat(@PathVariable Long userId) {
		this.userService.removeWechat(userId);
	}*/
	
	/**
	 * @Description  app登录
	 * @Author   GFF 
	 * @Version  v2.0
	 */
/*	@RequestMapping(value = "/v2.0/login/{mobile}", method = RequestMethod.GET)
	@ResponseBody
	public ResUserLogin appLogin(@PathVariable("mobile") String mobile) {
		return this.userService.appLogin(mobile);
	}*/
	 
	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/v2.0/{id}",method = RequestMethod.GET)
	public ResUser findById(@PathVariable("id") Long id) {
		ResUser user = userService.findById(id);
		return user;
	}
	/**
	 * 更新用户下单数
	 * @param id
	 */
	@RequestMapping(value="/v2.0/order",method = RequestMethod.POST)
	public void order(@RequestParam("id")Long id) {
		 this.userService.order(id);
	}
	
	@RequestMapping(value="/v2.0/checkout",method = RequestMethod.POST)
	public void checkout(@RequestParam("id")Long id) {
		 this.userService.checkout(id);
	}
	
	/**
	 * @Description	查询list  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/page", method = RequestMethod.POST)
	public ViewPage findPage(@RequestBody ViewPageable pageable) {
		return this.userService.findPage(pageable);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return this.userService.delete(ids);
	}
	
	/**
	 * @Description	导出
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/export", method = RequestMethod.POST)
	public List<ResPageUser> export(@RequestBody ViewPageable pageable) {
		return this.userService.export(pageable);
	}
	
	/**
	 * @Description  更新账户名称
	 * @Author   GFF 
	 * @Version  v2.0
	 */
/*	@RequestMapping(value = "/v2.0/account_name", method = RequestMethod.PUT)
	public void updateAccountName(@RequestBody ReqUpdateAccount account) {
		this.userService.updateRealname(account);
	}*/
	
	@RequestMapping(value = "/v2.0/by_username", method = RequestMethod.GET)
	@ResponseBody
	public ResUser getUserByUserName(@RequestParam("userName") String userName) {
		return this.userService.getUserByUserName(userName);
	}
	
	@RequestMapping(value = "/by-mobile", method = RequestMethod.GET)
	@ResponseBody
	public Long getUserIdByMobile(@RequestParam("mobile") String mobile) {
		return this.userService.getUserIdByMobile(mobile);
	}
	
	@RequestMapping(value = "/by-mobile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Long> getUserMapByMobile(@RequestBody List<String> mobile) {
		return this.userService.getUserMapByMobile(mobile);
	}
	
	@RequestMapping(value = "/by-all", method = RequestMethod.GET)
	@ResponseBody
	public List<ResUser> findAll(){
		return this.userService.findAll();
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.PUT)
	@ResponseBody
	public Object reset(@RequestBody ReqUserResetPW reset) {
		this.userService.reset(reset.getIds(),reset.getPassword());
		return null;
	}
	
	@RequestMapping(value = "/by-mobile-username", method = RequestMethod.GET)
	@ResponseBody
	public Long getUserIdByMobile(@RequestParam("mobile") String mobile,@RequestParam("username")  String username) {
		return this.userService.getUserMapByMobile(mobile,username);
	}

}
