package cn.linkmore.enterprise.controller.ops;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentEnt;
import cn.linkmore.enterprise.service.RentEntService;

@Controller
@RequestMapping("/ops/rent-ent")
public class RentEntController {

	@Resource
	private RentEntService rentEntService;
	
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.rentEntService.findPage(pageable);
	}
	
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqRentEnt ent) {
		this.rentEntService.save(ent);
	}
	
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqRentEnt ent) {
		this.rentEntService.update(ent);
	}
	
	@RequestMapping(value = "/v2.0/ids", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids) {
		this.rentEntService.delete(ids);
	}
	
	@RequestMapping(value = "/v2.0/stall-company", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage stallListCompany(@RequestBody ViewPageable pageable) {
		return this.rentEntService.stallListCompany(pageable);
	}
	
	@RequestMapping(value = "/v2.0/tree", method = RequestMethod.GET)
	@ResponseBody
	public List<Tree> tree(@RequestParam("entId") Long entId) {
		return this.rentEntService.tree(entId);
	}
	
	/**
	 * 更改状态 开启/关闭
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/v2.0/update_status", method = RequestMethod.POST)
	@ResponseBody
	public int updateStatus(@RequestBody Map<String, Object> map) {
		return this.rentEntService.updateStatus(map);
	}
}
