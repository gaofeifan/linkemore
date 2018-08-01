package cn.linkmore.ops.ent.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.ent.service.VipUserService;

@RequestMapping(value = "/admin/ent/vip_user")
@Controller
public class VipUserController {

	@Resource
	private VipUserService vipUserService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
	   System.out.println( "进入" );
	   ViewPage a =new ViewPage();
	   a = vipUserService.findPage(pageable);
	   System.out.println( a );
		return a;
	}

}
