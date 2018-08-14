package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.enterprise.response.ResBrandPreStall;
import cn.linkmore.enterprise.response.ResBrandStall;
import cn.linkmore.prefecture.client.EntBrandPreClient;
/**
 * 远程调用实现 - 企业品牌车区信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class EntBrandPreClientHystrix implements EntBrandPreClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public List<ResBrandPreStall> preStallList() {
		log.info("enterprise service List<ResBrandPreStall> preStallList() hystrix");
		return null;
	}

	@Override
	public ResBrandPre findById(Long id) {
		log.info("enterprise service ResBrandPre findById() hystrix");
		return null;
	}

	@Override
	public List<ResBrandStall> brandStallList(Long id) {
		log.info("enterprise service List<ResBrandStall> brandStallList() hystrix");
		return null;
	}
	
}

