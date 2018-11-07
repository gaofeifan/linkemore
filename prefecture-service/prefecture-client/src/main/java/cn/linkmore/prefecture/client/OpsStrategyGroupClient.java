package cn.linkmore.prefecture.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.OpsStrategyGroupClientHystrix;
import cn.linkmore.prefecture.request.ReqStrategyGroup;
import cn.linkmore.prefecture.request.ReqStrategyGroupDetail;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.response.ResStrategyGroup;
import cn.linkmore.prefecture.response.ResStrategyGroupArea;

/**
 * 远程调用 - 时段策略
 * @author lilinhai
 * @version 1.0
 *
 */
@FeignClient(value = "prefecture-server", path = "/ops/strategy/group", fallback=OpsStrategyGroupClientHystrix.class,configuration = FeignConfiguration.class)
public interface OpsStrategyGroupClient {

	/**
	 * 新增
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqStrategyGroup reqStrategyGroup) ;

	/**
	 * 更新
	 * @param reqStrategyBase
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqStrategyGroup reqStrategyGroup);

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids);

	/**
	 * 删除分组中的车位
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/stall/delete", method = RequestMethod.POST)
	@ResponseBody
	public int deleteStall(@RequestBody Map<String, Object> map);

	/**
	 * 按名称查询车位是否存在
	 * @param stallName
	 * @return
	 */
	@RequestMapping(value = "/stall/exists", method = RequestMethod.POST)
	@ResponseBody
	public Long existsStall(@RequestBody Map<String, Object> map);

	/**
	 * 添加一个车位
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/stall/add", method = RequestMethod.POST)
	@ResponseBody
	public int addStall(@RequestBody ReqStrategyGroupDetail reqStrategyGroupDetail);

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
	 * 列表-无分页
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/find_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyGroup> findList(Map<String, Object> map);
	/**
	 * 根据id获取一条记录
	 * @param pageable
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public ResStrategyGroup selectByPrimaryKey(Long id);

	/**
	 * 获取分区信息，以及分区下的车位信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/area/list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStrategyGroupArea> selectStallByPrimaryKey(@RequestBody Long id);
	/**
	 * 获取分组树
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree findTree(@RequestBody Map<String, Object> param);

	/**
	 * 根据preId,areaId,startName,endName 获取车区信息
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/findAreaStall", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> findAreaStall(@RequestBody Map<String, Object> param) ;
}
