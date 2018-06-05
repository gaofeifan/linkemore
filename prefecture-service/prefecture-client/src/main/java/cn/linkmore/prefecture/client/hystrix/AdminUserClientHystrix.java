package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.AdminUserClient;
import cn.linkmore.prefecture.request.ReqAdminUser;
import cn.linkmore.prefecture.request.ReqCheck;
/**
 * 远程调用实现 - 管理员
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class AdminUserClientHystrix implements AdminUserClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("prefecture service admin_user list() hystrix");
		return null;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("prefecture service admin_user check() hystrix");
		return null;
	}

	@Override
	public int save(ReqAdminUser admin) {
		log.info("prefecture service admin_user save() hystrix");
		return 0;
	}

	@Override
	public int update(ReqAdminUser admin) {
		log.info("prefecture service admin_user update() hystrix");
		return 0;
	}

	@Override
	public Tree tree() {
		log.info("prefecture service admin_user tree() hystrix");
		return null;
	}

	@Override
	public Map<String, Object> resource(Long id) {
		log.info("prefecture service admin_user resource() hystrix");
		return null;
	}

	@Override
	public void bind(Long id, String authids) {
		log.info("prefecture service admin_user bind() hystrix");
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("prefecture service admin_user delete() hystrix");
		return 0;
	}
}
