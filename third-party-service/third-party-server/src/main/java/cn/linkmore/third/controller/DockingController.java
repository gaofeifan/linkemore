package cn.linkmore.third.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.third.request.ReqOrder;
import cn.linkmore.third.service.DockingService;

@RestController
@RequestMapping("/docking")
public class DockingController {
	
	@Autowired
	private DockingService dockingService;
	
	@RequestMapping(value = "/v2.0/order", method = RequestMethod.POST)  
	public void order(@RequestBody ReqOrder ro) {
		this.dockingService.order(ro);
	}
}
