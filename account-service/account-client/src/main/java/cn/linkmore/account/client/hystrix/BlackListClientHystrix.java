package cn.linkmore.account.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.account.client.BlackListClient;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
/**
 * 用户数据录入--容错
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Component
public class BlackListClientHystrix implements BlackListClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public Boolean status() {
		log.info("account service  BlackList Boolean status() hystrix");
		return null;
	}
	@Override
	public ViewMsg open() {
		log.info("account service  ViewMsg ViewMsg open() hystrix");
		return null;
	}
	@Override
	public ViewMsg close() {
		log.info("account service  ViewMsg ViewMsg close() hystrix");
		return null;
	}
	@Override
	public ViewMsg enable(List<Long> list) {
		log.info("account service  ViewMsg enable(List<Long> list) hystrix");
		return null;
	}
	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("account service  ViewMsg list(ViewPageable pageable) hystrix");
		return null;
	}

	
}
