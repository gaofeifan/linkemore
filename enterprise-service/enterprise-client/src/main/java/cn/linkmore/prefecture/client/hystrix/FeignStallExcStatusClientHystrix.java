package cn.linkmore.prefecture.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.enterprise.response.ResEntExcStallStatus;
import cn.linkmore.prefecture.client.FeignStallExcStatusClient;
@Component
public class FeignStallExcStatusClientHystrix implements FeignStallExcStatusClient {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<ResEntExcStallStatus> findAll() {
		log.info("=======Hystrix==========List<ResEntExcStallStatus> findAll()");
		return null;
	}
	
	
}
