package cn.linkmore.common.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.common.client.WyAppVersionClient;
import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.response.ResVersionBean;
import cn.linkmore.common.response.ResWyAppVersion;

/**
 * 物业版版本管理
 * @author   GFF
 * @Date     2018年9月6日
 * @Version  v2.0
 */
@Component
public class WyAppVersionClientHystrix implements WyAppVersionClient {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public ResWyAppVersion current(Integer source) {
		log.info("common service ResVersionBean current(Integer source) hystrix");
		return null;
	}

	@Override
	public void report(ReqVersion vrb) {
		log.info("common service void report(ReqVersion vrb) hystrix");

	}

}
