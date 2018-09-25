package cn.linkmore.order.controller.staff;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.order.controller.staff.request.ReqUnusualOrder;
import cn.linkmore.order.controller.staff.response.ResUnusualOd;
import cn.linkmore.order.service.UnusualOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 订单
 * 
 * @author changlei
 * @version 2.0
 *
 */
@Api(tags = "UnusualOrder", description = "异常订单")
@RestController
@RequestMapping("/staff/unusual")
public class StaffOrderController {

	@Autowired
	UnusualOrderService unusualOrderService;
	
	/**
	 *异常订单
	 */
	@ApiOperation(value = "查询所有异常订单", notes = "查询所有异常订单", consumes = "application/json")
	@RequestMapping(value = "/select", method = RequestMethod.POST)
	public ResponseEntity<List<ResUnusualOd>> selectDetails(@RequestBody ReqUnusualOrder reqUnusualOrder,HttpServletRequest request){
		List<ResUnusualOd> list =this.unusualOrderService.findList2(reqUnusualOrder);
		return ResponseEntity.success(list, request);
	}
	
	
	
	
	
}
