package cn.linkmore.security.client.hystrix;

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
	public void save(ReqInterface reqInterface) {
		log.info("interface save");
	}

	@Override
	public void update(ReqInterface reqInterface) {
		log.info("interface update");
	}

	@Override
	public void delete(List<Long> ids) {
		log.info("interface delete");
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

}
