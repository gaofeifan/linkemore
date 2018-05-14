package cn.linkmore.common.client;

import java.util.List;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.hystrix.CityClientHystrix;
import cn.linkmore.common.request.ReqCity;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.feign.FeignConfiguration;
/**
 * 远程调用 - 城市信息
 * @author liwenlong
 * @version 2.0
 *
 */
@Controller
@FeignClient(value = "common-server", path = "/common/citys", fallback=CityClientHystrix.class,configuration = FeignConfiguration.class)
public interface CityClient {
	/**
	 * 根据城市id获取对应的城市信息
	 * @param id 主键ID
	 * @return 城市信息
	 */
	@RequestMapping(value="{id}",method=RequestMethod.GET)
	@ResponseBody 
    ResCity find(@PathVariable("id") Long id);
	
	/**
	 * 分页获取城市信息
	 * @param start 起始
	 * @param size 记录数
	 * @return List<ResCity> 城市信息集合
	 */
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody 
	List<ResCity> list(@RequestParam("start") Integer start, @RequestParam("size") Integer size);
	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody 
	public ViewPage list(@RequestBody ViewPageable pageable);
	 
	/**
	 * 保存城市信息
	 * @param reqCity 城市信息
	 */
	@RequestMapping(method=RequestMethod.POST)
	void save(@RequestBody ReqCity reqCity) ; 
	
	/**
	 * 更新城市信息
	 * @param reqCity 城市信息
	 */
	@RequestMapping(method=RequestMethod.PUT)
	void update(@RequestBody ReqCity reqCity); 
	
	/**
	 * 删除城市信息
	 * @param id 主键
	 */
	@RequestMapping(value="{id}",method = RequestMethod.DELETE)
	void delete(@PathVariable("id") Long id);
}
