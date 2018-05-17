package cn.linkmore.third.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.third.request.ReqLocate;
import cn.linkmore.third.response.ResLocate;
import cn.linkmore.third.service.LocateService;

@RestController
@RequestMapping("/locate")
public class LocateController {
	
	@Autowired
	private LocateService locateService;
	
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResLocate get(@RequestBody ReqLocate req) {
		return this.locateService.getInfo(req);
	}
}
