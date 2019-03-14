package cn.linkmore.account.client.hystrix;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.request.ReqBind;
import cn.linkmore.account.request.ReqUpdateAccount;
import cn.linkmore.account.request.ReqUpdateMobile;
import cn.linkmore.account.request.ReqUpdateNickname;
import cn.linkmore.account.request.ReqUpdateSex;
import cn.linkmore.account.request.ReqUpdateVehicle;
import cn.linkmore.account.request.ReqUserAppfans;
import cn.linkmore.account.response.ResPageUser;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.response.ResUserLogin;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * @author   GFF
 * @Date     2018年5月22日
 * @Version  v2.0
 */
@Component
public class UserClientHystrix implements UserClient{
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	 
	public void updateNickname(@RequestBody ReqUpdateNickname nickname) {
		log.info("account service user updateNickname(ReqUpdateNickname nickname) hystrix");
	};
	
	 
	public void updateSex(@RequestBody ReqUpdateSex reqSex) {
		log.info("account service user updateSex(ReqUpdateSex reqSex) hystrix");
	}
	
	 
	public void updateVehicle(@RequestBody ReqUpdateVehicle req) {
		log.info("account service user updateVehicle(ReqUpdateVehicle req) hystrix");
	}
	
	 
	public ResUserDetails detail(@PathVariable("userId") Long userId) {
		log.info("account service user detail(Long userId) hystrix");
		return null;
	}
	
	 
	public void sendCode( @RequestBody ReqBind bean) {
		log.info("account service user sendCode(ReqBind bean) hystrix");
	}

	 
	public void updateMobile(@RequestBody ReqUpdateMobile bean) {
		log.info("account service user updateMobile( ReqUpdateMobile bean) hystrix");
	}
	
	 
	public void updateAppfans(@RequestBody ReqUserAppfans bean) {
		log.info("account service user updateAppfans( ReqUserAppfans bean) hystrix");
	} 
	
	 
	public void removeWechat(@PathVariable("userId") Long userId) { 
		log.info("account service user removeWechat( Long userId) hystrix");
	}
	
	 
	public ResUser getUserCacheKey(@PathVariable("userId") Long userId) {
		log.info("account service user getUserCacheKey(userId) hystrix" + userId);
		return null;
	}

	public ResUserLogin appLogin(@PathVariable("mobile") String mobile) {
		log.info("account service user appLogin( String mobile) hystrix");
		return null;
	}
  
	@Override
	public ResUser findById(Long id) {
		log.info("account service user findById(Long id) hystrix");
		return null;
	}

	@Override
	public void order(@RequestParam("id")Long id) {
		 log.info("account service user order(Long id) hystrix");
	}
	
	@Override
	public void checkout(@RequestParam("id")Long id) {
		log.info("account service user checkout(Long id) hystrix");
	}

	@Override
	public ViewPage findPage(@RequestBody ViewPageable pageable) {
		log.info("account service ViewPage findPage(ViewPageable pageable) hystrix"); 
		return null;
	}


	@Override
	public List<ResPageUser> export(ViewPageable pageable) {
		log.info("account service List<ResPageUser> export(ViewPageable pageable) hystrix"); 
		return null;
	}

	@Override
	public void updateAccountName(ReqUpdateAccount account) {
		log.info("account service updateAccountName(ReqUpdateAccount account) hystrix"); 
	}


	@Override
	public ResUser getUserByUserName(String userName) {
		log.info("account service getUserByUserName(userName) hystrix"); 
		return new ResUser();
	}


	@Override
	public ResUser getUserByUserName(Map<String, Object> param) {
		log.info("account service ResUser getUserByUserName(Map<String, Object> param) hystrix"); 
		return null;
	}


	@Override
	public ResUser save(ResUser user) {
		log.info("account service ResUser save(ResUser user) hystrix"); 
		return null;
	}
	

	@RequestMapping(value = "/by-mobile", method = RequestMethod.GET)
	@ResponseBody
	public Long getUserIdByMobile(@RequestParam("mobile") String mobile) {
		log.info("account service ResUser getUserByUserName(Map<String, Object> param) hystrix");
		return null;
	}
	
	@RequestMapping(value = "/by-mobile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Long> getUserMapByMobile(@RequestBody List<String> mobile) {
		log.info("account service ResUser getUserByUserName(Map<String, Object> param) hystrix");
		return null;
	}

	@RequestMapping(value = "/by-all", method = RequestMethod.GET)
	@ResponseBody
	public List<ResUser> findAll() {
		log.info("account service List<ResUser> findAll() hystrix");
		return null;
	}


	@Override
	public int delete(List<Long> ids) {
		log.info("account service int delete(List<Long> ids) hystrix");
		return 0;
	}


	@Override
	public Object reset(List<Long> ids, String passwrod) {
		log.info("account service int reset(List<Long> ids, String passwrod) hystrix");
		return null;
	}
	
	
	
}

