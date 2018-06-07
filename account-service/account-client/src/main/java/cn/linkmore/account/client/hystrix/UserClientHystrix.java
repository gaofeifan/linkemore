package cn.linkmore.account.client.hystrix;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.request.ReqBind;
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
 
	public ResUserLogin wxLogin(@RequestBody ReqUserAppfans appfans) {
		log.info("account service user wxLogin(  ReqUserAppfans appfans) hystrix");
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
	
	
	
}

