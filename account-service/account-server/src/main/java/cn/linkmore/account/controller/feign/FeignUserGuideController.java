package cn.linkmore.account.controller.feign;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.response.ResUserGuide;
import cn.linkmore.account.service.UserGuideService;

@RestController
@RequestMapping("/feign/user_guide")
public class FeignUserGuideController {
	@Resource
	private UserGuideService userGuideService;
	
	@RequestMapping(value = "/v2.0/detail/{language}", method = RequestMethod.GET)
	public List<ResUserGuide> list(@PathVariable("language") String language){
		List<ResUserGuide> list = userGuideService.find(language);
		return list;
	}
	
}
