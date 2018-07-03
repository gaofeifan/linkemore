package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqEnterpriseUser;
import cn.linkmore.enterprise.response.ResEnterpriseUser;
import cn.linkmore.prefecture.client.EnterpriseUserClient;
/**
 * @author   GFF
 * @Date     2018年6月25日2
 * @Version  v2.0
 */

@Component
public class EnterpriseUserClientHystrix implements EnterpriseUserClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service ViewPage list(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public int update(ReqEnterpriseUser enterpriseUser) {
		log.info("prefecture service int update(ReqEnterpriseUser enterpriseUser) hystrix");
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Long id) {
		log.info("prefecture service int delete(Long id) hystrix");
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ResEnterpriseUser> export(String userName) {
		log.info("prefecture service List<ResEnterpriseUser> export(String userName) hystrix");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResEnterpriseUser getEnterpriseUser(Long id) {
		log.info("prefecture service ResEnterpriseUser getEnterpriseUser(Long id) hystrix");
		return null;
	}

	@Override
	public List<ResEnterpriseUser> findExcel(Map<String, Object> param) {
		log.info("prefecture service List<ResEnterpriseUser> findExcel(Map<String, Object> param) hystrix");
		return null;
	}

	@Override
	public void saveAll(List<ReqEnterpriseUser> entAll) {
		log.info("prefecture service saveAll(List<ReqEnterpriseUser> entAll) hystrix");
	}
	
	
	
	

}

