package cn.linkmore.enterprise.controller.app;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.app.request.ReqLocation;
import cn.linkmore.enterprise.controller.app.request.ReqUserRentStall;
import cn.linkmore.enterprise.controller.app.response.OwnerRes;
import cn.linkmore.enterprise.controller.app.response.ResCurrentOwner;
import cn.linkmore.enterprise.service.UserRentStallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户长租车位
 * @author   GFF
 * @Date     2019年1月10日
 * @Version  v2.0
 */
@Api(tags = "UserRentStall", description = "用户长租车位")
@RestController
@RequestMapping("/app/user-rent-stall")
public class AppUserRentStallController {

	@Autowired
	private UserRentStallService userRentStallService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@ApiOperation(value = "获取车位列表", notes = "根据用户身份获取已拥有车位", consumes = "application/json")
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<OwnerRes> list(@Validated  @RequestBody ReqLocation  location,HttpServletRequest request) {
		ResponseEntity<OwnerRes> response = null;
		 try {
			 OwnerRes res = userRentStallService.findStall(request,location);
			 response = ResponseEntity.success(res, request);
		}  catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		 return response;
	}
	
	@ApiOperation(value = "长租用户操作车位锁",notes = "8005099地锁升起失败,再升一次,8005100地锁降下失败,再降一次,8005101地锁升起失败,8005102地锁降下失败,8005093 车位锁其他用户在操作;", consumes = "application/json")
	@RequestMapping(value = "/v2.0/control", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> controlLock(@Validated @RequestBody ReqUserRentStall reqConStall,HttpServletRequest request) {
		try {
			Boolean control = userRentStallService.control(reqConStall, request);
			return ResponseEntity.success(control, request);
		} catch (BusinessException e) {
			return ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			log.info("e={}",JSON.toJSON(e.getMessage()));
			return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
	}
	
	@ApiOperation(value = "查询用户是否有长租车位", notes = "查询用户是否有长租车位", consumes = "application/json")
	@RequestMapping(value = "/v2.0/owner", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Boolean> owner( HttpServletRequest request) {
		Boolean is = userRentStallService.owner(request);
		return ResponseEntity.success(is, request);
	}
	
	@ApiOperation(value = "查询当前是否有使用长租车位", notes = "查询当前是否使用长租车位", consumes = "application/json")
	@RequestMapping(value = "/v2.0/current", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResCurrentOwner> current(HttpServletRequest request){
		ResCurrentOwner owner = this.userRentStallService.current(request);
		return ResponseEntity.success(owner, request);
	}
	
	@ApiOperation(value = "查询用户是否有车位授权标识", notes = "查询用户是否有车位授权标识", consumes = "application/json")
	@RequestMapping(value = "/v2.0/auth-flag", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Boolean> authFlag( HttpServletRequest request) {
		Boolean is = userRentStallService.authFlag(request);
		return ResponseEntity.success(is, request);
	}
	
	
}
