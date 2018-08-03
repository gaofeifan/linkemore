/**
 * 
 */
package cn.linkmore.enterprise.controller.ops;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.controller.ent.request.ReqAddEntStaff;
import cn.linkmore.enterprise.controller.ent.request.ReqUpdateEntStaff;
import cn.linkmore.enterprise.request.ReqCheck;
import cn.linkmore.enterprise.request.ReqStaffAuthBind;
import cn.linkmore.enterprise.service.EntStaffService;
import cn.linkmore.enterprise.service.StaffAuthService;
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
@RequestMapping("/ops/staff")
public class EntStaffController {
	
	@Autowired
	private EntStaffService entStaffService;
	@Resource
	private StaffAuthService staffAuthService;
	
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
	
	@RequestMapping(value = "/page",method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findPage(@RequestBody ViewPageable pageable) {
		return this.entStaffService.findPage(pageable);
	}
	
	@RequestMapping(value = "delete",method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestParam("id")Long id) {
		this.entStaffService.deleteEntStaff(id);
	}
	
	@RequestMapping(value = "save",method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody cn.linkmore.enterprise.request.ReqAddEntStaff staff) {
		this.entStaffService.saveEntStaff(staff.getEntId(), staff.getMobile(), staff.getRealname(), staff.getType());
	}

	@RequestMapping(value = "update",method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody cn.linkmore.enterprise.request.ReqAddEntStaff staff) {
		this.entStaffService.updateEntStaff(staff.getId(), staff.getEntId(), staff.getMobile(), staff.getRealname(), staff.getType(), staff.getStatus());
	}
	
	@RequestMapping(value = "start",method = RequestMethod.PUT)
	@ResponseBody
	public void start(@RequestParam("id") Long id) {
		this.entStaffService.start(id);
	}
	@RequestMapping(value = "stop",method = RequestMethod.PUT)
	@ResponseBody
	public void stop(@RequestParam("id") Long id) {
		this.entStaffService.stop(id);
	}
	
	@RequestMapping(value = "check",method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck) {
		Integer count = this.entStaffService.check(reqCheck.getProperty(),reqCheck.getValue(),reqCheck.getId());
		if(count > 0) {
			return false;
		}
		return true;
	}
}
