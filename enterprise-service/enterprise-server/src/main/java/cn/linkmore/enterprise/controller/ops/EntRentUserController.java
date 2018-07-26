package cn.linkmore.enterprise.controller.ops;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.ent.request.ReqAddEntPreture;
import cn.linkmore.enterprise.controller.ent.request.ReqAddEntRentUser;
import cn.linkmore.enterprise.controller.ent.request.ReqUpdateEntPreture;
import cn.linkmore.enterprise.controller.ent.request.ReqUpdateEntRentUser;
import cn.linkmore.enterprise.service.EntRentUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */

@Api(tags = "rent",description="企业rent用户", produces = "application/json")
@RestController
@RequestMapping("/ops/rent")
public class EntRentUserController {
	
	@Autowired
	private EntRentUserService entRentUserService;
	
	@ApiOperation(value = "保存企业rent用户", notes = "保存企业rent用户", consumes = "application/json")
	@RequestMapping(value = "/save-ent-rent-user",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> saveEntRentUser(@RequestBody ReqAddEntRentUser reqAddEntRentUser,HttpServletRequest request){
		if(reqAddEntRentUser ==  null){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		int result = entRentUserService.saveEntRentUser(reqAddEntRentUser.getEntId(),reqAddEntRentUser.getEntPreId(),reqAddEntRentUser.getStallId(),reqAddEntRentUser.getMobile(),reqAddEntRentUser.getRealname(),reqAddEntRentUser.getPlate());
		if(result == 0){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		return ResponseEntity.success("保存成功", request);
	}
	
    @ApiOperation(value = "删除企业rent用户", notes = "删除企业rent用户", consumes = "application/json")
	@RequestMapping(value = "/delete-ent-rent-user",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> deleteEntRentUser(Long id ,HttpServletRequest request ){
		if(id ==  null){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		int result = entRentUserService.deleteEntRentUser(id);
		if(result == 0){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		return ResponseEntity.success("删除成功", request);
	}
    
    @ApiOperation(value = "修改企业分区", notes = "修改企业分区", consumes = "application/json")
	@RequestMapping(value = "/update-ent-rent-user",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> updateEntRentUser(@RequestBody ReqUpdateEntRentUser reqUpdateEntRentUser,HttpServletRequest request ){
		if(reqUpdateEntRentUser ==  null){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		int result = entRentUserService.updateEntRentUser(reqUpdateEntRentUser.getId(),reqUpdateEntRentUser.getMobile(),reqUpdateEntRentUser.getRealname(),reqUpdateEntRentUser.getPlate());
		if(result == 0){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		return ResponseEntity.success("修改成功", request);
	}

}
