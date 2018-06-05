package cn.linkmore.ops.base.controller;
import java.util.List
;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.base.service.TimingScheduleService;
import cn.linkmore.ops.request.ReqTimingSchedule;
import cn.linkmore.util.CronUtils;
/**
 * @Description   定时调度任务
 * @author  GFF
 * @Date     2018年3月16日
 *
 */
@Controller
@RequestMapping("/admin/base/task")
public class TimingScheduleController {
	
	@Autowired
	private TimingScheduleService timingScheduleService;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg add(ReqTimingSchedule timingSchedule){
		ViewMsg msg = null;
		try {
			this.timingScheduleService.save(timingSchedule);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
		
	} 
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqTimingSchedule timingSchedule){
		ViewMsg msg = null;
		try {
			this.timingScheduleService.update(timingSchedule);
			msg = new ViewMsg("更新成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("更新失败",false);
		}
		return msg;
	} 
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.timingScheduleService.findPage(pageable); 
		
	} 
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids){
		ViewMsg msg = null;
		try {
			this.timingScheduleService.deleteIds(ids);
			msg = new ViewMsg("删除成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败",false);
		}
		return msg;
		
		
	} 
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(String property,String value,Long id ){
		return this.timingScheduleService.check(property, value, id); 
	}
	
	@RequestMapping(value = "/checkCron", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkCron(String cron){
		try {
			new CronUtils(cron, null).checkCron(cron);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
