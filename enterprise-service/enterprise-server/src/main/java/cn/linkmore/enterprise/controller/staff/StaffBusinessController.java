package cn.linkmore.enterprise.controller.staff;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.annotation.AopIgnore;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.common.request.ReqUnusualLog;
import cn.linkmore.util.ObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller -运营【管理版】
 * @author 
 * @version 2.0
 *
 */
@Api(tags = "ManagerBusiness", description = "运营【管理版】")
@RestController
@RequestMapping("/staff/buss")
public class StaffBusinessController {

	@RequestMapping(value = "/demo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="demo",notes="demo", consumes = "application/json")
	@AopIgnore
	public ResponseEntity<?> upload(HttpServletRequest request) {
		
		return ResponseEntity.success("demo",request );
	}
	
	
}
