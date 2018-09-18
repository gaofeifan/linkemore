package cn.linkmore.account.controller.staff;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.controller.staff.response.ResCenter;
import cn.linkmore.account.service.StaffCenterService;
import cn.linkmore.bean.common.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Staff-Center", description = "个人中心【管理版】")
@RestController
@RequestMapping("/staff/center")
@Validated
public class StaffCenterController {

	@Resource
	private StaffCenterService staffCenterService;

	@ApiOperation(value = "个人资料详情", notes = "个人资料详情")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResCenter> detail(HttpServletRequest request) {
		ResCenter center = staffCenterService.getCenter(request);
		return ResponseEntity.success(center, request);
	}
}
