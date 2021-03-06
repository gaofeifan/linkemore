package cn.linkmore.account.controller.app;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.controller.app.response.ResUserGuide;
import cn.linkmore.account.request.ReqCheck;
import cn.linkmore.account.request.ReqUserGuide;
import cn.linkmore.account.service.UserGuideService;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.util.ObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/app/user-guide")
@Api(tags = "User Guide",description="用户指南", produces = "application/json")
public class AppUserGuideController {
	@Resource
	private UserGuideService userGuideService;

	@ApiOperation(value = "列表", notes = "列表", consumes = "application/json")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<List<ResUserGuide>> list(HttpServletRequest request) {
		String language = request.getHeader("lan");
		List<cn.linkmore.account.response.ResUserGuide> list = userGuideService.find(language);
		List<ResUserGuide> result = new ArrayList<>();
		for (cn.linkmore.account.response.ResUserGuide resUserGuide : list.get(0).getChildren()) {
			ResUserGuide guide = ObjectUtils.copyObject(resUserGuide, new ResUserGuide());
			if(resUserGuide.getChildren() != null && resUserGuide.getChildren().size() != 0) {
				guide.setChildren(resUserGuide.getChildren());
			}
			result.add(guide);
		}
		return ResponseEntity.success(result, request);
	}
	
	

}
