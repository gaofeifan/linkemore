package cn.linkmore.account.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.account.client.WhitelistClient;
import cn.linkmore.account.request.ReqCheck;
import cn.linkmore.account.request.ReqWhitelist;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
/**
 * 权限管理--类管理--熔断
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Component
public class WhitelistClientHystrix implements WhitelistClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void save(ReqWhitelist record) {
		log.info("account service Whitelist save(ReqWhitelist record) hystrix");
	}

	@Override
	public void update(ReqWhitelist record) {
		log.info("account service Whitelist update(ReqWhitelist record) hystrix");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(List<Long> ids) {
		log.info("account service Whitelist delete(List<Long> ids) hystrix");
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean check(ReqCheck check) {
		log.info("account service Boolean check(ReqCheck check) hystrix");
		return null;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("account service ViewPage list(ViewPageable pageable) hystrix");
		return null;
	}

}

