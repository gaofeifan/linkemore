package cn.linkmore.coupon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqSendRecord;
import cn.linkmore.coupon.service.SendRecordService;

@Controller
@RequestMapping("/coupon_send_record")
public class SendRecordController {

	@Autowired
	private SendRecordService sendRecordService;

	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqSendRecord record) {
		return this.sendRecordService.save(record);
	}

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.sendRecordService.findPage(pageable);
	}

	@RequestMapping(value = "/v2.0/saveBusiness", method = RequestMethod.POST)
	@ResponseBody
	public int saveBusiness(@RequestBody ReqSendRecord record) {
		return	this.sendRecordService.saveBusiness(record);
	}
	
	@RequestMapping(value = "/v2.0/timingForSend", method = RequestMethod.GET)
	@ResponseBody
	public void timingForSend() {
		this.sendRecordService.timingForSend();
	}
}
