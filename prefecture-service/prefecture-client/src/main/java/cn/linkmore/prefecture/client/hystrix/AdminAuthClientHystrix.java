package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.AdminAuthClient;
import cn.linkmore.prefecture.request.ReqAdminAuth;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.response.ResStaffCity;
/**
 * 远程调用实现 - 车位授权
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class AdminAuthClientHystrix implements AdminAuthClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service admin_auth list() hystrix");
		return null;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("prefecture service admin_auth check() hystrix");
		return null;
	}

	@Override
	public int save(ReqAdminAuth admin) {
		log.info("prefecture service admin_auth save() hystrix");
		return 0;
	}

	@Override
	public int update(ReqAdminAuth admin) {
		log.info("prefecture service admin_auth update() hystrix");
		return 0;
	}

	@Override
	public Tree tree() {
		log.info("prefecture service admin_auth tree() hystrix");
		return null;
	}

	@Override
	public Map<String, Object> resource(Long id) {
		log.info("prefecture service admin_auth resource() hystrix");
		return null;
	}

	@Override
	public void bind(Long id, String citys, String pres, String json) {
		log.info("prefecture service admin_auth bind() hystrix");
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("prefecture service admin_auth delete() hystrix");
		return 0;
	}

	@Override
	public List<ResStaffCity> findStaffCitysByAdminId(Long id) {
		log.info("prefecture Map<String, Object> findStaffCitysByUserId(Long id) hystrix");
		return null;
	}
	
	
}
