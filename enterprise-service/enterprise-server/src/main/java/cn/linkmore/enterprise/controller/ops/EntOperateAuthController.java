package cn.linkmore.enterprise.controller.ops;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.controller.ops.request.ReqOperateAuth;
import cn.linkmore.enterprise.controller.ops.request.ReqOperateBind;
import cn.linkmore.enterprise.service.OperateAuthService;
import cn.linkmore.security.response.ResRoleElement;
import cn.linkmore.security.response.ResRolePage;

/**
 * @author   GFF
 * @Date     2018年7月19日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/ops/operate/auth")
public class EntOperateAuthController {

	@Resource
	private OperateAuthService operateService;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqOperateAuth operateAuth){
		this.operateService.save(operateAuth);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqOperateAuth operateAuth){
		this.operateService.update(operateAuth);
	}

	@RequestMapping(value="/stop",method=RequestMethod.PUT)
	@ResponseBody
	public void stop(@RequestParam("id")Long id){
		this.operateService.stop(id);
	}
	@RequestMapping(value="/start",method=RequestMethod.PUT)
	@ResponseBody
	public void start(@RequestParam("id")Long id){
		this.operateService.start(id);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestParam("id")Long id){
		this.operateService.delete(id);
	}
	
	
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		ViewPage page = this.operateService.findPage(pageable);
		return page;
	}

	@RequestMapping(value="/tree" , method=RequestMethod.GET)
	@ResponseBody
	public List<Tree> tree(){
		return this.operateService.tree();
	}

	@RequestMapping(value="/bind" , method=RequestMethod.POST)
	@ResponseBody
	public void bind(@RequestBody ReqOperateBind bind){
		this.operateService.bind(bind);
	}
	
	@RequestMapping(value="/resource" , method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resource(@RequestParam("id")Long id) { 
		return this.operateService.resource(id);
	}

}
