package cn.linkmore.security.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.client.PersonClient;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqPerson;
import cn.linkmore.security.response.ResPersonRole;
import cn.linkmore.security.response.ResRole;
/**
 * 远程调用实现 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class PersonClientHystrix implements PersonClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void save(ReqPerson person) {
		log.info("person save");
	}

	@Override
	public void update(ReqPerson person) {
		log.info("person update");
	}

	@Override
	public void delete(List<Long> ids) {
		log.info("person delete");
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("person check");
		return false;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("person list");
		return null;
	}

	@Override
	public void unlock(Long id) {
		log.info("person unlock");
	}

	@Override
	public List<ResRole> roleList() {
		log.info("person roleList");
		return null;
	}

	@Override
	public void bind(Long id, String ids) {
		log.info("person bind");
	}

	@Override
	public List<ResPersonRole> personRolList(Long id) {
		log.info("person personRolList");
		return null;
	}

}
