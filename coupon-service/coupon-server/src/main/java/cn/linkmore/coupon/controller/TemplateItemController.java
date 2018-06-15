package cn.linkmore.coupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.coupon.request.ReqTemplateItem;
import cn.linkmore.coupon.service.TemplateItemService;

@Controller
@RequestMapping("/coupon_template_item")
public class TemplateItemController {
	
	@Autowired
	private TemplateItemService templateItemService;
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqTemplateItem record){
		return	this.templateItemService.save(record);
	}
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqTemplateItem record){
		return this.templateItemService.update(record);
	}
}
