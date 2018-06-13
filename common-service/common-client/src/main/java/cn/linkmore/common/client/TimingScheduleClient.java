package cn.linkmore.common.client;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.hystrix.TimingScheduleClientHystrix;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqTimingSchedule;
import cn.linkmore.feign.FeignConfiguration;
/**
 * 定时调度 -- 远程连接
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@FeignClient(value = "common-server", path = "/feign/task", fallback = TimingScheduleClientHystrix.class, configuration = FeignConfiguration.class)
public interface TimingScheduleClient {

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void add(@RequestBody ReqTimingSchedule timingSchedule);
	
	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqTimingSchedule timingSchedule);
	
	/**
	 * @Description  list
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	/**
	 * @Description  删除(批量)
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids);
	
	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck check);
	
	/**
	 * @Description  校验cron
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/check_cron/{cron}", method = RequestMethod.GET)
	@ResponseBody
	public Boolean checkCron(@PathVariable("cron") String cron);
	
}
