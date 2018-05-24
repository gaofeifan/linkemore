package cn.linkmore.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.user.request.ReqBooking;
import cn.linkmore.user.request.ReqOrderStall;
import cn.linkmore.user.response.ResOrder;
import cn.linkmore.user.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 订单
 * 
 * @author liwenlong
 * @version 2.0
 *
 */
@Api(tags = "Order", description = "订单")
@RestController
@RequestMapping("/orders")
@Validated
public class OrderController {

	@Autowired
	private OrderService orderService;

	@ApiOperation(value = "预约下单", notes = "车区ID不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> create(@Validated @RequestBody ReqBooking rb, HttpServletRequest request) {
		ResponseEntity<?> response = null;
		try {
			this.orderService.create(rb, request);
			response = ResponseEntity.success(null, request);
		} catch (BusinessException e) {
			e.printStackTrace();
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			e.printStackTrace();
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "当前订单", notes = "结账离场[组织数据,计算费用，计算时长]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/current", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResOrder> current(HttpServletRequest request) {
		ResponseEntity<ResOrder> response = null;
		try {
			ResOrder order = this.orderService.current(request);
			response = ResponseEntity.success(order, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "降下地锁", notes = "降下预约车位地锁[异步操作]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/down", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> downLock( @Validated @RequestBody ReqOrderStall ros, 
			HttpServletRequest request) {
		ResponseEntity<ResOrder> response = null;
		try {
			this.orderService.down(ros, request);
			response = ResponseEntity.success(null, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

}
