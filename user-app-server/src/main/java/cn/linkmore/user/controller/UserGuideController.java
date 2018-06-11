package cn.linkmore.user.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.user.response.ResUserGuide;
import cn.linkmore.user.service.UserGuideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("user_guide")
@Api(value = "用户指南", produces = "application/json")
public class UserGuideController {
	@Resource
	private UserGuideService userGuideService;

	@ApiOperation(value = "列表", notes = "列表", consumes = "application/json")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<List<ResUserGuide>> list(HttpServletRequest request) {
		String language = request.getHeader("lan");
		List<ResUserGuide> list = userGuideService.find(language);
		return ResponseEntity.success(list, request);
	}

}
