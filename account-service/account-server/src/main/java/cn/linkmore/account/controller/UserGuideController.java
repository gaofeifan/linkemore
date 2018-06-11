package cn.linkmore.account.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.response.ResUserGuide;
import cn.linkmore.account.service.UserGuideService;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;

@RestController
@RequestMapping("user_guide")
public class UserGuideController {
	@Resource
	private UserGuideService userGuideService;
	
	@RequestMapping(value = "/v2.0/detail/{language}", method = RequestMethod.GET)
	public List<ResUserGuide> list(@PathVariable("language") String language){
		List<ResUserGuide> list = userGuideService.find(language);
		return list;
	}
	
}
