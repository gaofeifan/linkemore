package cn.linkmore.order.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.order.controller.app.request.ReqBooking;
import cn.linkmore.order.controller.app.request.ReqOrderStall;
import cn.linkmore.order.controller.app.request.ReqSwitch;
import cn.linkmore.order.controller.app.response.ResCheckedOrder;
import cn.linkmore.order.controller.app.response.ResOrder;
import cn.linkmore.order.controller.app.response.ResOrderDetail;
import cn.linkmore.order.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 订单
 * 
 * @author liwenlong
 * @version 2.0
 *
 */
@Api(tags = "Order", description = "车位预约")
@RestController
@RequestMapping("/order/app/order")
@Validated
public class AppOrderController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrdersService ordersService;

	@ApiOperation(value = "预约下单", notes = "车区ID不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody ReqBooking rb, HttpServletRequest request) {
		ResponseEntity<?> response = null;
		try {
			this.ordersService.create(rb, request);
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
	@ApiOperation(value = "切换车位", notes = "原因ID不能为空，备注可为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/switch", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> switchStall( @RequestBody ReqSwitch rs, HttpServletRequest request) {
		ResponseEntity<?> response = null;
		try {
			this.ordersService.switchStall(rs, request);
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
			ResOrder order = this.ordersService.current(request);
			response = ResponseEntity.success(order, request);
		} catch (BusinessException e) {
			e.printStackTrace();
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			e.printStackTrace();
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "用户已完成订单列表", notes = "订单列表[起始请从0开始每页10条记录]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ResCheckedOrder>> list(@RequestParam("start") Long start,HttpServletRequest request) {
		ResponseEntity<List<ResCheckedOrder>> response = null;
		try {
			List<ResCheckedOrder> orders = this.ordersService.list(start,request);
			response = ResponseEntity.success(orders, request);
		} catch (BusinessException e) {
			e.printStackTrace();
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			e.printStackTrace();
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	@ApiOperation(value = "订单详情", notes = "订单详情[订单ID须为数字]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResOrderDetail> detail( 
			@Min(value=0,message="订单ID为大于0的长整数")
			@RequestParam("orderId") Long orderId,HttpServletRequest request) {
		ResponseEntity<ResOrderDetail> response = null;
		try { 
			ResOrderDetail od = this.ordersService.detail(orderId,request);
			response = ResponseEntity.success(od, request);
		} catch (BusinessException e) {
			e.printStackTrace();
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			e.printStackTrace();
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@ApiOperation(value = "降下地锁", notes = "降下预约车位地锁[异步操作]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/down", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> downLock(@RequestBody ReqOrderStall ros, 
			HttpServletRequest request) {
		ResponseEntity<ResOrder> response = null;
		try {
			this.ordersService.down(ros, request);
			response = ResponseEntity.success(null, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

}
