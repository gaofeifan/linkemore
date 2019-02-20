package cn.linkmore.enterprise.controller.ops;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqFixedUserPick;
import cn.linkmore.enterprise.service.FixedUserService;

@RestController
@RequestMapping("/fixed/user")
public class FixedUserController {

	@Autowired
	FixedUserService fixedUserService;
	
	//长租用户列表
	@RequestMapping(value = "/page",method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findPage(@RequestBody ViewPageable pageable) {
		return fixedUserService.findPage(pageable);
	}
	
	//开启
	@RequestMapping(value = "/pick",method = RequestMethod.POST)
	public void pick(@RequestBody ReqFixedUserPick reqFixedUserPick) {
		fixedUserService.pick(reqFixedUserPick);
	}
	
}
