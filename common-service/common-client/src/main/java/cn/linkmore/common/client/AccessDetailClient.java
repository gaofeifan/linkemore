package cn.linkmore.common.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.linkmore.common.client.hystrix.AccessDetailClientHystrix;
import cn.linkmore.common.request.ReqAccessDetail;
import cn.linkmore.feign.FeignConfiguration;
/**
 * 接口访问详情
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@FeignClient(value = "common-server", path = "/feign/access_detail", fallback=AccessDetailClientHystrix.class,configuration = FeignConfiguration.class)
public interface AccessDetailClient {
	
	/**
	 * @Description  app接口访问详情日志
	 * @Author   GFF 
	 * @Date       2018年5月15日
	 */
	@RequestMapping(value="/v2.0/app_save",method=RequestMethod.POST)
	public void appSave(@RequestBody ReqAccessDetail accessDetail);
	
	/**
	 * @Description  小程序接口访问详情日志
	 * @Author   GFF 
	 * @Date     2018年5月15日
	 */
	@RequestMapping(value="/v2.0/mini_save",method=RequestMethod.POST)
	public void miniSave(@RequestBody ReqAccessDetail accessDetail);

}
