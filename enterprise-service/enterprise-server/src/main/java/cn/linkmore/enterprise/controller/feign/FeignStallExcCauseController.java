package cn.linkmore.enterprise.controller.feign;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.enterprise.controller.ent.request.ReqStallExcCause;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/feign/stall/exc")
public class FeignStallExcCauseController {

	@Resource
	private cn.linkmore.enterprise.service.EntStallService EntStallService;
	
	@ApiOperation(value = "保存车位锁异常原因", notes = "接收车位锁异常原因", consumes = "application/json")
	@RequestMapping(value = "/stall-exc-cause",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> saveStallExcCause(@RequestBody List<ReqStallExcCause> causes ,HttpServletRequest request){
		this.EntStallService.saveStallExcCause(causes);
		return ResponseEntity.success(true, request);
	}
}
