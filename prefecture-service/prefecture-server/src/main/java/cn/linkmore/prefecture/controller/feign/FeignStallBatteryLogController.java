package cn.linkmore.prefecture.controller.feign;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.prefecture.service.StallBatteryLogService;

/**
 * Controller - 车位电池日志
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/feign/stall-battery-log")
public class FeignStallBatteryLogController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StallBatteryLogService stallBatteryLogService;
	
	@RequestMapping(value = "/v2.0/battery-log", method = RequestMethod.GET)
	@ResponseBody
	public List<ResStallBatteryLog> findBatteryLogList(@RequestParam("stallId")Long stallId) {
		log.info("find battery log list by stallId:{}",stallId);
		return this.stallBatteryLogService.findBatteryLogList(stallId);
	}
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	public void save(@RequestBody ResStallBatteryLog sbl) {
		this.stallBatteryLogService.save(sbl);
	}
}
