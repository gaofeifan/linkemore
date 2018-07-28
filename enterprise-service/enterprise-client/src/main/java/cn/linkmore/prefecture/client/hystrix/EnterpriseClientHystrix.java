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
import cn.linkmore.enterprise.request.ReqEnterprise;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.prefecture.client.EnterpriseClient;
/**
 * 远程调用实现 - 锁操作日志信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class EnterpriseClientHystrix implements EnterpriseClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service ViewPage list(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public int save(ReqEnterprise record) {
		log.info("prefecture service int save(ReqEnterprise record) hystrix");
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ReqEnterprise record) {
		log.info("prefecture service int update(ReqEnterprise record) hystrix");
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
	public Boolean check(ReqCheck reqCheck) {
		log.info("prefecture service Boolean check(ReqCheck reqCheck) hystrix");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResEnterprise> selectAll() {
		log.info("prefecture service Object selectAll() hystrix");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResEnterprise findName(Map<String, Object> param) {
		log.info("prefecture service ResEnterprise find() hystrix");
		return null;
	}
	
}

