package cn.linkmore.prefecture.client;

import java.util.List;
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
import cn.linkmore.enterprise.request.ReqOperateAuth;
import cn.linkmore.enterprise.request.ReqOperateBind;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.OpsOperateAuthClientHystrix;
/**
 * 远程调用 - 企业优惠劵
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@FeignClient(value = "enterprise-server", path = "/ops/operate/auth", fallback=OpsOperateAuthClientHystrix.class,configuration = FeignConfiguration.class)
public interface OpsOperateAuthClient {
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqOperateAuth operateAuth);
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqOperateAuth operateAuth);
	
	@RequestMapping(value="/stop",method=RequestMethod.PUT)
	@ResponseBody
	public void stop(@RequestParam("id")Long id);
	
	@RequestMapping(value="/start",method=RequestMethod.PUT)
	@ResponseBody
	public void start(@RequestParam("id")Long id);
	
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestParam("id")Long id);
	
	
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);

	@RequestMapping(value="/tree" , method=RequestMethod.GET)
	@ResponseBody
	public List<Tree> tree();

	@RequestMapping(value="/bind" , method=RequestMethod.POST)
	@ResponseBody
	public void bind(@RequestBody ReqOperateBind bind);
	
	@RequestMapping(value="/resource" , method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resource(@RequestParam("id")Long id);
}
