package cn.linkmore.prefecture.controller.feign;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqApplicationGroup;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.service.ApplicationGroupService;
@Controller
@RequestMapping("/feign/application_group")
public class FeignApplicationGroupController {

	@Resource
	private ApplicationGroupService groupService;

	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return groupService.findPage(pageable);
	}
	/*
	 * 新增
	 */
	@RequestMapping( method = RequestMethod.POST)
	@ResponseBody
	public void add(@RequestBody ReqApplicationGroup requestBean){
		this.groupService.add(requestBean);
	}

	/*
	 * 启用
	 */
	@RequestMapping(value = "/start", method = RequestMethod.PUT)
	@ResponseBody
	public void start(@RequestBody List<Long> ids) {
		this.groupService.start(ids);
	}
	/*
	 * 禁用
	 */
	@RequestMapping(value = "/down", method = RequestMethod.PUT)
	@ResponseBody
	public void down(@RequestBody List<Long> ids) {
		this.groupService.down(ids);
	}
	/*
	 * 检查名称重复
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck check) {
		Boolean flag = true;
		Integer count = this.groupService.check(check.getProperty(), check.getValue(), check.getId());
		if (count > 0) {
			flag = false;
		}
		return flag;
	}
}
