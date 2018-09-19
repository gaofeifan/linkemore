package cn.linkmore.account.controller.staff;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.service.CustomerInfoService;
import cn.linkmore.account.service.StaffCauseService;
import cn.linkmore.prefecture.client.AdminUserClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "客户信息录入", produces = "application/json")
@RestController
@RequestMapping("/staff/customer")
public class StaffCustomerController {
	
	@Resource
	private StaffCauseService staffCauseService;
	
	@Resource
	private AdminUserClient adminUserClient;
	
	@Resource
	private CustomerInfoService customerInfoService;
	
/*	@ApiOperation(value = "顾客属性数据来源", notes = "顾客属性数据来源", consumes = "application/json")
	@RequestMapping(value = "/datas", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResCustomerInfo> datas(HttpServletRequest request,@RequestParam("oId") Long oId){ 
		CustomerResponseBean crb = this.causeService.findCustoerData(oId);
		return ResponseEntity.success(crb, request); 
	}
	
	@ApiOperation(value = "保存顾客信息", notes = "保存顾客信息", consumes = "application/json")
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public ResponseEntity<Void>  submit(@RequestBody CustomerRequestBean crb, HttpServletRequest request){ 
		ResponseEntity<Void> re = null;
		try {
			AdminUser au = this.adminUserService.getCacheUser(request);
			crb.setAdminId(au.getId());
			cusTomerInfoService.save(crb);
			re = ResponseEntity.success(null, request);
		} catch (BusinessException e) { 
			re = ResponseEntity.fail(Msg.CUSTOMER_SUBMIT_ERR, request);
			return re;
		} catch (Exception e) { 
			re = ResponseEntity.fail(Msg.CUSTOMER_SUBMIT_ERR, request);
			return re;
		}
		return re;
	}*/
}
