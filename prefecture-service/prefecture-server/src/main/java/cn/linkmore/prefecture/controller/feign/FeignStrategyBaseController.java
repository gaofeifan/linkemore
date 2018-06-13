package cn.linkmore.prefecture.controller.feign;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.prefecture.entity.StrategyBase;
import cn.linkmore.prefecture.fee.OrderFee;
import cn.linkmore.prefecture.request.ReqStrategy;
import cn.linkmore.prefecture.service.StrategyBaseService;

/**
 * Controller - 计费操作
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/feign/strategy")
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
	@RequestMapping(value = "/v2.0/fee", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fee(@RequestBody ReqStrategy reqStrategy) {
		log.info("strategy fee " + reqStrategy.getStrategyId());
		StrategyBase strategyBase =  this.strategyBaseService.findById(reqStrategy.getStrategyId());
		Map<String, Object> costMap = new HashMap<String, Object>();
		if(strategyBase != null) {
			costMap = OrderFee.getMultipleParkingCost(strategyBase, new Date(reqStrategy.getBeginTime()), 
					new Date(reqStrategy.getEndTime()));
		}
		return costMap;
	}
}
