package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqOperateAuth;
import cn.linkmore.enterprise.request.ReqOperateBind;
import cn.linkmore.prefecture.client.OpsOperateAuthClient;
/**
 * 操作权限
 * @author   GFF
 * @Date     2018年7月27日
 * @Version  v2.0
 */
@Component
public class OpsOperateAuthClientHystrix implements OpsOperateAuthClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public void bind(ReqOperateBind bind) {
		log.info("operate service void bind(ReqOperateBind bind) hystrix");		
	}

	@Override
	public void save(ReqOperateAuth operateAuth) {
		log.info("operate service void save(ReqOperateAuth operateAuth) hystrix");		
	}

	@Override
	public void update(ReqOperateAuth operateAuth) {
		log.info("operate service void update(ReqOperateAuth operateAuth) hystrix");		
	}

	@Override
	public void stop(Long id) {
		log.info("operate service void stop(Long id) hystrix");		
	}

	@Override
	public void start(Long id) {
		log.info("operate service void start(Long id) hystrix");		
	}

	@Override
	public void delete(Long id) {
		log.info("operate service void delete(Long id) hystrix");		
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("operate service ViewPage list(ViewPageable pageable) hystrix");		
		return null;
	}


	@Override
	public Map<String, Object> resource(Long id) {
		log.info("operate service Map<String, Object> resource(Long id)hystrix");		
		return null;
	}

	@Override
	public List<Tree> tree(Long entId) {
		log.info("operate service Map<String, Object> tree(Long entId)hystrix");		
		return null;
	}


}
