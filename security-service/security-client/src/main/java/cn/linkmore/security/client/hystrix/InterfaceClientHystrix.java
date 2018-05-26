package cn.linkmore.security.client.hystrix;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.client.InterfaceClient;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqInterface;
import cn.linkmore.security.response.ResInterface;
/**
 * 远程调用实现 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class InterfaceClientHystrix implements InterfaceClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqInterface reqInterface) {
		log.info("interface save");
		return 0;
	}

	@Override
	public int update(ReqInterface reqInterface) {
		log.info("interface update");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("interface delete");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("interface check");
		return false;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("interface list");
		return null;
	}

	@Override
	public Tree tree() {
		log.info("interface tree");
		return null;
	}

	@Override
	public List<ResInterface> findAll() {
		log.info("interface findAll");
		return new ArrayList<ResInterface>();
	}

}
