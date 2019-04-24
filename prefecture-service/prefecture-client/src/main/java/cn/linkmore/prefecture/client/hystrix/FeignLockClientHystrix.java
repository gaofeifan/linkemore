package cn.linkmore.prefecture.client.hystrix;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import cn.linkmore.prefecture.client.FeignLockClient;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.prefecture.response.ResLockInfos;
import cn.linkmore.prefecture.response.ResLockMessage;
/**
 * 锁服务熔断
 * @author   GFF
 * @Date     2018年11月19日
 * @Version  v2.0
 */
@Component
public class FeignLockClientHystrix implements FeignLockClient {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(FeignLockClientHystrix.class);
	
	@Override
	public ResLockInfo lockInfo(String sn) {
		log.info("Hystrix ResLockInfo lockInfo(String sn)");
		return null;
	}

	@Override
	public List<ResLockInfo> lockList(String groupCode) {
		log.info("Hystrix List<ResLockInfo> lockList(String groupCode)");
		return null;
	}

	@Override
	public ResLockMessage downLockMes(String sn) {
		log.info("Hystrix ResLockMessage downLockMes(String sn)");
		return null;
	}

	@Override
	public Boolean downLock(String sn) {
		log.info("Hystrix Boolean downLock(String sn)");
		return null;
	}

	@Override
	public Boolean upLock(String sn) {
		log.info("Hystrix Boolean upLock(String sn)");
		return null;
	}

	@Override
	public ResLockMessage upLockMes(String sn) {
		log.info("Hystrix ResLockMessage upLockMes(String sn) ");
		return null;
	}

	@Override
	public List<ResLockInfos> lockLists(List<String> lockSns) {
		log.info("Hystrix ResLockMessage lockLists(List<String> lockSns) ");
		return new ArrayList<ResLockInfos>();
	}
	
	

}
