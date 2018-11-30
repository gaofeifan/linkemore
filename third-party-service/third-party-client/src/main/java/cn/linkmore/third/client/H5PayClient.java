package cn.linkmore.third.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.third.client.hystrix.H5PayClientHystrix;
import cn.linkmore.third.request.ReqH5Term;
import cn.linkmore.third.request.ReqH5Token;
import cn.linkmore.third.response.ResH5Degree;
import cn.linkmore.third.response.ResH5Term;


@FeignClient(value = "third-party-server", path = "/feign/h5-pay", fallback=H5PayClientHystrix.class,configuration = FeignConfiguration.class)
public interface H5PayClient {
	
	@RequestMapping(value="/wx",method=RequestMethod.POST)
	@ResponseBody 
	public ResH5Degree wxopenid(@RequestBody ReqH5Token reqH5Token);
	
	@RequestMapping(value="/ali",method=RequestMethod.POST)
	@ResponseBody 
	public ResH5Degree aliopenid(@RequestBody ReqH5Token reqH5Token);
	
	@RequestMapping(value = "/wxpay", method = RequestMethod.POST)
	@ResponseBody
	public ResH5Term wxpay(@RequestBody ReqH5Term reqH5Term);
	
}
