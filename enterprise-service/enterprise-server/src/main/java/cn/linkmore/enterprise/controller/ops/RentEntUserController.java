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
import cn.linkmore.enterprise.request.ReqRentUser;
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
	@RequestMapping(value = "/v2.0/i-list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage listI(@RequestBody ViewPageable pageable) {
		return this.rentEntUserService.findPageI(pageable);
	}
	
	@RequestMapping(value = "/v2.0/i", method = RequestMethod.POST)
	@ResponseBody
	public void saveI(@RequestBody ReqRentUser ent) {
		this.rentEntUserService.saveI(ent);
	}
	
	@RequestMapping(value = "/v2.0/i", method = RequestMethod.PUT)
	@ResponseBody
	public void updateI(@RequestBody ReqRentUser ent) {
		this.rentEntUserService.updateI(ent);
	}
	
	@RequestMapping(value = "/v2.0/i-ids", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteI(@RequestBody List<Long> ids) {
		this.rentEntUserService.deleteIdsI(ids);
	}
	
	@RequestMapping(value = "/v2.0/exists", method = RequestMethod.POST)
	@ResponseBody
	public boolean exists(@RequestBody ReqRentEntUser ent) {
		return this.rentEntUserService.exists(ent);
	}
	
	@RequestMapping(value = "/v2.0/sync/byCompanyId", method = RequestMethod.POST)
	@ResponseBody
	public void syncRentStallByCompanyId(@RequestBody Long companyId) {
		this.rentEntUserService.syncRentStallByCompanyId(companyId);
	}
	
	@RequestMapping(value = "/v2.0/sync/byUserId", method = RequestMethod.POST)
	@ResponseBody
	public void syncRentStallByUserId(@RequestBody Long userId) {
		this.rentEntUserService.syncRentStallByUserId(userId);
	}
	
	@RequestMapping(value = "/v2.0/sync/personal/byPlate", method = RequestMethod.POST)
	@ResponseBody
	public void syncRentPersonalUserStallByPlate(@RequestBody String plate) {
		this.rentEntUserService.syncRentPersonalUserStallByPlate(plate);
	}
	
}
