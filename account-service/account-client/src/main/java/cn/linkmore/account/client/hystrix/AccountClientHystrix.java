package cn.linkmore.account.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.account.client.AccountClient;
import cn.linkmore.account.client.CustomerInfoClient;
import cn.linkmore.account.request.ReqCustomerInfoExport;
import cn.linkmore.account.response.ResCustomerInfoExport;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
/**
 * 用户数据录入--容错
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Component
public class AccountClientHystrix implements AccountClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("account service ViewPage list(ViewPageable pageable) hystrix");
		return null;
	}

	
	
}
