package cn.linkmore.enterprise.controller.ops;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentEnt;
import cn.linkmore.enterprise.request.ReqRentEntUser;
import cn.linkmore.enterprise.service.RentEntUserService;
import cn.linkmore.util.ObjectUtils;

@RestController
@RequestMapping("/ops/rent-ent-user")
public class RentEntUserController {

	@Resource
	private RentEntUserService rentEntUserService;
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.rentEntUserService.findPage(pageable);
	}
	
	@RequestMapping(value = "/v2.0", method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqRentEntUser ent) {
		this.rentEntUserService.save(ent);
	}
	
	@RequestMapping(value = "/v2.0", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqRentEntUser ent) {
		this.rentEntUserService.update(ent);
	}
	
	@RequestMapping(value = "/v2.0/ids", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids) {
		this.rentEntUserService.deleteIds(ids);
	}
}
