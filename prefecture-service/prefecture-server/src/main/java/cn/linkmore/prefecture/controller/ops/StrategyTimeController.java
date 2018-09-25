package cn.linkmore.prefecture.controller.ops;

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
import cn.linkmore.prefecture.request.ReqStrategyTime;
import cn.linkmore.prefecture.response.ResStrategyTime;
import cn.linkmore.prefecture.service.StrategyTimeService;

/**
 * Controller - 时段策略
 * 
 * @author lilinhai
 * @version 1.0
 *
 */

@RestController
@RequestMapping("/ops/strategy/time")
public class StrategyTimeController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StrategyTimeService strategyTimeService;
	
	/**
	 * 新增时段
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqStrategyTime reqStrategyInterval) {
		System.out.println(111);
		return this.strategyTimeService.save(reqStrategyInterval);
	}
	
	/**
	 * 更新
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqStrategyTime reqStrategyInterval) {
		return this.strategyTimeService.update(reqStrategyInterval);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return this.strategyTimeService.delete(ids);
	}	
	
	/**
	 * 更改状态
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/update_status", method = RequestMethod.POST)
	@ResponseBody
	public int updateStatus(@RequestBody Map<String, Object> map) {
		return this.strategyTimeService.updateStatus(map);
	}

	
	/**
	 * 列表-分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.strategyTimeService.findPage(pageable);
	}
	
	/**
	 * 列表-无分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyTime> findList(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", 1);
		return this.strategyTimeService.findList(param);
	}	
	
	/**
	 * 根据id获取一条记录
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ResStrategyTime getByPrimarkey(@RequestBody Long id) {
		return this.strategyTimeService.selectByPrimaryKey(id);
	}
}
