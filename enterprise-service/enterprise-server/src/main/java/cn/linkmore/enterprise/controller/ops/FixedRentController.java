package cn.linkmore.enterprise.controller.ops;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqFixedRent;
import cn.linkmore.enterprise.response.ResFixedRent;
import cn.linkmore.enterprise.response.ResStall;
import cn.linkmore.enterprise.service.FixedRentService;


@RestController
@RequestMapping("/ops/fixed/rent")
public class FixedRentController {
	@Autowired
	private FixedRentService fixedRentService;
	
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.fixedRentService.findPage(pageable);
	}
	
	@RequestMapping(value = "/stall_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> stallList(@RequestBody Map<String, Object> map){
		return this.fixedRentService.stallList(map);
	}
	
	@RequestMapping(value = "/free_stall_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> freeStallList(@RequestBody Map<String, Object> map){
		return this.fixedRentService.freeStallList(map);
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public String check(@RequestBody ReqFixedRent reqFixedRent) {
		return this.fixedRentService.check(reqFixedRent);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public int insert(@RequestBody ReqFixedRent reqFixedRent) {
		return this.fixedRentService.insert(reqFixedRent);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public int update(@RequestBody ReqFixedRent reqFixedRent) {
		return this.fixedRentService.update(reqFixedRent);
	}
	
	@RequestMapping(value = "/delete/stall", method = RequestMethod.POST)
	@ResponseBody
	public int deleteStall(@RequestBody Map<String, Object> map) {
		return this.fixedRentService.deleteStall(map);
	}

	@RequestMapping(value = "/status/open", method = RequestMethod.POST)
	@ResponseBody
	public int open(@RequestBody Map<String, Object> map) {
		return this.fixedRentService.open(map);
	}
	
	@RequestMapping(value = "/status/close", method = RequestMethod.POST)
	@ResponseBody
	public int close(@RequestBody Map<String, Object> map) {
		return this.fixedRentService.close(map);
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.POST)
	@ResponseBody
	public ResFixedRent view(@RequestBody Long fixedId) {
		return this.fixedRentService.view(fixedId);
	}
	
	
	
}
