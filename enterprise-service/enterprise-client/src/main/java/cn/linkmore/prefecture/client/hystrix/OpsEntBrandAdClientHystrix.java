package cn.linkmore.prefecture.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandAd;
import cn.linkmore.prefecture.client.OpsEntBrandAdClient;
/**
 * 远程调用实现 - 企业品牌广告信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class OpsEntBrandAdClientHystrix implements OpsEntBrandAdClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("enterprise service ViewPage list() hystrix");
		return null;
	}

	@Override
	public int save(ReqEntBrandAd reqEntBrandAd) {
		log.info("enterprise service int save() hystrix");
		return 0;
	}

	@Override
	public int update(ReqEntBrandAd reqEntBrandAd) {
		log.info("enterprise service int update() hystrix");
		return 0;
	}

	@Override
	public int delete(Long id) {
		log.info("enterprise service int delete() hystrix");
		return 0;
	}
}

