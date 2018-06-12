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
import cn.linkmore.prefecture.client.hystrix.StrategyBaseClientHystrix;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStrategy;
import cn.linkmore.prefecture.request.ReqStrategyBase;
import cn.linkmore.prefecture.response.ResFeeStrategy;
import cn.linkmore.prefecture.response.ResStrategyBase;
/**
 * 远程调用 - 计费详情
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/feign/strategy", fallback=StrategyBaseClientHystrix.class,configuration = FeignConfiguration.class)
public interface StrategyBaseClient {
	
	/**
	 * 根据计费策略和进出时间获取计费信息
	 * 
	 * @param strategyId String
	 * @param beginTime Date
	 * @param endTime Date
	 */
	@RequestMapping(value = "/v2.0/fee", method=RequestMethod.POST)
	public Map<String, Object> fee(@RequestBody ReqStrategy reqStrategy);
	
	/*****************************************************************/
	
	/**
	 * 新增
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqStrategyBase reqStrategyBase) ;
	/**
	 * 更新
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqStrategyBase reqStrategyBase);
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids);
	/**
	 * 校验
	 * @param reqCheck
	 * @return
	 */
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck);
	/**
	 * 列表
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);

	/**
	 * 列表
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/v2.0/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyBase> findList();
	
	/**
	 * 计费策略下拉列表
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/v2.0/select_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResFeeStrategy> findSelectList();
	
	
}
