package cn.linkmore.order.controller.app;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.order.controller.app.request.ReqPayConfirm;
import cn.linkmore.order.controller.app.response.ResOrderDetail;
import cn.linkmore.order.controller.app.response.ResPayCheckout;
import cn.linkmore.order.controller.app.response.ResPayConfirm;
import cn.linkmore.order.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 支付
 * @author liwenlong
 * @version 2.0
 *
 */
@Api(tags="Pay",description="订单结账")
@RestController
@RequestMapping("/app/pay")
public class AppPayController {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PayService payService;
	
	@ApiOperation(value = "生成账单", notes = "生成账单[订单ID不为空]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/checkout", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResPayCheckout> checkout(@RequestParam("orderId") Long orderId, HttpServletRequest request) {
		ResPayCheckout checkout = payService.checkout(orderId,request);
		return ResponseEntity.success(checkout, request);
	}
	@ApiOperation(value = "确认支付", notes = "确认支付", consumes = "application/json")
	@RequestMapping(value = "/v2.0/confirm", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResPayConfirm> confirm(@RequestBody ReqPayConfirm roc, HttpServletRequest request) {
		ResponseEntity<ResPayConfirm> response = null;
		try { 
			ResPayConfirm confirm = this.payService.confirm(roc,request);
			return ResponseEntity.success(confirm, request);
		} catch (BusinessException e) { 
			response = ResponseEntity.fail(e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "校验支付", notes = "校验支付[订单ID不为空]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/verify", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResOrderDetail> verify(@RequestParam("orderId") Long orderId, HttpServletRequest request) {
		ResOrderDetail detail = this.payService.verify(orderId,request);
		return ResponseEntity.success(detail, request);
	}
	
}
