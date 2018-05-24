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
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.entity.Clazz;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqClazz;
import cn.linkmore.security.service.ClazzService;
import cn.linkmore.util.ObjectUtils;

/**
 * Controller - 类操作
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/clazz")
public class ClazzController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ClazzService clazzService;
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqClazz reqClazz){
		Clazz clazz = new Clazz();
		clazz = ObjectUtils.copyObject(reqClazz, clazz);
		this.clazzService.save(clazz);
	}
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqClazz reqClazz){
		Clazz clazz = new Clazz();
		clazz = ObjectUtils.copyObject(reqClazz, clazz);
		this.clazzService.update(clazz);
	}
	
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids){ 
		this.clazzService.delete(ids);
	}
	
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck){
		Boolean flag = true ;
		Integer count = this.clazzService.check(reqCheck); 
		if(count>0){
            flag = false;
        }
        return flag;
	}
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.clazzService.findPage(pageable); 
	} 
}
