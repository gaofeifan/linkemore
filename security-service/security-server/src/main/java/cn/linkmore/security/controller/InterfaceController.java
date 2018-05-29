package cn.linkmore.security.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqInterface;
import cn.linkmore.security.response.ResInterface;
import cn.linkmore.security.service.InterfaceService;

/**
 * Controller - 接口操作
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/interface")
public class InterfaceController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private InterfaceService interfaceService;
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqInterface reqItf){
		return this.interfaceService.save(reqItf);
	}
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqInterface reqItf){
		return this.interfaceService.update(reqItf);
	}
	
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids){ 
		return this.interfaceService.delete(ids);
	}
	
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck){
		Boolean flag = true ;
		Integer count = this.interfaceService.check(reqCheck); 
		if(count>0){
            flag = false;
        }
        return flag;
	}
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){ 
		return this.interfaceService.findPage(pageable); 
	} 
	
	
	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.GET)
	@ResponseBody
	public Tree tree(){ 
		return this.interfaceService.findTree();
	}
	
	@RequestMapping(value = "/v2.0/findAll", method = RequestMethod.GET)
	@ResponseBody
	public List<ResInterface> findAll(){ 
		log.info("query find all");
		return this.interfaceService.findAll();
	}
	
	@RequestMapping(value = "/v2.0/person_auth_list", method = RequestMethod.GET)
	@ResponseBody
	public List<ResInterface> findPersonAuthList(Long id){ 
		return this.interfaceService.findPersonAuthList(id);
	}
}
