package cn.linkmore.ops.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.TimingScheduleClient;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.ops.request.ReqTimingSchedule;
import cn.linkmore.ops.service.TimingScheduleService;
import cn.linkmore.util.ObjectUtils;

/**
 * 定时调度实现
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@Service
public class TimingScheduleServiceImpl implements TimingScheduleService {

	@Resource
	private TimingScheduleClient timingScheduleClient;
	
	@Override
	public void save(ReqTimingSchedule timingSchedule) {
		cn.linkmore.common.request.ReqTimingSchedule schedule = ObjectUtils.copyObject(timingSchedule,new cn.linkmore.common.request.ReqTimingSchedule());
		this.timingScheduleClient.add(schedule);
	}

	@Override
	public void update(ReqTimingSchedule timingSchedule) {
		cn.linkmore.common.request.ReqTimingSchedule schedule = ObjectUtils.copyObject(timingSchedule,new cn.linkmore.common.request.ReqTimingSchedule());
		this.timingScheduleClient.update(schedule);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		ViewPage page = this.timingScheduleClient.list(pageable);
		return page;
	}

	@Override
	public Boolean check(cn.linkmore.ops.request.ReqCheck check) {
		ReqCheck object = ObjectUtils.copyObject(check, new ReqCheck());
		Boolean flag = this.timingScheduleClient.check(object );
		return flag;
	}

	@Override
	public void deleteIds(List<Long> ids) {
		this.timingScheduleClient.delete(ids);
	}

	
}
