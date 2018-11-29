package cn.linkmore.prefecture.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentEntUser;
import cn.linkmore.prefecture.client.OpsRentEntUserClient;
@Component
public class OpsRentEntUserClientHystrix implements OpsRentEntUserClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("=======Hystrix==========ViewPage list(ViewPageable pageable) ");
		return null;
	}

	@Override
	public void save(ReqRentEntUser ent) {
		log.info("=======Hystrix==========void save(ReqRentEnt ent) ");
	}

	@Override
	public void update(ReqRentEntUser ent) {
		log.info("=======Hystrix==========void update(ReqRentEntUser ent) ");
	}

	@Override
	public void delete(List<Long> ids) {
		log.info("=======Hystrix==========void delete(List<Long> ids) ");
	}
	
	
}
