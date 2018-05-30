package cn.linkmore.ops.controller;
import java.util.List
;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqTimingSchedule;
import cn.linkmore.ops.service.TimingScheduleService;
import cn.linkmore.util.CronUtils;
/**
 * @Description   定时调度任务
 * @author  GFF
 * @Date     2018年3月16日
 *
 */
@RestController
@RequestMapping("/task")
public class TimingScheduleController {
	
	@Autowired
	private TimingScheduleService timingScheduleService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> add(@RequestBody ReqTimingSchedule timingSchedule,HttpServletRequest request){
		this.timingScheduleService.save(timingSchedule);
		return ResponseEntity.success(null, request);
	} 
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> update(@RequestBody ReqTimingSchedule timingSchedule,HttpServletRequest request){
		this.timingScheduleService.update(timingSchedule);
		return ResponseEntity.success(null, request);
	} 
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ViewPage> list(@RequestBody ViewPageable pageable,HttpServletRequest request){
		ViewPage page = this.timingScheduleService.findPage(pageable);
		return ResponseEntity.success(page, request);
	} 
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> delete(@RequestBody List<Long> ids,HttpServletRequest request){
		this.timingScheduleService.deleteIds(ids);
		return ResponseEntity.success(null, request);
	} 
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> check(@RequestBody ReqCheck check,HttpServletRequest request){
		Boolean flag = this.timingScheduleService.check(check);
		return ResponseEntity.success(flag, request);
	}
	
	@RequestMapping(value = "/check_ron/{cron}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Boolean> checkCron(@PathVariable("cron") String cron,HttpServletRequest request){
		try {
			new CronUtils(cron, null).checkCron(cron);
			return ResponseEntity.success(true, request);
		} catch (Exception e) {
			return ResponseEntity.success(true, request);
		}
	}
	
}
