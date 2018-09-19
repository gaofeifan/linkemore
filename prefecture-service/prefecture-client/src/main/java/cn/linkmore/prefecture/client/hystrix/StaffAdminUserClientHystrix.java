package cn.linkmore.prefecture.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.prefecture.client.StaffAdminUserClient;
import cn.linkmore.prefecture.response.ResAdmin;
import cn.linkmore.prefecture.response.ResAdminUser;
@Component
public class StaffAdminUserClientHystrix implements StaffAdminUserClient {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public ResAdminUser findMobile(String mobile) {
		log.info("ResAdminUser findMobile(String mobile) hystrix");
		return null;
	}
	@Override
	public void updateLoginTime(Long id) {
		log.info(" void updateLoginTime(Map<String, Object> map) hystrix");
	}
	@Override
	public ResAdmin authLogin(String mobile) {
		log.info(" ResAdminUser authLogin(String mobile) hystrix");
		return null;
	}
	@Override
	public ResAdminUser findById(Long id) {
		log.info(" ResAdminUser findById(Long id) hystrix");
		return null;
	}
	
	
	

}
