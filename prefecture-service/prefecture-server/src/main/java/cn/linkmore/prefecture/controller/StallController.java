package cn.linkmore.prefecture.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.prefecture.service.StallService;

/**
 * Controller - 车位操作
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@RequestMapping("/stall")
public class StallController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StallService stallService;

	/**
	 * 预约订单时，根据车位锁序列号查询车位
	 * 
	 * @param lockSn String
	 */
	@RequestMapping(value = "/v2.0/order", method=RequestMethod.PUT)
	public void order(@RequestParam("lockSn") String lockSn) {
		 this.stallService.order(lockSn);
	}
	/**
	 * 取消订单释放车位
	 * 
	 * @param stallId Long
	 */
	@RequestMapping(value = "/v2.0/cancel", method=RequestMethod.PUT)
	public void cancel(@RequestParam("stallId") Long stallId) {
		 this.stallService.cancel(stallId);
	}

	/**
	 * 降锁操作
	 * 
	 * @param stallId Long
	 */
	@RequestMapping(value = "/v2.0/downlock", method=RequestMethod.PUT)
	public void downlock(@RequestParam("stallId") Long stallId) {
		 this.stallService.downlock(stallId);
	}
	
	/**
	 * 升锁操作
	 * 
	 * @param stallId Long
	 */
	@RequestMapping(value = "/v2.0/uplock", method=RequestMethod.PUT)
	public void uplock(@RequestParam("stallId") Long stallId) {
		 this.stallService.uplock(stallId);
	}

	/**
	 * 结账离场释放车位
	 * 
	 * @param stallId Long
	 */
	@RequestMapping(value = "/v2.0/checkout", method=RequestMethod.PUT)
	public void checkout(@RequestParam("stallId") Long stallId) {
		 this.stallService.checkout(stallId);
	}
}
