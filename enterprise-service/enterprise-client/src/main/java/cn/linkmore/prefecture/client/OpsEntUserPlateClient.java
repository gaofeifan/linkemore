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
import cn.linkmore.enterprise.request.ReqEntUserPlate;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.OpsEntUserPlateClientHystrix;

/**
 * 企业免费车牌远程调用
 * 
 * @author cl
 * @Date 2018年7月27日
 * @Version v2.0
 */
@FeignClient(value = "enterprise-server", path = "/ops/ent_user_plate", fallback = OpsEntUserPlateClientHystrix.class, configuration = FeignConfiguration.class)
public interface OpsEntUserPlateClient {


	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqEntUserPlate ent);
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqEntUserPlate ent);
	
	@RequestMapping(value = "/v2.0/ids", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids);

	@RequestMapping(value = "/v2.0/exists", method = RequestMethod.POST)
	@ResponseBody
	public boolean exists(@RequestBody Map<String, Object> checkParam);

	@RequestMapping(value = "/v2.0/save-batch", method = RequestMethod.POST)
	@ResponseBody
	public int saveBatch(@RequestBody List<ReqEntUserPlate> plateList);

}
