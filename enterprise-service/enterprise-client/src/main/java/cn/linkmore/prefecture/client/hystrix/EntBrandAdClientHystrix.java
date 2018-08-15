package cn.linkmore.prefecture.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.enterprise.response.ResBrandAd;
import cn.linkmore.prefecture.client.EntBrandAdClient;
/**
 * 远程调用实现 - 企业品牌广告信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class EntBrandAdClientHystrix implements EntBrandAdClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResBrandAd findByEntId(Long entId) {
		log.info("enterprise service ResBrandAd findByEntId() hystrix");
		return null;
	}
	
}

