package cn.linkmore.common.client.hystrix;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.TimingScheduleClient;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqTimingSchedule;

/**
 * 接口访问实现
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@Component
public class TimingScheduleClientHystrix implements TimingScheduleClient {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void add(ReqTimingSchedule timingSchedule) {
		log.info("common service TimingSchedule add(ReqTimingSchedule timingSchedule) hystrix");
	}

	@Override
	public void update(ReqTimingSchedule timingSchedule) {
		log.info("common service TimingSchedule add(ReqTimingSchedule timingSchedule) hystrix");
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("common service TimingSchedule list(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public void delete(List<Long> ids) {
		log.info("common service TimingSchedule delete(List<Long> ids) hystrix");
	}

	@Override
	public Boolean check(ReqCheck check) {
		log.info("common service TimingSchedule check(ReqCheck check) hystrix");
		return null;
	}

	@Override
	public Boolean checkCron(String cron) {
		log.info("common service TimingSchedule checkCron(String cron) hystrix");
		return null;
	}
	
}

