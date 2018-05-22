package cn.linkmore.common.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.common.client.AccessDetailClient;
import cn.linkmore.common.request.ReqAccessDetail;

/**
 * 接口访问实现
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@Component
public class AccessDetailClientHystrix implements AccessDetailClient {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void appSave(ReqAccessDetail accessDetail) {
		log.info("common service AccessDetail  appSave(ReqAccessDetail accessDetail) hystrix");
	}

	@Override
	public void miniSave(ReqAccessDetail accessDetail) {
		log.info("common service AccessDetail  miniSave(ReqAccessDetail accessDetail) hystrix");
	}

	
	
}
