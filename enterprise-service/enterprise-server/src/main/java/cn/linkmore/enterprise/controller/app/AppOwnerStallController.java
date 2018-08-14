package cn.linkmore.enterprise.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.enterprise.controller.app.response.OwnerPre;
import cn.linkmore.enterprise.service.OwnerStallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 长租车位
 * 
 * @author changlei
 * @version 1.0
 *
 */
@Api(tags = "OwnerStall", description = "企业与个人长租车位")
@RestController
@RequestMapping("/app/owner-stall")
public class AppOwnerStallController {

	@Autowired
	private OwnerStallService ownerStallServicel;

	@ApiOperation(value = "获取车位列表", notes = "根据用户身份获取已拥有车位", consumes = "application/json")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<OwnerPre>> list(@Validated HttpServletRequest request) {
			System.out.println("11" );
			List<OwnerPre>   res =    	ownerStallServicel.findStall(request);
		return ResponseEntity.success(res, request);
	}

}
