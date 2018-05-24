package cn.linkmore.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.user.request.ReqMobileBind;
import cn.linkmore.user.request.ReqUpdateVehicle;
import cn.linkmore.user.service.UserService;
import cn.linkmore.util.ObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * Controller - 用户
 * @author liwenlong
 * @version 2.0
 *
 */
@Api(tags="User",description="用户信息")
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService; 
	
	@ApiOperation(value="绑定手机号",notes="手机号不能为空,短信验证码不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/mobile", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> bindMobile(@RequestBody ReqMobileBind rmb, HttpServletRequest request){
		ResponseEntity<?> response = null; 
		try {
			this.userService.bindMobile(rmb,request);
			response = ResponseEntity.success(null, request);
		}catch(BusinessException e){
			e.printStackTrace();
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		}catch(Exception e){
			e.printStackTrace();
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value="发短信验证码",notes="手机号不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/sms", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> sms(@RequestParam(value="mobile" ,required=true) String mobile,HttpServletRequest request){
		ResponseEntity<?> response = null; 
		this.userService.send(mobile,request);
		return response;
	}
	

	/**
	 * @Description  更新昵称
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="更新昵称",notes="昵称不能为空，用户需要登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/nickname", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateNickname(@ApiParam(value="昵称",required=true) @NotBlank(message="昵称不能为空") @RequestParam("nickname") String nickname,HttpServletRequest request) {
		this.userService.updateNickname(nickname,request);
		return ResponseEntity.success(null, request);
	}
	
	/**
	 * @Description  更新性别
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="更新性别",notes="性别不能为空，用户需要登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/sex", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateSex(@ApiParam(value="性别 1 男 2女",required=true) @NotBlank(message="性别不能为空")  @RequestParam("sex") @Pattern(regexp="\\(1/2\\)" ,message="参数填写有误请输入1/2") Integer sex,HttpServletRequest request) {
		this.userService.updateSex(sex,request);
		return new ResponseEntity<>();
	}
	
	/**
	 * @Description  修改车辆品牌信息
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="修改车辆品牌信息",notes="车牌号不能为空，用户需要登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/vehicle", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateVehicle(@RequestBody ReqUpdateVehicle vehicle,HttpServletRequest request) {
		this.userService.updateVehicle(vehicle,request);
		return ResponseEntity.success(null, request);
	}
	
	
	/**
	 * @Description  查询详情
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="查询详情",notes="用户需要登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<cn.linkmore.user.response.ResUserDetails> detail(HttpServletRequest request) {
		ResUserDetails details = this.userService.detail(request);
		cn.linkmore.user.response.ResUserDetails object = ObjectUtils.copyObject(details, new cn.linkmore.user.response.ResUserDetails());
		ResponseEntity<cn.linkmore.user.response.ResUserDetails> entity = ResponseEntity.success(object, request);
		return entity;
	}
	
	/**
	 * @Description  删除微信号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="删除微信号",notes="用户需要登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/wechat", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> removeWechat(HttpServletRequest request) {
		this.userService.removeWechat(request);
		ResponseEntity<?> response = ResponseEntity.success(null, request);
		return response;
	}
}
