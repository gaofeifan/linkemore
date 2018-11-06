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
import cn.linkmore.prefecture.client.hystrix.OpsStrategyTimeClientHystrix;
import cn.linkmore.prefecture.request.ReqStrategyTime;
import cn.linkmore.prefecture.response.ResStrategyDate;
import cn.linkmore.prefecture.response.ResStrategyTime;

/**
 * 远程调用 - 时段策略
 * @author lilinhai
 * @version 1.0
 *
 */

@FeignClient(value = "prefecture-server", path = "/ops/strategy/time", fallback=OpsStrategyTimeClientHystrix.class,configuration = FeignConfiguration.class)
public interface OpsStrategyTimeClient {

	/**
	 * 新增
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqStrategyTime reqStrategyTimeInterval) ;

	/**
	 * 更新
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqStrategyTime reqStrategyInterval);

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
	 * @param ids
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
	 * 列表-无分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyTime> findList(Map<String, Object> map);
	/**
	 * 根据id获取一条记录
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ResStrategyTime selectByPrimaryKey(Long id);

}
