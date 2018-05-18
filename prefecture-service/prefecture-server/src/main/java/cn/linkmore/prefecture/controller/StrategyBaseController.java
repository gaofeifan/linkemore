package cn.linkmore.prefecture.controller;

import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.prefecture.entity.StrategyBase;
import cn.linkmore.prefecture.service.StrategyBaseService;
import cn.linkmore.prefecture.util.OrderFee;

/**
 * Controller - 计费操作
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/strategy")
public class StrategyBaseController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StrategyBaseService strategyBaseService;

	/**
	 * 根据计费策略和进出时间获取计费信息
	 * 
	 * @param strategyId String
	 * @param beginTime Date
	 * @param endTime Date
	 */
	@RequestMapping(value = "/v2.0/fee", method=RequestMethod.GET)
	public Map<String, Object> fee(@RequestParam("strategyId") Long strategyId, 
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date beginTime,
			@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endTime) {
		StrategyBase strategyBase =  this.strategyBaseService.findById(strategyId);
		Map<String, Object> costMap = OrderFee.getMultipleParkingCost(strategyBase, beginTime, endTime);
		return costMap;
	}
}
