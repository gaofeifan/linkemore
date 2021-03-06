package cn.linkmore.enterprise.controller.feign;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.enterprise.controller.ent.request.ReqStallExcCause;
import cn.linkmore.enterprise.response.ResEntExcStallStatus;
import cn.linkmore.enterprise.service.StallExcStatusService;
import cn.linkmore.util.ObjectUtils;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/feign/stall/exc")
@RestController
public class FeignStallExcCauseController {

	@Resource
	private StallExcStatusService excStatusService;
	@Resource
	private cn.linkmore.enterprise.service.EntStallService EntStallService;
	
	@ApiOperation(value = "保存车位锁异常原因", notes = "接收车位锁异常原因", consumes = "application/json")
	@RequestMapping(value = "/stall-exc-cause",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> saveStallExcCause(@RequestBody List<ReqStallExcCause> causes ,HttpServletRequest request){
		this.EntStallService.saveStallExcCause(causes);
		return ResponseEntity.success(true, request);
	}
	
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public List<ResEntExcStallStatus> findAll(){
		return this.excStatusService.findResAll();
	}
	
	@RequestMapping(value="/by-stall-id",method=RequestMethod.GET)
	@ResponseBody
	public ResEntExcStallStatus findByStallId(@RequestParam("stallId")Long stallId) {
		return this.excStatusService.findByStallId(stallId);
	}
	
	@RequestMapping(value="/exc-status",method=RequestMethod.PUT)
	@ResponseBody
	public void updateExcStatus(@RequestBody Map<String, Object> map) {
		this.excStatusService.updateExcStatus(map);
	}
	
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ResEntExcStallStatus causes){
		this.excStatusService.save(causes);
	}
}
