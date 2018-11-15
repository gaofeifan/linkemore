package cn.linkmore.prefecture.controller.feign;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.prefecture.request.ReqStrategy;
import cn.linkmore.prefecture.service.StrategyFeeService;

@RestController
@RequestMapping("/feign/strategy/fee")
public class FeignStrategyFeeController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StrategyFeeService strategyFeeService;
	
	/**
	 * 计算总金额
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/amount", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> amount(@RequestBody Map<String, Object> param) {
		return strategyFeeService.amount(param);
	}
	
	
	/**
	 * 获取收费策略信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/info", method=RequestMethod.POST)
	@ResponseBody
	public String info(@RequestBody Map<String, Object> param) {
		return strategyFeeService.info(param);
	}
	
}
