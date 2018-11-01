package cn.linkmore.security.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.client.RoleClient;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqRole;
import cn.linkmore.security.response.ResRole;
/**
 * 远程调用实现 - 角色信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class RoleClientHystrix implements RoleClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqRole role) {
		log.info("role save");
		return 0;
	}

	@Override
	public int update(ReqRole role) {
		log.info("role update");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("role delete");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("role check");
		return false;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("role list");
		return null;
	}

	@Override
	public Tree tree() {
		log.info("role tree");
		return null;
	}

	@Override
	public Map<String, Object> resource(Long id) {
		log.info("role resource");
		return null;
	}

	@Override
	public void bind(Long id, String pids, String eids) {
		log.info("role bind");
	}

	@Override
	public List<ResRole> findList(Map<String, Object> param) {
		log.info("role findList");
		return null;
	}

	
	
}
