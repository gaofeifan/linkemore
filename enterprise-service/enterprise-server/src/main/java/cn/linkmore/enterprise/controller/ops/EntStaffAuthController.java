package cn.linkmore.enterprise.controller.ops;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.enterprise.request.ReqStaffAuthBind;
import cn.linkmore.enterprise.service.StaffAuthService;

/**
 * @author   GFF
 * @Date     2018年7月19日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/ops/staff/auth")
public class EntStaffAuthController {

	@Resource
	private StaffAuthService staffAuthService;
	
	@RequestMapping(value="/bind",method=RequestMethod.POST)
	@ResponseBody
	public void bind(@RequestBody ReqStaffAuthBind staff){
		this.staffAuthService.bind(staff);
	}

	@RequestMapping(value="/tree",method=RequestMethod.GET)
	@ResponseBody
	public Tree tree(){
		return this.staffAuthService.tree();
	}
	
	@RequestMapping(value="/resouce",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> resouce(@RequestParam("id")Long staffId){
		return this.staffAuthService.resouce(staffId);
	}
}
