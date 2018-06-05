package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.TargetSettingClient;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqTargetSetting;
import cn.linkmore.prefecture.response.ResPreList;
/**
 * 远程调用实现 - 锁操作日志信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class TargetSettingClientHystrix implements TargetSettingClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqTargetSetting reqTargetSetting) {
		log.info("target setting save hystrix");
		return 0;
	}

	@Override
	public int update(ReqTargetSetting reqTargetSetting) {
		log.info("target setting update hystrix");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("target setting delete hystrix");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("target setting check hystrix");
		return null;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("target setting list hystrix");
		return null;
	}

	@Override
	public List<ResPreList> prefectureList() {
		log.info("target setting prefectureList hystrix");
		return null;
	}
	
}
