package cn.linkmore.enterprise.controller.feign;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.enterprise.entity.EntStaff;
import cn.linkmore.enterprise.response.ResEntStaff;
import cn.linkmore.enterprise.service.EntStaffService;
import cn.linkmore.util.ObjectUtils;

/**
 * 内部调用用户信息
 * @author   GFF
 * @Date     2018年9月4日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/feign/staff") 
public class FeignEntStaffController {

	@Resource
	private EntStaffService entStaffService;
	
	@RequestMapping(value = "/by-id", method = RequestMethod.GET)
	@ResponseBody
	public ResEntStaff findById(@RequestParam("id") Long id) {
		EntStaff entStaff = this.entStaffService.findById(id);
		return ObjectUtils.copyObject(entStaff, new ResEntStaff());
	}
}
