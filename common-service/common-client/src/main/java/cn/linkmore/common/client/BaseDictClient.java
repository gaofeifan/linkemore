package cn.linkmore.common.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.common.client.hystrix.BaseDictClientHystrix;
import cn.linkmore.common.request.ReqBaseDict;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.common.response.ResOldDict;
import cn.linkmore.feign.FeignConfiguration;
/**
 * 数据词典
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "common-server", path = "/dict", fallback=BaseDictClientHystrix.class,configuration = FeignConfiguration.class)
public interface BaseDictClient {
	@RequestMapping(value="/{code}",method=RequestMethod.GET)
	public List<ResBaseDict> findList(@PathVariable("code") String code);
	
	@RequestMapping(method=RequestMethod.POST)
	public void save(@RequestBody ReqBaseDict baseDict);

	@RequestMapping(method=RequestMethod.PUT)
	public void update(@RequestBody ReqBaseDict baseDict);
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void update(@PathVariable("id") Long id);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}/",method=RequestMethod.GET)
	public ResBaseDict find(@PathVariable("id") Long id);
	/**
	 * 根据id查询老字典
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/old/{id}/",method=RequestMethod.GET)
	public ResOldDict findOld(@PathVariable("id") Long id);
}
