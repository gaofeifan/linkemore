package cn.linkmore.prefecture.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.OpsPrefectureStrategyClientHystrix;
import cn.linkmore.prefecture.request.ReqPrefectureStrategy;
import cn.linkmore.prefecture.response.ResPrefectureStrategyNew;
import cn.linkmore.prefecture.response.ResStrategyFee;

/**
 * 远程调用 - 时段策略
 * @author lilinhai
 * @version 1.0
 *
 */

@FeignClient(value = "prefecture-server", path = "/ops/prefecture_strategy", fallback=OpsPrefectureStrategyClientHystrix.class,configuration = FeignConfiguration.class)
public interface OpsPrefectureStrategyClient {

	/**
	 * 新增
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqPrefectureStrategy reqPrefectureStrategy) ;

	/**
	 * 更新
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqPrefectureStrategy reqPrefectureStrategy);

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids);

	

	/**
	 * 更改状态 开启/关闭
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/update_status", method = RequestMethod.POST)
	@ResponseBody
	public int updateStatus(@RequestBody Map<String, Object> map);
	
	/**
	 * 列表-分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);

	/**
	 * 根据id获取一条记录
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ResPrefectureStrategyNew selectByPrimaryKey(Long id);
	
	/**
	 * 获取计费策略列表
	 * @return
	 */
	@RequestMapping(value = "/strategy_fee/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyFee> findList();
	
	/**
	 * 验证运营时段是否交叉
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/validate/time", method = RequestMethod.POST)
	@ResponseBody
	public int validateTime(@RequestBody Map<String,String> map);
	
	
	/**
	 * 验证运营 分期策略是否交叉
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/validate/date", method = RequestMethod.POST)
	@ResponseBody
	public int validateDate(@RequestBody Map<String,String> map) ;
}
