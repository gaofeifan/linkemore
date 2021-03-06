package cn.linkmore.account.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.account.client.UserGuideClient;
import cn.linkmore.account.request.ReqCheck;
import cn.linkmore.account.request.ReqUserGuide;
import cn.linkmore.account.response.ResUserGuide;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
/**
 * 用户指南--熔断
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Component
public class UserGuideClientHystrix implements UserGuideClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	
	@Override
	public List<ResUserGuide> list(String language) {
		log.info("account service ResUserGuide list(String language) hystrix");
		return null;
	}

	@Override
	public void save(ReqUserGuide userGuide) {
		log.info("account service ResUserGuide save(ReqUserGuide userGuide) hystrix");
	}

	@Override
	public void update(ReqUserGuide userGuide) {
		log.info("account service ResUserGuide update(ReqUserGuide userGuide) hystrix");
	}

	@Override
	public void delete(List<Long> ids) {
		log.info("account service ResUserGuide delete(List<Long> ids) hystrix");
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("account service ResUserGuide check(ReqCheck reqCheck) hystrix");
		return null;
	}

	@Override
	public Tree findTree() {
		log.info("account service Tree findTree() hystrix");
		return null;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("account service ResUserGuide list(ViewPageable pageable) hystrix");
		return null;
	}
}
