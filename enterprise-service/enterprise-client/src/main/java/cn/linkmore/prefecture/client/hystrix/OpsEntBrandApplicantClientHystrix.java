package cn.linkmore.prefecture.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.OpsEntBrandApplicantClient;
/**
 * 远程调用实现 - 企业品牌广告信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class OpsEntBrandApplicantClientHystrix implements OpsEntBrandApplicantClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("enterprise service ViewPage list() hystrix");
		return null;
	}
}

