package cn.linkmore.enterprise.controller.ops;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.service.AuthRecordService;

@RestController
@RequestMapping("/auth/record")
public class AuthRecordController {

	@Autowired
	AuthRecordService authRecordService;
	
	//列表
	@RequestMapping(value = "/page",method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findPage(@RequestBody ViewPageable pageable) {
		return authRecordService.findPage(pageable);
	}
	
	//开启
	@RequestMapping(value = "/operate",method = RequestMethod.POST)
	public int operateSwitch(@RequestBody Map<String,Object> param) {
		return authRecordService.operateSwitch(param);
	}
	
}
