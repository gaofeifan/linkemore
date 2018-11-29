package cn.linkmore.ops.ent.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentEnt;
import cn.linkmore.ops.ent.service.RentEntService;
import cn.linkmore.prefecture.response.ResStall;

@RequestMapping(value="/admin/ent/rent-ent")
@Controller
public class RentEntController {

	@Resource
	private RentEntService rentEntService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.rentEntService.findPage(pageable);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void save(HttpServletRequest request, cn.linkmore.ops.ent.request.ReqRentEnt ent) {
		
		this.rentEntService.save(ent);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public void update(HttpServletRequest request, ReqRentEnt ent) {
		this.rentEntService.update(ent);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public void delete(HttpServletRequest request,@RequestBody List<Long> ids) {
		this.rentEntService.delete(ids);
	}
	@RequestMapping(value = "/stall-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> stallList(HttpServletRequest request) {
		return this.rentEntService.stallList(request);
	}
	@RequestMapping(value = "/stall-list-company", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage stallListCompany(HttpServletRequest request, ViewPageable pageable) {
		return this.rentEntService.stallListCompany(pageable);
	}
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public List<Tree> tree(HttpServletRequest request) {
		return this.rentEntService.tree();
	}
	
}
