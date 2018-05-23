package cn.linkmore.common.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.common.client.UnusualLogClient;
import cn.linkmore.common.request.ReqUnusualLog;

/**
 * 数据词典容错
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@Component
public class UnusualLogClientHystrix implements UnusualLogClient {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void insert(ReqUnusualLog unusualLog) {
		log.info("common service unusuallog insert(ReqUnusualLog unusualLog) hystrix");
	}

	
	
}
