package cn.linkmore.enterprise.controller.ent;

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
import cn.linkmore.enterprise.controller.ent.request.ReqAddEntVipUser;
import cn.linkmore.enterprise.controller.ent.request.ReqUpdateEntPreture;
import cn.linkmore.enterprise.controller.ent.request.ReqUpdateEntVipUser;
import cn.linkmore.enterprise.service.EntVipUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */

@Api(tags = "vip",description="企业vip用户", produces = "application/json")
@RestController
@RequestMapping("/vip")
public class EntVipUserController {
	
	@Autowired
	private EntVipUserService entVipUserService;
	
	@ApiOperation(value = "保存企业vip用户", notes = "保存企业vip用户", consumes = "application/json")
	@RequestMapping(value = "/save-ent-vip-user",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> saveEntVipUser(@RequestBody ReqAddEntVipUser reqAddEntVipUser,HttpServletRequest request){
		if(reqAddEntVipUser ==  null){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		int result = entVipUserService.saveEntVipUser(reqAddEntVipUser.getEntId(),reqAddEntVipUser.getEntPreId(),reqAddEntVipUser.getMobile(),reqAddEntVipUser.getRealname(),reqAddEntVipUser.getPlate());
		if(result == 0){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		return ResponseEntity.success("保存成功", request);
	}
	
    @ApiOperation(value = "删除企业vip用户", notes = "删除企业vip用户", consumes = "application/json")
	@RequestMapping(value = "/delete-ent-vip-user",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> deleteEntVipUser(Long id ,HttpServletRequest request ){
		if(id ==  null){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		int result = entVipUserService.deleteEntVipUser(id);
		if(result == 0){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		return ResponseEntity.success("删除成功", request);
	}
    
    @ApiOperation(value = "修改企业分区", notes = "修改企业分区", consumes = "application/json")
	@RequestMapping(value = "/update-ent-vip-user",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> updateEntVipUser(@RequestBody ReqUpdateEntVipUser reqUpdateEntVipUser,HttpServletRequest request ){
		if(reqUpdateEntVipUser ==  null){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		int result = entVipUserService.updateEntVipUser(reqUpdateEntVipUser.getId(),reqUpdateEntVipUser.getMobile(),reqUpdateEntVipUser.getRealname(),reqUpdateEntVipUser.getPlate());
		if(result == 0){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		return ResponseEntity.success("修改成功", request);
	}

}
