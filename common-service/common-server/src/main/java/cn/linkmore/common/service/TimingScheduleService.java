package cn.linkmore.common.service;
import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.entity.TimingSchedule;
import cn.linkmore.common.request.ReqTimingSchedule;

/**
 * @Description  
 * @author  GFF
 * @Date     2018年3月16日
 *
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
	 * @Description  删除任务调度
	 * @Author   GFF 
	 * @Date       2018年3月16日
	 * @Param    TimingScheduleMapper
	 * @Return   void
	 */
	void delete(Long id);
	
	/**
	 * @Description  查询任务调度
	 * @Author   GFF 
	 * @Date       2018年3月16日
	 * @Param    TimingScheduleMapper
	 * @Return   void
	 */
	List<TimingSchedule> list(TimingSchedule timingSchedule);
	

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
	Integer check(String property, String value, Long id);


	/**
	 * @Description  批量删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteIds(List<Long> ids);


}
