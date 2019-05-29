package cn.linkmore.order.controller.feign;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.request.ReqDataCount;
import cn.linkmore.order.request.ReqOrderExcel;
import cn.linkmore.order.response.ResOrderExcel;
import cn.linkmore.order.response.ResOrderOperateLog;
import cn.linkmore.order.response.ResUserOrder;
import cn.linkmore.order.service.DataStatiscsService;
import cn.linkmore.order.service.OrdersService;

@RestController
@RequestMapping("/feign/data-count")
@Validated
public class FeignDataCountController {
	
	@Resource
	private DataStatiscsService dataStatiscsService;
	
	
	@RequestMapping(value = "/save-virtual-data", method = RequestMethod.POST)
	@ResponseBody
	public void saveVirtualData(@RequestBody ReqDataCount copyObject) {
		this.dataStatiscsService.saveVirtualData(copyObject);
	}
	
	@RequestMapping(value = "/stop", method = RequestMethod.GET)
	@ResponseBody
	public void stop() {
		this.dataStatiscsService.stop();
	}
	@RequestMapping(value = "/start", method = RequestMethod.GET)
	@ResponseBody
	public void start() {
		this.dataStatiscsService.start();	
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	void delete(@RequestParam("id") Long id) {
		this.dataStatiscsService.delete(id);
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.dataStatiscsService.list(pageable);
	}
}
