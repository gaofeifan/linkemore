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
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.StallAssignClientHystrix;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResStall;
/**
 * 远程调用 - 车位指定
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "prefecture-server", path = "/ops/stall_assign", fallback=StallAssignClientHystrix.class,configuration = FeignConfiguration.class)
public interface StallAssignClient {
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
	/**
	 * 车区列表
	 */
	@RequestMapping(value = "/v2.0/prefecture_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreList> findPrefectureList();
	
	@RequestMapping(value = "/v2.0/stall_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> stallList(@RequestBody Map<String,Object> param);
	
}
