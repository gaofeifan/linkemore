package cn.linkmore.prefecture.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqAddEntPreture;
import cn.linkmore.prefecture.client.PrefectrueClient;
/**
 * 熔断企业车区
 * @author   GFF
 * @Date     2018年7月27日
 * @Version  v2.0
 */
@Component
public class PrefectrueClientHystrix implements PrefectrueClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		log.info("ops staffauth service ViewPage findPage(ViewPageable pageable) hystrix");
		return null;
	}
	@Override
	public void save(ReqAddEntPreture auth) {
		log.info("ops staffauth service void save(ReqAddEntPreture auth) hystrix");
	}
	@Override
	public void update(ReqAddEntPreture auth) {
		log.info("ops staffauth servicevoid update(ReqAddEntPreture auth) hystrix");
	}
	@Override
	public void delete(List<Long> ids) {
		log.info("ops staffauth servicevoid delete(List<Long> ids) hystrix");
		
	}

	
}
