package cn.linkmore.prefecture.controller.ops;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqStrategyDate;
import cn.linkmore.prefecture.response.ResStrategyDate;
import cn.linkmore.prefecture.service.StrategyDateService;

@Controller
@RequestMapping("/ops/strategy/date")
public class StrategyDateController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StrategyDateService strategyDateService;

	/**
	 * 新增分期
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqStrategyDate reqStrategyDate) {
		return this.strategyDateService.insert(reqStrategyDate);
	}
	
	/**
	 * 修改分期
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqStrategyDate reqStrategyDate) {
		return this.strategyDateService.updateByPrimaryKey(reqStrategyDate);
	}

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return this.strategyDateService.delete(ids);
	}
	
	/**
	 * 更改状态
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/update_status", method = RequestMethod.POST)
	@ResponseBody
	public int updateStatus(@RequestBody Map<String, Object> map) {
		return this.strategyDateService.updateStatus(map);
	}
	
	/**
	 * 列表-分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.strategyDateService.findPage(pageable);
	}
	
	/**
	 * 根据id获取一条记录
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ResStrategyDate getByPrimarkey(@RequestBody Long id) {
		return this.strategyDateService.selectByPrimaryKey(id);
	}
}
