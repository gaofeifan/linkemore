package cn.linkmore.account.controller.app;

import javax.servlet.http.HttpServletRequest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.controller.app.request.ReqMobileBind;
import cn.linkmore.account.controller.app.request.ReqUpdateVehicle;
import cn.linkmore.account.response.ResUserDetails;
import cn.linkmore.account.service.UserService;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
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
@RequestMapping("/app/users")
@Validated
public class AppUserController {
	
	@Autowired
	private UserService userService; 
	
	@ApiOperation(value="绑定手机号",notes="手机号不能为空,短信验证码不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/mobile", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> bindMobile(@RequestBody @Validated ReqMobileBind rmb, HttpServletRequest request){
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
	public ResponseEntity<?> sms(@NotBlank(message="手机号不能为空") 
	@Pattern(regexp="^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\\d{8})$", message="无效手机号") 
	@RequestParam(value="mobile" ,required=true)  String mobile,HttpServletRequest request){
		ResponseEntity<?> response = null;  
		try {
			this.userService.send(mobile,request);
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
	

	/**
	 * @Description  更新昵称
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="更新昵称",notes="昵称不能为空，用户需要登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/nickname", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateNickname(@ApiParam(value="昵称",required=true)  @NotBlank(message="昵称不能为空") @RequestParam("nickname") String nickname,HttpServletRequest request) {
		this.userService.updateNickname(nickname,request);
		return ResponseEntity.success(null, request);
	}
	
	/**
	 * @Description  更新账号名称
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="更新账号名称",notes="账号名称不能为空，用户需要登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/account_name", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateAccountName(@ApiParam(value="账号名称",required=true)  @NotBlank(message="账号名称不能为空") @RequestParam("accountName") String accountName,HttpServletRequest request) {
		this.userService.updateAccountName(accountName,request);
		return ResponseEntity.success("账户更新成功", request);
	}
	
	/**
	 * @Description  更新性别
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="更新性别",notes="性别不能为空，用户需要登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/sex", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateSex(@ApiParam(value="性别 1 男 2女",required=true) @NotNull(message="性别不能为空") @Range(max=2,min=1,message="数据格式有误请输入1或2")  @RequestParam("sex")Integer sex,HttpServletRequest request) {
		try {
			this.userService.updateSex(sex,request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.success(null, request);
	}
	
	/**
	 * @Description  修改车辆品牌信息
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="修改车辆品牌信息",notes="车牌号不能为空，用户需要登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/vehicle", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateVehicle(@RequestBody @Validated ReqUpdateVehicle vehicle,HttpServletRequest request) {
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
	public ResponseEntity<cn.linkmore.account.controller.app.response.ResUserDetails> detail(HttpServletRequest request) {
		ResUserDetails details = this.userService.detail(request);
		cn.linkmore.account.controller.app.response.ResUserDetails object = ObjectUtils.copyObject(details, new cn.linkmore.account.controller.app.response.ResUserDetails());
		ResponseEntity<cn.linkmore.account.controller.app.response.ResUserDetails> entity = ResponseEntity.success(object, request);
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