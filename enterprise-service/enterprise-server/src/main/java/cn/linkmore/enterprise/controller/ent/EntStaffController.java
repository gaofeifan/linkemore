/**
 * 
 */
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
import cn.linkmore.enterprise.controller.ent.request.ReqAddEntStaff;
import cn.linkmore.enterprise.controller.ent.request.ReqUpdateEntStaff;
import cn.linkmore.enterprise.service.EntStaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 企业员工
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
@Api(tags = "staff",description="企业员工", produces = "application/json")
@RestController
@RequestMapping("/staff")
public class EntStaffController {
	
	@Autowired
	private EntStaffService entStaffService;
	
	@ApiOperation(value = "保存企业员工", notes = "保存企业员工", consumes = "application/json")
	@RequestMapping(value = "/save-ent-staff",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> saveEntStaff(@RequestBody ReqAddEntStaff reqAddEntStaff,HttpServletRequest request){
		if(reqAddEntStaff ==  null){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		int result = entStaffService.saveEntStaff(reqAddEntStaff.getEntId(),reqAddEntStaff.getMobile(),reqAddEntStaff.getRealname(),reqAddEntStaff.getType());
		if(result == 0){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		return ResponseEntity.success("保存成功", request);
	}
	
    @ApiOperation(value = "删除企业员工", notes = "删除企业员工", consumes = "application/json")
	@RequestMapping(value = "/delete-ent-Staff",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> deleteEntStaff(Long id ,HttpServletRequest request ){
		if(id ==  null){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		int result = entStaffService.deleteEntStaff(id);
		if(result == 0){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		return ResponseEntity.success("删除成功", request);
	}
    
    @ApiOperation(value = "修改企业分区", notes = "修改企业分区", consumes = "application/json")
	@RequestMapping(value = "/update-ent-Staff",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> updateEntStaff(@RequestBody ReqUpdateEntStaff reqUpdateEntStaff,HttpServletRequest request ){
		if(reqUpdateEntStaff ==  null){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		int result = entStaffService.updateEntStaff(reqUpdateEntStaff.getId(),reqUpdateEntStaff.getEntId(),reqUpdateEntStaff.getMobile(),reqUpdateEntStaff.getRealname(),reqUpdateEntStaff.getType(),reqUpdateEntStaff.getStatus());
		if(result == 0){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		return ResponseEntity.success("修改成功", request);
	}

}