package cn.linkmore.enterprise.controller.staff;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.annotation.AopIgnore;
import cn.linkmore.bean.common.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller -车区【管理版】
 * @author 
 * @version 2.0
 *
 */
@Api(tags = "ManagePrefecture", description = "车区【管理版】")
@RestController
@RequestMapping("/staff/pre")
public class StaffPrefectureController {

	@RequestMapping(value = "/demo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="demo",notes="demo", consumes = "application/json")
	@AopIgnore
	public ResponseEntity<?> upload(HttpServletRequest request) {
		
		return ResponseEntity.success("demo",request );
	}
	
	
	
	
	
}
