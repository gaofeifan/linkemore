package cn.linkmore.account.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.entity.UserStaff;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.account.service.UserStaffService;
import cn.linkmore.util.ObjectUtils;

/**
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/user_staff")
public class UserStaffController {

	@Resource
	private UserStaffService userStaffService;
	
	/**
	 * @Description	根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResUserStaff selectById(@PathVariable("id") Long id) {
		ResUserStaff staff = this.userStaffService.findById(id);
		if(staff != null) {
			return ObjectUtils.copyObject(staff, new ResUserStaff());
		}
		return null;
	}
	
}
