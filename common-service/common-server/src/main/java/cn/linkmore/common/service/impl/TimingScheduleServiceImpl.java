package cn.linkmore.common.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.config.DefaultSchedulingConfigurer;
import cn.linkmore.common.dao.cluster.TimingScheduleClusterMapper;
import cn.linkmore.common.dao.master.TimingScheduleMasterMapper;
import cn.linkmore.common.entity.TimingSchedule;
import cn.linkmore.common.request.ReqTimingSchedule;
import cn.linkmore.common.service.TimingScheduleService;
import cn.linkmore.util.DomainUtil;

/**
 * @Description 定时调度的实现
 * @author GFF
 * @Date 2018年3月16日
 *
 */
@Service
public class TimingScheduleServiceImpl implements TimingScheduleService {

//	@Autowired
//	private DefaultSchedulingConfigurer defaultSchedulingConfigurer;
	@Autowired
	private TimingScheduleClusterMapper timingScheduleMapper;
	@Autowired
	private TimingScheduleMasterMapper timingScheduleMasterMapper;

	@Override
	public void save(ReqTimingSchedule timingSchedule) {
		this.timingScheduleMasterMapper.saveReq(timingSchedule);
		// 新增定时任务 为启动状态时添加定时任务
//		if (timingSchedule.getStatus() == 1) {
//			defaultSchedulingConfigurer.addTriggerTask(timingSchedule);
//		}
	}

	@Override
	public void update(ReqTimingSchedule timingSchedule) {
		this.timingScheduleMasterMapper.updateReq(timingSchedule);
		// 修改定时任务 为启动状态时重置任务 为关闭状态时 取消之前的任务
//		if (timingSchedule.getStatus() == 1) {
//			defaultSchedulingConfigurer.resetTriggerTask(timingSchedule);
//		} else {
//			defaultSchedulingConfigurer.cancelTriggerTask(timingSchedule.getId() + "");
//		}
	}

	@Override
	public void deleteIds(List<Long> ids) {
		this.timingScheduleMasterMapper.deleteIds(ids);
//		for (Long id : ids) {
//			// 删除时 取消任务
//			defaultSchedulingConfigurer.cancelTriggerTask(id.toString());
//		}
	}

	@Override
	public List<TimingSchedule> list(TimingSchedule timingSchedule) {
		return this.timingScheduleMapper.list(timingSchedule);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<ViewFilter> filters = pageable.getFilters();
		if (StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if (filters != null && filters.size() > 0) {
			for (ViewFilter filter : filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if (StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.timingScheduleMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<TimingSchedule> list = this.timingScheduleMapper.findPage(param);
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public Integer check(String property, String value, Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", property);
		param.put("value", value);
		param.put("id", id);
		return this.timingScheduleMapper.check(param);
	}

	@Override
	public void delete(Long id) {
		this.timingScheduleMasterMapper.deleteById(id);
		// 删除时 取消任务
//		defaultSchedulingConfigurer.cancelTriggerTask(id.toString());
	}

}
