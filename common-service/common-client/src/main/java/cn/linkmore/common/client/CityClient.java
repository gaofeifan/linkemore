package cn.linkmore.common.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.hystrix.CityClientHystrix;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqCity;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.feign.FeignConfiguration;

/**
 * 远程调用 - 城市信息
 * 
 * @author liwenlong
 * @version 2.0
 *
 */
@FeignClient(value = "common-server", path = "/citys", fallback = CityClientHystrix.class, configuration = FeignConfiguration.class)
public interface CityClient {

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	@ResponseBody
	ResCity getById(@PathVariable("id") Long id);

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	List<ResCity> list(@RequestParam("start") Integer start, @RequestParam("size") Integer size);

	@RequestMapping(method = RequestMethod.POST)
	void save(@RequestBody ReqCity reqCity);

	@RequestMapping(method = RequestMethod.PUT)
	void update(@RequestBody ReqCity reqCity);

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	void delete(@PathVariable("id") Long id);

	@RequestMapping(value = "/code", method = RequestMethod.GET)
	@ResponseBody
	ResCity getByCode(@RequestParam("code") String code);

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	void deleteIds(@RequestBody List<Long> ids);

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	Boolean check(@RequestBody ReqCheck check);

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	ViewPage findPage(@RequestBody ViewPageable pageable);
}
