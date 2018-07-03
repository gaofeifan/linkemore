package cn.linkmore.ops.account.controller;


import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.AccountService;

/**
 * Controller - 账户信息
 * @author liwenlong
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/admin/account/account")
public class AccountController {
	@Autowired
	private AccountService accountService;
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.accountService.findPage(pageable); 
	} 
}
