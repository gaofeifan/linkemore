package cn.linkmore.common.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.common.client.hystrix.CityClientHystrix;
import cn.linkmore.common.response.ResCity;
/**
 * 远程调用 - 城市信息
 * @author liwenlong
 * @version 2.0
 *
 */
@FeignClient(value = "common-server", path = "/common/citys", fallback=CityClientHystrix.class,configuration = FeignConfiguration.class)
public interface CityClient {
	/**
	 * 根据城市id获取对应的城市信息
	 * @param id 主键ID
	 * @return 城市信息
	 */
	@RequestMapping(value="{id}",method=RequestMethod.GET)
    ResCity find(@PathVariable("id") Long id);
	/**
	 * 分页获取城市信息
	 * @param start 起始
	 * @param size 记录数
	 * @return List<ResCity> 城市信息集合
	 */
	@RequestMapping(value="list",method=RequestMethod.GET)
	List<ResCity> list(@RequestParam("start") int start, @RequestParam("size") int size);
}
