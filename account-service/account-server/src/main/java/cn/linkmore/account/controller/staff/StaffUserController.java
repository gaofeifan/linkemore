package cn.linkmore.account.controller.staff;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.service.StaffAdminUserService;
import cn.linkmore.bean.common.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户认证
 * @author   GFF
 * @Date     2018年7月19日
 * @Version  v2.0
 */
@Api(tags="Staff-user",description="用户信息【管理版】")
@RestController
@RequestMapping("/staff/user") 
@Validated
public class StaffUserController {

	@Resource
	private StaffAdminUserService staffAdminUserService; 
	
	@ApiOperation(value="绑定手机号",notes="绑定手机号", consumes = "application/json")
	@RequestMapping(value = "/bind-mobile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> bindMobile(
			@ApiParam("手机号") 
			@NotBlank(message="手机号不能为空") 
			@Pattern(regexp="^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\\d{8})$", message="无效手机号") 
	        @RequestParam("mobile") String mobile, HttpServletRequest request){
		ResponseEntity<Boolean> response = null; 
		boolean flag = this.staffAdminUserService.bindMobile(mobile,request);
		response = ResponseEntity.success(flag, request);
		return response;
	}
	@ApiOperation(value="更换手机号",notes="更换手机号", consumes = "application/json")
	@RequestMapping(value = "/edit-mobile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> editMobile(
			@ApiParam("手机号") 
			@NotBlank(message="手机号不能为空") 
			@Pattern(regexp="^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\\d{8})$", message="无效手机号") 
			@RequestParam("mobile") String mobile, 
			HttpServletRequest request,
			@NotBlank(message="验证码不能为空") 
			@ApiParam(value="code",required=true)
			@Size(min=4, max=4,message="验证码长度为4位有效字符串")  
			String code
			){
		ResponseEntity<Boolean> response = null; 
		boolean flag = this.staffAdminUserService.editMobile(mobile,request,code);
		response = ResponseEntity.success(flag, request);
		return response;
	}
}
