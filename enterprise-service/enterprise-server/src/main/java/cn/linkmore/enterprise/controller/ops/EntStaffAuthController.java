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
import cn.linkmore.enterprise.request.ReqStaffAuthBind;
import cn.linkmore.enterprise.service.EntStaffService;
import cn.linkmore.enterprise.service.StaffAuthService;
import io.swagger.annotations.ApiOperation;

/**
 * @author   GFF
 * @Date     2018年7月19日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/ops/staff")
public class EntStaffAuthController {

	@Resource
	private StaffAuthService staffAuthService;
	
	@Autowired
	private EntStaffService entStaffService;
	
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
	
	@RequestMapping(value = "/delete-ent-Staff",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> deleteEntStaff(@RequestParam("id") Long id ,HttpServletRequest request ){
		if(id ==  null){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		int result = entStaffService.deleteEntStaff(id);
		if(result == 0){
			return ResponseEntity.fail(StatusEnum.VALID_EXCEPTION, request);
		}
		return ResponseEntity.success("删除成功", request);
	}
    
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

	@RequestMapping(value = "/page",method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findPage(@RequestBody ViewPageable pageable) {
		return this.entStaffService.findPage(pageable);
	}
	
}
