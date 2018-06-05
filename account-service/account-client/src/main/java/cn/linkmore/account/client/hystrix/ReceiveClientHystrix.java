package cn.linkmore.account.client.hystrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.account.client.ReceiveClient;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 分享领劵实现
 * @author   GFF
 * @Date     2018年5月22日
 * @Version  v2.0
 */
@Component
public class ReceiveClientHystrix implements ReceiveClient{
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("account service receive list(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public ViewPage detailList(ViewPageable pageable) {
		log.info("account service receive detailList(ViewPageable pageable) hystrix");
		return null;
	}

	
}

