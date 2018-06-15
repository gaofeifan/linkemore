package cn.linkmore.coupon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.coupon.request.ReqTemplateItem;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.coupon.service.TemplateItemEnService;

@Controller
@RequestMapping("/coupon_enterprise_Item")
public class TemplateItemEnController {
	
	@Autowired
	private TemplateItemEnService templateItemEnService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqTemplateItem record){
		return this.templateItemEnService.save(record);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqTemplateItem record){
		return this.templateItemEnService.update(record);
	}
	
	@RequestMapping(value = "/selectBuDealNumber", method = RequestMethod.POST)
	@ResponseBody
	public Object selectBuDealNumber(@RequestParam("dealNumber") String dealNumber){
		List<ResTemplateItem> items = this.templateItemEnService.selectBuDealNumber(dealNumber);
		return items;
	}
	@RequestMapping(value = "/selectByEnterpriseId", method = RequestMethod.POST)
	@ResponseBody
	public Object selectBuEnterpriseId(@RequestParam("id") Long id){
		List<ResTemplateItem> items = this.templateItemEnService.selectBuEnterpriseId(id);
		return items;
	}
	
}
