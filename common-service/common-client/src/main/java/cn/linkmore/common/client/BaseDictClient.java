package cn.linkmore.common.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.common.client.hystrix.BaseDictClientHystrix;
import cn.linkmore.common.response.ResBaseDict;
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
	public List<ResBaseDict> selectList(@PathVariable("code") String code);
}
