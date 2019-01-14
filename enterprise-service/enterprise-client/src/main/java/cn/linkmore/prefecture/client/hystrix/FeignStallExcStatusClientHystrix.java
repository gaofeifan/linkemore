package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.enterprise.request.ReqStallExcCause;
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

	@Override
	public ResEntExcStallStatus findByStallId(Long stallId) {
		log.info("=======Hystrix==========ResEntExcStallStatus findByStallId(Long stallId");
		return null;
	}

	@Override
	public void updateExcStatus(Map<String, Object> map) {
		log.info("=======Hystrix==========void updateExcStatus(Map<String, Object> map");
		
	}

	@Override
	public void save(ResEntExcStallStatus causes) {
		log.info("=======Hystrix==========void save(ResEntExcStallStatus causes");
	}
	
	
	
}
