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
import cn.linkmore.prefecture.request.ReqPrefectureStrategy;
import cn.linkmore.prefecture.response.ResPrefectureStrategyNew;
import cn.linkmore.prefecture.response.ResStrategyFee;
import cn.linkmore.prefecture.service.PrefectureStrategyService;
import cn.linkmore.prefecture.service.StrategyFeeService;
/**
 * 车区策略
 * @author llh
 *
 */
@Controller
@RequestMapping("/ops/prefecture_strategy")
public class PrefectureStrategyController {

private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PrefectureStrategyService prefectureStrategyService;
	
	@Autowired
	private StrategyFeeService strategyFeeService;
	/**
	 * 新增
	 * @param reqPrefectureStrategy
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqPrefectureStrategy reqPrefectureStrategy) {
		return this.prefectureStrategyService.save(reqPrefectureStrategy);
	}

	/**
	 * 更新
	 * @param reqPrefectureStrategy
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqPrefectureStrategy reqPrefectureStrategy) {
		return this.prefectureStrategyService.update(reqPrefectureStrategy);
	}

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return this.prefectureStrategyService.delete(ids);
	}
	
	/**
	 * 更改状态
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/update_status", method = RequestMethod.POST)
	@ResponseBody
	public int updateStatus(@RequestBody Map<String, Object> map) {
		return this.prefectureStrategyService.updateStatus(map);
	}

	/**
	 * 列表-分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.prefectureStrategyService.findPage(pageable);
	}

	/**
	 * 根据id获取一条记录
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ResPrefectureStrategyNew getByPrimarkey(@RequestBody Long id) {
		return this.prefectureStrategyService.selectByPrimaryKey(id);
	}

	/**
	 * 获取计费策略列表
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/strategy_fee/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyFee> findList() {
		return this.strategyFeeService.findList();
	}

	/**
	 * 验证运营时段是否交叉
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/validate/time", method = RequestMethod.POST)
	@ResponseBody
	public int validateTime(@RequestBody Map<String,String> map) {
		return this.prefectureStrategyService.validateTime(map);
	}
	
	/**
	 * 验证运营 分期策略是否交叉
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/validate/date", method = RequestMethod.POST)
	@ResponseBody
	public int validateDate(@RequestBody Map<String,String> map) {
		return this.prefectureStrategyService.validateDate(map);
	}
}
