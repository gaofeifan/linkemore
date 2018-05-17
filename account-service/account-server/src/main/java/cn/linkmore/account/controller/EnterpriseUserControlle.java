package cn.linkmore.account.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.entity.EnterpriseUser;
import cn.linkmore.account.response.ResEnterpriseUser;
import cn.linkmore.account.service.EnterpriseUserService;

/**
 * @Description  
 * @author  GFF
 * @Date     2018年5月16日
 *
 */
@RestController
@RequestMapping("/account/enterprise_user")
public class EnterpriseUserControlle {

	@Resource
	private EnterpriseUserService enterpriseUserService;
	
	@RequestMapping(value="/v2.0/{userId}",method=RequestMethod.GET)
	public ResEnterpriseUser selectById(@PathVariable Long userId) {
		return this.enterpriseUserService.selectByUserId(userId);
	}
	
	
}
