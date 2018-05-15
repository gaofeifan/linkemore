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
@RequestMapping("/prefecture/stall")
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
	@RequestMapping(value = "/v2.0/lockdown", method=RequestMethod.PUT)
	public void lockdown(@RequestParam("stallId") Long stallId) {
		 this.stallService.lockdown(stallId);
	}

	/**
	 * 结账立场释放车位
	 * 
	 * @param stallId Long
	 */
	@RequestMapping(value = "/v2.0/pay", method=RequestMethod.PUT)
	public void pay(@RequestParam("stallId") Long stallId) {
		 this.stallService.pay(stallId);
	}
}
