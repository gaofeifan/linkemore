package cn.linkmore.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.user.response.ResOrder;
import cn.linkmore.user.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * Controller - 订单
 * @author liwenlong
 * @version 2.0
 *
 */
@Api(tags="Order",description="订单")
@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@ApiOperation(value="预约下单",notes="车区ID不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> login(@RequestParam(value="prefectureId" ,required=true) Long prefectureId, HttpServletRequest request) {
		ResponseEntity<?> response = null; 
		try {  
			response = ResponseEntity.success(null, request);
		}catch(BusinessException e){
			response = ResponseEntity.fail(e.getStatusEnum(), request); 
		}catch(Exception e){
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value="当前订单",notes="结账离场[组织数据,计算费用，计算时长]", consumes = "application/json")
	@RequestMapping(value = "/v2.0", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResOrder> current(HttpServletRequest request) {
		ResponseEntity<ResOrder> response = null; 
		try {  
			response = ResponseEntity.success(null, request);
		}catch(BusinessException e){
			response = ResponseEntity.fail(e.getStatusEnum(), request); 
		}catch(Exception e){
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value="降下地锁",notes="降下预约车位地锁[异步操作]", consumes = "application/json")
	@RequestMapping(value = "/v2.0", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> downLock(HttpServletRequest request) {
		ResponseEntity<ResOrder> response = null; 
		try {  
			response = ResponseEntity.success(null, request);
		}catch(BusinessException e){
			response = ResponseEntity.fail(e.getStatusEnum(), request); 
		}catch(Exception e){
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	
	
}
