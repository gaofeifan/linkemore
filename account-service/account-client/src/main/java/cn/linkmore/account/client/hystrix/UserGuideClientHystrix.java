package cn.linkmore.account.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.account.client.UserGuideClient;
import cn.linkmore.account.response.ResUserGuide;
/**
 * 用户指南--熔断
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Component
public class UserGuideClientHystrix implements UserGuideClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	
	@Override
	public List<ResUserGuide> list(String language) {
		log.info("account service ResUserGuide list(String language) hystrix");
		return null;
	}

	
	
}
