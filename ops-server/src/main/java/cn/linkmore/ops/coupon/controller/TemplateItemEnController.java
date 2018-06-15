package cn.linkmore.ops.coupon.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.coupon.request.ReqTemplateItem;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.ops.coupon.service.TemplateItemEnService;

@Controller
@RequestMapping("/admin/coupon_enterprise_Item")
public class TemplateItemEnController {
	
	@Autowired
	private TemplateItemEnService templateItemEnService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqTemplateItem record){
		ViewMsg msg = null;
		try {
			this.templateItemEnService.save(record);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
		 
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqTemplateItem record){
		ViewMsg msg = null;
		try {
			this.templateItemEnService.update(record);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/selectBuDealNumber", method = RequestMethod.POST)
	@ResponseBody
	public Object selectBuDealNumber(String dealNumber){
		List<ResTemplateItem> items = this.templateItemEnService.selectBuDealNumber(dealNumber);
		return items;
	}
	@RequestMapping(value = "/selectByEnterpriseId", method = RequestMethod.POST)
	@ResponseBody
	public Object selectBuEnterpriseId(HttpServletRequest request , Long id){
		List<ResTemplateItem> items = this.templateItemEnService.selectBuEnterpriseId(id);
		return items;
	}

}
