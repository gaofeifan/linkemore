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
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStrategyBase;
import cn.linkmore.prefecture.response.ResFeeStrategy;
import cn.linkmore.prefecture.response.ResStrategyBase;
import cn.linkmore.prefecture.service.StrategyBaseService;

/**
 * Controller - 计费操作
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/ops/strategy")
public class StrategyBaseController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StrategyBaseService strategyBaseService;
	
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
	
	/**
	 * 列表
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/v2.0/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyBase> findList(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", 1);
		return this.strategyBaseService.findList(param);
	}
	
	/**
	 * 计费策略下拉列表
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/v2.0/select_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResFeeStrategy> findSelectList() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", 1);
		return this.strategyBaseService.findSelectList(param);
	}
}
