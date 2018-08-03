package cn.linkmore.prefecture.client;

import java.util.Map;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqAddEntStaff;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqStaffAuthBind;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.OpsStaffAuthClientHystrix;
/**
 * @author   GFF
 * @Date     2018年7月26日
 * @Version  v2.0
 */
@FeignClient(value = "enterprise-server", path = "/ops/staff", fallback=OpsStaffAuthClientHystrix.class,configuration = FeignConfiguration.class)
public interface OpsStaffAuthClient {
	
	
	@RequestMapping(value="/bind",method=RequestMethod.POST)
	@ResponseBody
	public void bind(@RequestBody ReqStaffAuthBind staff);

	@RequestMapping(value="/tree",method=RequestMethod.GET)
	@ResponseBody
	public Tree tree();
	
	@RequestMapping(value="/resouce",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> resouce(@RequestParam("id")Long staffId);
	
	@RequestMapping(value = "/page",method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findPage(@RequestBody ViewPageable pageable);

	@RequestMapping(value = "delete",method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestParam("id")Long id);
	
	@RequestMapping(value = "save",method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqAddEntStaff staff);

	@RequestMapping(value = "update",method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqAddEntStaff staff);
	
	@RequestMapping(value = "start",method = RequestMethod.PUT)
	@ResponseBody
	public void start(@RequestParam("id") Long id);
	
	@RequestMapping(value = "stop",method = RequestMethod.PUT)
	@ResponseBody
	public void stop(@RequestParam("id") Long id);

	@RequestMapping(value = "check",method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck);
}
