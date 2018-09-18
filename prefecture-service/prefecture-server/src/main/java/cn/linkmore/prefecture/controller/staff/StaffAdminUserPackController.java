package cn.linkmore.prefecture.controller.staff;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.prefecture.service.StaffAdminUserPackService;
import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags="Staff-Admin-user-pack",description="管理版  用户包")
@RestController
@RequestMapping("/staff/admin-user-pack")
@ApiIgnore
public class StaffAdminUserPackController {

	@Resource
	private StaffAdminUserPackService staffAdminUserPackService;
	
	@ResponseBody
	@RequestMapping(value="/by-admin-id",method=RequestMethod.GET)
	public HashMap<String, Object> findByAdminId(@RequestParam("adminId")Long adminid){
		return this.staffAdminUserPackService.findByAdminId(adminid);
	}
	
}
