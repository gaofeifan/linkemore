package cn.linkmore.account.controller.app;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.controller.app.request.ReqStallFault;
import cn.linkmore.account.service.StallFaultService;
import cn.linkmore.bean.common.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/app/fault-stall/feedback")
@Validated
@Api(tags="fault-stall-Feedback",description="故障车位上报")
public class AppStallFaultController {

	@Resource
	private StallFaultService stallFaultService;
	
	@ApiOperation(value = "保存", consumes = "application/json")
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> save(@RequestBody ReqStallFault fault ,HttpServletRequest request){
		this.stallFaultService.save(fault,request);
		return ResponseEntity.success(true, request);
	}
	
}
