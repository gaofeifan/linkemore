package cn.linkmore.prefecture.controller.app;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.prefecture.service.StallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * Controller - 临停车位
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Api(tags = "Stall", description = "扫码降锁停车")
@RestController
@RequestMapping("/app/stall")
public class AppStallController {

	@Autowired
	private StallService stallService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@ApiOperation(value = "用户操作车位锁", notes = "用户操作车位锁", consumes = "application/json")
	@RequestMapping(value = "/v2.0/control", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> controlLock(@Validated @RequestParam(value="stallId", required=true) Long stallId ,HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		 try {
			 boolean flag = stallService.control(stallId, request);
			 response = ResponseEntity.success(flag, request);
		}  catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(),  request);
		} catch (Exception e) {
			log.info(">>>>>>>>>>>>control exception={} ,stack:{}",e.getMessage(),e.getStackTrace());
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		 return response;
	}
	
	@ApiOperation(value = "蓝牙降锁成功后上报验证", notes = "蓝牙降锁成功后上报验证", consumes = "application/json")
	@RequestMapping(value = "/v2.0/verify-bluetooth", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> bluetooth(@Validated @RequestParam(value="stallId", required=true) Long stallId ,HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		 try {
			 boolean flag = stallService.verify(stallId, request);
			 response = ResponseEntity.success(flag, request);
		}  catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(),  request);
		} catch (Exception e) { 
			log.info(">>>>>>>>>>>>verify-bluetooth exception={} ,stack:{}",e.getMessage(),e.getStackTrace());
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		 return response;
	}
	
	@ApiOperation(value = "扫码降锁下单后操作车位锁", notes = "扫码降锁下单后操作车位锁", consumes = "application/json")
	@RequestMapping(value = "/v2.0/down-control", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> downLock(@Validated @RequestParam(value="stallId", required=true) Long stallId ,HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		try {
			 boolean flag = stallService.controlLock(stallId, request);
			 response = ResponseEntity.success(flag, request);
		}  catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(),  request);
		} catch (Exception e) { 			
			log.info(">>>>>>>>>>>>down-control exception={} ,stack:{}",e.getMessage(),e.getStackTrace());
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		 return response;
	}
}
