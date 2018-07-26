package cn.linkmore.prefecture.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEntBrandPre;
import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.prefecture.client.OpsEntBrandPreClient;
/**
 * 远程调用实现 - 企业品牌车区信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class OpsEntBrandPreClientHystrix implements OpsEntBrandPreClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResBrandPre findById(Long id) {
		log.info("enterprise service ResBrandPre findById() hystrix");
		return null;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("enterprise service ViewPage list() hystrix");
		return null;
	}

	@Override
	public int save(ReqEntBrandPre reqEntBrandPre) {
		log.info("enterprise service int save(reqEntBrandPre) hystrix");
		return 0;
	}

	@Override
	public int update(ReqEntBrandPre reqEntBrandPre) {
		log.info("enterprise service int update(reqEntBrandPre) hystrix");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("enterprise service int delete(ids) hystrix");
		return 0;
	}
	
}

