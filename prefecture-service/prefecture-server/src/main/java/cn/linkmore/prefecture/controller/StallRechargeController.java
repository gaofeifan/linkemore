package cn.linkmore.prefecture.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqStallRechargeExcel;
import cn.linkmore.prefecture.response.ResStallRecharge;
import cn.linkmore.prefecture.service.StallRechargeService;

/**
 * Controller - 电池更换记录
 * @author jiaohanbin
 * @version 2.0
 */
@Controller
@RequestMapping("/stall_recharge")
public class StallRechargeController {
	
	@Autowired
	private StallRechargeService stallRechargeService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.stallRechargeService.findPage(pageable);
	}
	/**
	 * 导出
	 */
	@RequestMapping(value = "/v2.0/export", method = RequestMethod.POST)
	public List<ResStallRecharge> export(ReqStallRechargeExcel bean){
		List<ResStallRecharge> list = this.stallRechargeService.exportList(bean);
		return list;
	}
}
