package cn.linkmore.ops.ent.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentEntUser;
import cn.linkmore.enterprise.request.ReqRentUser;
import cn.linkmore.ops.ent.service.RentEntUserService;

@RestController
@RequestMapping("/admin/ent/rent-ent-user")
public class RentEntUserController {

	@Resource
	private RentEntUserService rentEntUserService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list( ViewPageable pageable) {
		return this.rentEntUserService.findPage(pageable);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void save( ReqRentEntUser ent) {
		this.rentEntUserService.save(ent);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public void update( ReqRentEntUser ent) {
		this.rentEntUserService.update(ent);
	}
	
	@RequestMapping(value = "/ids", method = RequestMethod.POST)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids) {
		this.rentEntUserService.deleteIds(ids);
	}
	

	@RequestMapping(value = "/i-list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage listI( ViewPageable pageable) {
		return this.rentEntUserService.findPageI(pageable);
	}
	
	@RequestMapping(value = "/i-add", method = RequestMethod.POST)
	@ResponseBody
	public void saveI( ReqRentUser ent) {
		this.rentEntUserService.saveI(ent);
	}
	
	@RequestMapping(value = "/i-edit", method = RequestMethod.POST)
	@ResponseBody
	public void updateI( ReqRentUser ent) {
		this.rentEntUserService.updateI(ent);
	}
	
	@RequestMapping(value = "/i-ids", method = RequestMethod.POST)
	@ResponseBody
	public void deleteI(@RequestBody List<Long> ids) {
		this.rentEntUserService.deleteIdsI(ids);
	}
}
