package cn.linkmore.enterprise.controller.ops;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.enterprise.service.StaffService;
import io.swagger.annotations.Api;

/**
 * @author   GFF
 * @Date     2018年7月19日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/ops/staff/auth")
public class EntStaffAuthController {

	@Resource
	private StaffService staffService;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public void save(){
		this.staffService.save();
	}


}
