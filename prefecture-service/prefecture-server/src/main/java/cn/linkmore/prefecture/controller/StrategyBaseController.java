package cn.linkmore.prefecture.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.entity.StrategyBase;
import cn.linkmore.prefecture.fee.OrderFee;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStrategy;
import cn.linkmore.prefecture.request.ReqStrategyBase;
import cn.linkmore.prefecture.service.StrategyBaseService;

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
	
	/**
	 * 新增
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqStrategyBase reqStrategyBase) {
		return this.strategyBaseService.save(reqStrategyBase);
	}
	/**
	 * 更新
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqStrategyBase reqStrategyBase) {
		return this.strategyBaseService.update(reqStrategyBase);
	}
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return this.strategyBaseService.delete(ids);
	}
	/**
	 * 校验
	 * @param reqCheck
	 * @return
	 */
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck) {
		Boolean flag = true;
		Integer count = this.strategyBaseService.check(reqCheck);
		if (count > 0) {
			flag = false;
		}
		return flag;
	}
	/**
	 * 列表
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.strategyBaseService.findPage(pageable);
	}
}
