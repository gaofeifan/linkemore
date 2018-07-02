package cn.linkmore.prefecture.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqOrderStall;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallLock;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.prefecture.service.StallLockService;
import cn.linkmore.prefecture.service.StallService;
import cn.linkmore.util.ObjectUtils;

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

	@Autowired
	private StallLockService stallLockService;
	
	@Autowired
	private PrefectureService preService;

	/**
	 * 预约订单时，根据车位锁序列号查询车位
	 * 
	 * @param lockSn
	 *            String
	 */
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.PUT)
	public void order(@RequestParam("id") Long id) {
		this.stallService.order(id);
	}

	/**
	 * 取消订单释放车位
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/cancel", method = RequestMethod.PUT)
	@ResponseBody
	public boolean cancel(@RequestParam("stallId") Long stallId) {
		return this.stallService.cancel(stallId);
	}

	/**
	 * 降锁操作
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/downlock", method = RequestMethod.PUT)
	@ResponseBody
	public void downlock(@RequestBody ReqOrderStall stall) {
		log.info("downlock:{} .......................................", stall.toString());
		this.stallService.downlock(stall);
	}

	/**
	 * 升锁操作
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/uplock", method = RequestMethod.PUT)
	@ResponseBody
	public Boolean uplock(@RequestParam("stallId") Long stallId) {
		boolean flag = this.stallService.uplock(stallId);
		return flag;
	}

	/**
	 * 结账离场释放车位
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/checkout", method = RequestMethod.PUT)
	@ResponseBody
	public Boolean checkout(@RequestParam("stallId") Long stallId) {
		boolean flag = this.stallService.checkout(stallId);
		return flag;
	}

	/**
	 * 根据车位id获取车位信息
	 * 
	 * @param stallId
	 *            Long
	 */
	@RequestMapping(value = "/v2.0/{stallId}", method = RequestMethod.GET)
	@ResponseBody
	public ResStallEntity findById(@PathVariable("stallId") Long stallId) {
		ResStallEntity stallEntity = this.stallService.findById(stallId);
		return stallEntity;
	}

	@RequestMapping(value = "/v2.0/lock/{sn}", method = RequestMethod.GET)
	@ResponseBody
	public ResStallEntity findByLock(@PathVariable("sn") String sn) {
		ResStallEntity stallEntity = this.stallService.findByLock(sn);
		return stallEntity;
	}

	/*************************************************************************/

}
