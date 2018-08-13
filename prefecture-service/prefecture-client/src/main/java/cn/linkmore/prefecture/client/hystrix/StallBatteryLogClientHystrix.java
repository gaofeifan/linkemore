package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.prefecture.client.StallBatteryLogClient;
import cn.linkmore.prefecture.response.ResStallBatteryLog;
/**
 * 远程调用实现 - 车位电池日志
 * @author jiaohanbin
 * @version 2.0
 */ 
@Component
public class StallBatteryLogClientHystrix implements StallBatteryLogClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<ResStallBatteryLog> findBatteryLogList(Long stallId) {
		log.info("prefecture service List<ResStallBatteryLog> findBatteryLogList(stallId) hystrix");
		return null;
	}

	@Override
	public void save(ResStallBatteryLog sbl) {
		log.info("prefecture service void save(ResStallBatteryLog sbl) hystrix");
		
	}
	
	
}
