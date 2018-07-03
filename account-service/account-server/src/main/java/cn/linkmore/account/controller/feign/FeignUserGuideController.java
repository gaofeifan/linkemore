package cn.linkmore.account.controller.feign;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.request.ReqCheck;
import cn.linkmore.account.request.ReqUserGuide;
import cn.linkmore.account.response.ResUserGuide;
import cn.linkmore.account.service.UserGuideService;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import io.swagger.annotations.ApiOperation;

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
	
	@ApiOperation(value = "新增", notes = "新增", consumes = "application/json")
	@RequestMapping(method = RequestMethod.POST)
	public void save(@RequestBody ReqUserGuide userGuide) {
		this.userGuideService.save(userGuide);
	}

	@ApiOperation(value = "更新", notes = "更新", consumes = "application/json")
	@RequestMapping(method = RequestMethod.PUT)
	public void update(@RequestBody ReqUserGuide userGuide) {
		this.userGuideService.update(userGuide);
	}
	
	@ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@RequestBody List<Long> ids) {
		this.userGuideService.delete(ids);
	}
	
	@ApiOperation(value = "校验", notes = "校验", consumes = "application/json")
	@RequestMapping(value = "/check",method = RequestMethod.POST)
	public Boolean check(@RequestBody ReqCheck reqCheck) {
		Boolean check = this.userGuideService.check(reqCheck);
		return check;
	}
	@ApiOperation(value = "获取树桩数据", notes = "获取树桩数据", consumes = "application/json")
	@RequestMapping(value = "/tree",method = RequestMethod.GET)
	public Tree findTree() {
		Tree tree = this.userGuideService.findTree();
		return tree;
	}
	
	@ApiOperation(value = "获取分页数据", notes = "获取分页数据", consumes = "application/json")
	@RequestMapping(value = "/tree",method = RequestMethod.POST)
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.userGuideService.findPage(pageable); 
	} 
}
