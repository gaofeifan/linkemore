package cn.linkmore.prefecture.client;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.AuthRecordClientHystrix;
/**
 * 远程调用 - 授权记录
 * @author   GFF
 * @Date     2018年8月1日
 * @Version  v2.0
 */
@FeignClient(value = "enterprise-server", path = "/auth-record", fallback=AuthRecordClientHystrix.class,configuration = FeignConfiguration.class)
public interface AuthRecordClient {

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findList(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value = "/operate", method = RequestMethod.POST)
	@ResponseBody
	public int operateSwitch(@RequestBody Map<String, Object> param);
	
}
