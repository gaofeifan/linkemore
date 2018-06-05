package cn.linkmore.ops.base.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqTimingSchedule;

/**
 * 定时调度接口
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
public interface TimingScheduleService {
	/**
	 * @Description   添加任务调度
	 * @Author   GFF 
	 * @Date       2018年3月16日
	 * @Param    TimingScheduleService
	 * @Return   void
	 */
	void save(ReqTimingSchedule timingSchedule);
	
	/**
	 * @Description  修改任务调度
	 * @Author   GFF 
	 * @Date       2018年3月16日
	 * @Param    TimingScheduleMapper
	 * @Return   void
	 */
	void update(ReqTimingSchedule timingSchedule);
	
	/**
	 * 	根据条件查询
	 * @Description  
	 * @Author   GFF 
	 * @Date       2018年3月17日
	 * @Param    TimingScheduleService
	 * @Return   ViewMsg
	 */
	ViewPage findPage(ViewPageable pageable);


	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Date       2018年3月17日
	 * @Param    TimingScheduleService
	 * @Return   Integer
	 */
	Boolean check(String property, String value, Long id);

	/**
	 * @Description  批量删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteIds(List<Long> ids);

}
