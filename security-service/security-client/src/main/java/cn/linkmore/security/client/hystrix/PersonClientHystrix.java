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
import cn.linkmore.security.request.ReqPrincipal;
import cn.linkmore.security.response.ResPerson;
import cn.linkmore.security.response.ResPersonRole;
import cn.linkmore.security.response.ResRole;
/**
 * 远程调用实现 - 账户信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class PersonClientHystrix implements PersonClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqPerson person) {
		log.info("person save");
		return 0;
	}

	@Override
	public int update(ReqPerson person) {
		log.info("person update");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("person delete");
		return 0;
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

	@Override
	public ResPerson findByUsername(String username) {
		log.info("person findByUsername");
		return null;
	}

	@Override
	public List<String> findAuthList(ReqPrincipal principal) {
		log.info("person findAuthList");
		return null;
	}

	@Override
	public void updatePassword(ReqPerson person, String oldPassword, String password) {
		log.info("person updatePassword");
	}

	@Override
	public ResPerson findById(Long id) {
		log.info("person findById");
		return null;
	}

	@Override
	public void updatePassword(ReqPerson person) {
		log.info("person updatePassword");
	}

}
