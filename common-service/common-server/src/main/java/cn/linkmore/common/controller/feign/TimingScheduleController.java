package cn.linkmore.common.controller.feign;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqTimingSchedule;
import cn.linkmore.common.service.TimingScheduleService;
import cn.linkmore.util.CronUtils;
/**
 * @Description   定时调度任务
 * @author  GFF
 * @Date     2018年3月16日
 *
 */
@RestController
@RequestMapping("/feign/task")
public class TimingScheduleController {
	
	@Autowired
	private TimingScheduleService timingScheduleService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void add(@RequestBody ReqTimingSchedule timingSchedule){
		this.timingScheduleService.save(timingSchedule);
	} 
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqTimingSchedule timingSchedule){
		this.timingScheduleService.update(timingSchedule);
	} 
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.timingScheduleService.findPage(pageable); 
	} 
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids){
		this.timingScheduleService.deleteIds(ids);
	} 
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck check){
		Boolean flag = true ;
		Integer count = this.timingScheduleService.check(check.getProperty(), check.getValue(), check.getId()); 
		if(count>0){
            flag = false;
        }
        return flag;
	}
	
	@RequestMapping(value = "/check_ron/{cron}", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkCron(@PathVariable("cron") String cron){
		try {
			new CronUtils(cron, null).checkCron(cron);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
