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
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.Constants.SwitchResult;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.order.controller.app.request.ReqBooking;
import cn.linkmore.order.controller.app.request.ReqBrandBooking;
import cn.linkmore.order.controller.app.request.ReqOrderStall;
import cn.linkmore.order.controller.app.request.ReqStallBooking;
import cn.linkmore.order.controller.app.request.ReqSwitch;
import cn.linkmore.order.controller.app.response.ResCheckedOrder;
import cn.linkmore.order.controller.app.response.ResOrder;
import cn.linkmore.order.controller.app.response.ResOrderDetail;
import cn.linkmore.order.service.OrdersService;
import cn.linkmore.redis.RedisService;
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
@RequestMapping("/app/order")
@Validated
public class AppOrderController {
	
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private RedisService redisService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@ApiOperation(value = "预约下单", notes = "车区ID不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody ReqBooking rb, HttpServletRequest request) {
		this.ordersService.create(rb, request);
		return ResponseEntity.success(null, request);
	}
	
	@ApiOperation(value = "选择车位预约下单", notes = "车区ID不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.1/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> appoint(@RequestBody ReqStallBooking rsb, HttpServletRequest request) {
		ResponseEntity<?> response = null;
		try { 
			this.ordersService.appoint(rsb, request);
			return ResponseEntity.success(null, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "降下地锁预约下单", notes = "车区ID不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.2/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResOrder> downAppoint(@RequestBody ReqStallBooking rsb, HttpServletRequest request) {
		ResponseEntity<ResOrder> response = null;
		try { 
			ResOrder order = this.ordersService.downAppoint(rsb, request);
			response = ResponseEntity.success(order, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	
	@ApiOperation(value = "品牌预约下单", notes = "品牌预约下单", consumes = "application/json")
	@RequestMapping(value = "/v2.0/brand-create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> brandCreate(@RequestBody ReqBrandBooking rbb, HttpServletRequest request) {
		this.ordersService.brandCreate(rbb, request);
		return ResponseEntity.success(null, request);
	}
	
	@ApiOperation(value = "取消订单", notes = "订单ID不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/cancel", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> cancel(@RequestParam("orderId")Long orderId,HttpServletRequest request) {
		ResponseEntity<?> response = null;
		try { 
			this.ordersService.cancel(orderId, request);
			response = ResponseEntity.success(null, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "切换车位回调", notes = "切换车位回调校验结果[0失败、1成功、2关闭订单]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/switch/result", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> switchResult(@RequestParam("orderId")Long orderId,HttpServletRequest request) { 
		Integer count = (Integer)this.redisService.get(RedisKey.ORDER_SWITCH_RESULT.key+orderId);
		if(count==null) {
			count = SwitchResult.FAILED.value;
		}
		return  ResponseEntity.success(count, request);
	}
	@ApiOperation(value = "切换车位", notes = "原因ID不能为空，备注可为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/switch", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> switchStall( @RequestBody ReqSwitch rs, HttpServletRequest request) {
		this.ordersService.switchStall(rs, request);
		return ResponseEntity.success(null, request);
	}
	
	//TODO
	@ApiOperation(value = "切换车位", notes = "8005105-无空闲车位,订单已关闭；8005106-切换车位失败，若返回有车位名称则表示切换车位成功", consumes = "application/json")
	@RequestMapping(value = "/v2.1/switch", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> switchOrderStall(@RequestParam("orderId")Long orderId, HttpServletRequest request) {
		ResponseEntity<?> response = null;
		try { 
			String stallName = this.ordersService.switchOrderStall(orderId, request);
			response = ResponseEntity.success(stallName, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	

	@ApiOperation(value = "当前订单", notes = "结账离场[组织数据,计算费用，计算时长]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/current", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResOrder> current(HttpServletRequest request) {
		ResOrder order = this.ordersService.current(request);
		return ResponseEntity.success(order, request);
	}
	
	@ApiOperation(value = "用户已完成订单列表", notes = "订单列表[起始请从0开始每页10条记录]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ResCheckedOrder>> list(@RequestParam("start") Long start, @RequestParam(value = "orderFlag", defaultValue = "0", required = false) String orderFlag, HttpServletRequest request) {
		List<ResCheckedOrder> orders = this.ordersService.list(start, orderFlag, request);
		return ResponseEntity.success(orders, request);
	}
	@ApiOperation(value = "订单详情", notes = "订单详情[订单ID须为数字]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResOrderDetail> detail( 
			@Min(value=0,message="订单ID为大于0的长整数")
			@RequestParam("orderId") Long orderId,HttpServletRequest request) {
		ResOrderDetail od = this.ordersService.detail(orderId,request);
		return ResponseEntity.success(od, request);
	}

	@ApiOperation(value = "降下地锁", notes = "降下预约车位地锁[异步操作]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/down", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> downLock(@RequestBody ReqOrderStall ros, 
			HttpServletRequest request) {
		this.ordersService.down(ros, request);
		return ResponseEntity.success(null, request);
	}
	
	@ApiOperation(value = "降锁回调", notes = "降锁回调校验结果", consumes = "application/json")
	@RequestMapping(value = "/v2.0/down/result", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> downResult(HttpServletRequest request) { 
		Integer count = this.ordersService.downResult(request);
		ResponseEntity<?> response = null;
		if(count==0) {
			response =  ResponseEntity.success(count, request);
		}else if(count==1){
			response =  ResponseEntity.fail(StatusEnum.ORDER_LOCKDOWN_FAIL, request);
		}else if(count>1) {
			response = ResponseEntity.fail(StatusEnum.ORDER_FAIL_SWITCHLOCK, request);
		}
		return response;
	}
	
	@ApiOperation(value = "用户预约下单后控制降锁", notes = "8005092降锁失败，更换其他车位；,8005091降锁失败，请再试一次；", consumes = "application/json")
	@RequestMapping(value = "/v2.0/control-down", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> controlDown(@RequestBody ReqOrderStall ros, 
			HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		 try {
			 boolean flag = ordersService.controlDown(ros, request);
			 response = ResponseEntity.success(flag, request);
		}  catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(),  request);
		} catch (Exception e) {
			log.info(">>>>>>>>>>>>control exception={} ,stack:{}",e.getMessage(),e.getStackTrace());
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		 return response;
	}
	
	@ApiOperation(value = "用户预约下单后控制升锁", notes = "8005101升锁失败，故障上报；,8005099升锁失败，请再试一次；", consumes = "application/json")
	@RequestMapping(value = "/v2.0/control-up", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> controlUp(@RequestBody ReqOrderStall ros, 
			HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		 try {
			 boolean flag = ordersService.controlUp(ros, request);
			 response = ResponseEntity.success(flag, request);
		}  catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(),  request);
		} catch (Exception e) {
			log.info(">>>>>>>>>>>>control exception={}",e.getMessage());
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		 return response;
	}
	
	
}
