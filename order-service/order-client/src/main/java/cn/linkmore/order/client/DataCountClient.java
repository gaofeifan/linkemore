package cn.linkmore.order.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.order.client.hystrix.DataCountClientHystrix;
import cn.linkmore.order.client.hystrix.OrderClientHystrix;
import cn.linkmore.order.request.ReqDataCount;
import cn.linkmore.order.request.ReqOrderExcel;
import cn.linkmore.order.response.ResOrder;
import cn.linkmore.order.response.ResOrderExcel;
import cn.linkmore.order.response.ResOrderOperateLog;
import cn.linkmore.order.response.ResUserOrder;

@FeignClient(value = "order-server", path = "/feign/data-count", fallback=DataCountClientHystrix.class,configuration = FeignConfiguration.class)
public interface DataCountClient { 
	
	
	@RequestMapping(value = "/save-virtual-data", method = RequestMethod.POST)
	@ResponseBody
	public void saveVirtualData(@RequestBody ReqDataCount copyObject);

	@RequestMapping(value = "/stop", method = RequestMethod.GET)
	@ResponseBody
	void stop();
	@RequestMapping(value = "/start", method = RequestMethod.GET)
	@ResponseBody
	void start();
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	void delete(@RequestParam("id") Long id);
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable);
	
}
