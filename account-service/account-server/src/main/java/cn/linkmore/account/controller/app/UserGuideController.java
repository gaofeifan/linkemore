package cn.linkmore.account.controller.app;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.controller.app.response.ResUserGuide;
import cn.linkmore.account.service.UserGuideService;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.util.ObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/app/user_guide")
@Api(value = "用户指南", produces = "application/json")
public class UserGuideController {
	@Resource
	private UserGuideService userGuideService;

	@ApiOperation(value = "列表", notes = "列表", consumes = "application/json")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<List<ResUserGuide>> list(HttpServletRequest request) {
		String language = request.getHeader("lan");
		List<cn.linkmore.account.response.ResUserGuide> list = userGuideService.find(language);
		List<ResUserGuide> result = new ArrayList<>();
		for (cn.linkmore.account.response.ResUserGuide resUserGuide : list) {
			ResUserGuide guide = ObjectUtils.copyObject(resUserGuide, new ResUserGuide());
			result.add(guide);
		}
		return ResponseEntity.success(result, request);
	}

}
