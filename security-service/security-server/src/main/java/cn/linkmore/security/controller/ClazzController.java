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

import com.alibaba.druid.support.json.JSONUtils;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqClazz;
import cn.linkmore.security.service.ClazzService;
import cn.linkmore.util.JsonUtil;

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
	public int save(@RequestBody ReqClazz reqClazz){
		log.info("..........save:{}",JsonUtil.toJson(reqClazz));
		return this.clazzService.save(reqClazz);
	}
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.PUT)
	@ResponseBody
	public int update(@RequestBody ReqClazz reqClazz){
		log.info("..........update:{}",JsonUtil.toJson(reqClazz));
		return this.clazzService.update(reqClazz);
	}
	
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids){ 
		log.info("..........delete:{}",JsonUtil.toJson(ids));
		return this.clazzService.delete(ids);
	}
	
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck){
		Boolean flag = true ;
		Integer count = this.clazzService.check(reqCheck); 
		log.info("..........check:{}",JsonUtil.toJson(count));
		if(count>0){
            flag = false;
        }
        return flag;
	}
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		log.info("..........list:{}",this.clazzService.findPage(pageable).getList().size());
		
		return this.clazzService.findPage(pageable); 
	} 
}
