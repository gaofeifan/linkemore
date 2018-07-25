package cn.linkmore.prefecture.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandUser;
import cn.linkmore.prefecture.client.OpsEntBrandUserClient;
/**
 * 远程调用实现 - 品牌授权用户
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class OpsEntBrandUserClientHystrix implements OpsEntBrandUserClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("enterprise service ViewPage list() hystrix");
		return null;
	}

	@Override
	public int save(ReqEntBrandUser reqEntBrandUser) {
		log.info("enterprise service int save() hystrix");
		return 0;
	}

	@Override
	public int update(ReqEntBrandUser reqEntBrandUser) {
		log.info("enterprise service int update() hystrix");
		return 0;
	}

	@Override
	public int delete(Long id) {
		log.info("enterprise service int delete() hystrix");
		return 0;
	}
}

