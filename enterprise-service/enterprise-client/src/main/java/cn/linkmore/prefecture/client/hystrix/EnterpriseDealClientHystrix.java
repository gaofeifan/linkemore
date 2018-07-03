package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqEnterpriseDeal;
import cn.linkmore.enterprise.response.ResEnterpriseDeal;
import cn.linkmore.prefecture.client.EnterpriseDealClient;
/**
 * 合同管理--熔断
 * @author   GFF
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@Component
public class EnterpriseDealClientHystrix implements EnterpriseDealClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public int save(ReqEnterpriseDeal deal) {
		log.info("prefecture service int save(ReqEnterpriseDeal deal) hystrix");
		return 0;
	}

	@Override
	public int update(ReqEnterpriseDeal deal) {
		log.info("prefecture service int update(ReqEnterpriseDeal deal) hystrix");
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("prefecture service int delete(List<Long> ids) hystrix");
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("prefecture service Boolean check(ReqCheck reqCheck) hystrix");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service ViewPage list(ViewPageable pageable) hystrix");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tree> tree() {
		log.info("prefecture service List<Tree> tree() hystrix");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> map() {
		log.info("prefecture service Map<String, Object> map() hystrix");
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<ResEnterpriseDeal> listByEnterpriseId(Map<String, Object> map) {
		log.info("prefecture service List<ResEnterpriseDeal> listByEnterpriseId(Integer enterpriseId, Integer isCreate) hystrix");
		return null;
	}

	@Override
	public ResEnterpriseDeal selectByDealNumber(String number) {
		log.info("prefecture service ResEnterpriseDeal selectByDealNumber(String number) hystrix");
		return null;
	}

	@Override
	public void updateCreateStatus(Map<String, Object> map) {
		log.info("prefecture service void updateCreateStatus(Map<String, Object> map) hystrix");
	}
	
	

	
}

