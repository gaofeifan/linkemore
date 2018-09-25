package cn.linkmore.common.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.WyAppVersionClient;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.request.ReqWyAppVersion;
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

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		log.info("common service ViewPage findPage(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public void save(ReqWyAppVersion copyObject) {
		log.info("common service void save(ReqWyAppVersion copyObject) hystrix");
	}

	@Override
	public void update(ReqWyAppVersion copyObject) {
		log.info("common service void update(ReqWyAppVersion copyObject) hystrix");
	}

	@Override
	public void delete(List<Long> ids) {
		log.info("common service void delete(List<Long> ids) hystrix");
	}

	@Override
	public Boolean check(ReqCheck check) {
		log.info("common service  Boolean check(ReqCheck check) hystrix");
		return null;
	}

	

}
