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
import cn.linkmore.security.entity.DictGroup;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.request.ReqDictGroup;
import cn.linkmore.security.service.DictGroupService;
import cn.linkmore.util.ObjectUtils;

/**
 * Controller - 字典分组操作
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/dict_group")
public class DictGroupController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DictGroupService dictGroupService;
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqDictGroup reqDictGroup){
		DictGroup dictGroup = new DictGroup();
		dictGroup = ObjectUtils.copyObject(reqDictGroup, dictGroup);
		this.dictGroupService.save(dictGroup);
	}
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public void update(@RequestBody ReqDictGroup reqDictGroup){
		DictGroup dictGroup = new DictGroup();
		dictGroup = ObjectUtils.copyObject(reqDictGroup, dictGroup);
		this.dictGroupService.update(dictGroup);
	}
	
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids){ 
		this.dictGroupService.delete(ids);
	}
	
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck){
		Boolean flag = true ;
		Integer count = this.dictGroupService.check(reqCheck); 
		if(count>0){
            flag = false;
        }
        return flag;
	}
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.dictGroupService.findPage(pageable); 
	} 
}
