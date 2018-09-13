package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqAddEntPreture;
import cn.linkmore.enterprise.response.ResEntPrefecture;
import cn.linkmore.prefecture.client.PrefectrueClient;
import cn.linkmore.prefecture.response.ResPreList;
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
	
	@Override
	public List<ResPreList> findNotCreateEntPre() {
		log.info("ops staffauth service List<ResEntPrefecture> findNotCreateEntPre() hystrix");
		return null;
	}
	@Override
	public List<ResEntPrefecture> findAll(Map<String, Object> map) {
		log.info("ops staffauth service List<ResEntPrefecture> findAll() hystrix");
		return null;
	}
	
	

	
}
