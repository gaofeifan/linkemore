package cn.linkmore.account.controller.ops;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.request.ReqCheck;
import cn.linkmore.account.request.ReqUserStaff;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.account.service.UserStaffService;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
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
	
	@RequestMapping( method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqUserStaff record){
		this.userStaffService.save(record);
	}
	
	@RequestMapping( method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqUserStaff record){
		this.userStaffService.update(record);
	} 
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck check){
		Boolean flag = true ;
		Integer count = this.userStaffService.check(check); 
		if(count>0){
           flag = false;
       }
       return flag;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.userStaffService.findPage(pageable); 
	}  
	
	@RequestMapping(value = "/v2.0/mobile/{mobile}", method = RequestMethod.GET)
	@ResponseBody
	public ResUserStaff findByMobile(@PathVariable("mobile")String mobile) {
		return this.userStaffService.findByMobile(mobile);
	}
}
