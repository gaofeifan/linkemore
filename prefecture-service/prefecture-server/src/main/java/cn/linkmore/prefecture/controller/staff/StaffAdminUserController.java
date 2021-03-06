package cn.linkmore.prefecture.controller.staff;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.prefecture.response.ResAdmin;
import cn.linkmore.prefecture.response.ResAdminUser;
import cn.linkmore.prefecture.response.ResStaffPres;
import cn.linkmore.prefecture.service.AdminUserService;
import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags="Staff-Admin-user",description="【管理版】用户信息")
@RestController
@RequestMapping("/staff/admin-user")
@ApiIgnore
public class StaffAdminUserController {

	@Resource
	private AdminUserService adminUserService;
	
	@RequestMapping(value="/by-mobile",method=RequestMethod.GET)
	@ResponseBody
	public ResAdminUser findMobile(@RequestParam("mobile") String mobile) {
		return this.adminUserService.findMobile(mobile);
	}
	@RequestMapping(value="/by-account",method=RequestMethod.GET)
	@ResponseBody
	public ResAdmin findAccountName(@RequestParam("accountName") String accountName) {
		return this.adminUserService.findAccountName(accountName);
	}
	
	@RequestMapping(value="/login-time",method=RequestMethod.PUT)
	@ResponseBody
	public void updateLoginTime(@RequestParam("id") Long id) {
		this.adminUserService.updateLoginTime(id);
	}
	@RequestMapping(value = "/mobile", method = RequestMethod.PUT)
	@ResponseBody
	public void updateMobile(@RequestParam("id")Long id, @RequestParam("mobile")String mobile) {
		this.adminUserService.updateMobile(id,mobile);
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	@ResponseBody
	public ResAdmin authLogin(@RequestParam("mobile") String mobile) {
		return this.adminUserService.authLogin(mobile);
	}
	
	@RequestMapping(value = "/by-id", method = RequestMethod.GET)
	@ResponseBody
	public ResAdminUser findById(@RequestParam("id")Long id) {
		return this.adminUserService.find(id);
	}
	
	@RequestMapping(value = "/pw", method = RequestMethod.PUT)
	@ResponseBody
	public void updatePw(@RequestParam("id")Long id, @RequestParam("pw")String pw) {
		this.adminUserService.updatePw(id,pw);
	}
	
	@RequestMapping(value = "/find-user-pres", method = RequestMethod.GET)
	@ResponseBody
	public List<ResStaffPres> findUserPres(@RequestParam("id")Long id){
		return this.adminUserService.findUserPres(id);
	}
}
