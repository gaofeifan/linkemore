package cn.linkmore.prefecture.controller.ops;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPrefectureGroup;
import cn.linkmore.prefecture.service.PreGroupService;

/**
 * 专区分组
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Controller
@RequestMapping("/ops/pre_group")
public class PrefectureGroupController {

	@Autowired
	private PreGroupService preGroupService;

	/**
	 * 检查名称重复
	 */
	@RequestMapping(value = "/v2.0/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck reqCheck) {
		Boolean flag = true;
		Integer count = this.preGroupService.check(reqCheck);
		if (count > 0) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public int save(@RequestBody ReqPrefectureGroup preGroup) {
		return this.preGroupService.save(preGroup);
	}
	
	/**
	 * 信息列表
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.preGroupService.findPage(pageable);
	}
	
	/**
	 * 启用
	 */
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.POST)
	@ResponseBody
	public int delete(@RequestBody List<Long> ids) {
		return this.preGroupService.start(ids);
	}
	
	/**
	 * 启用
	 */
	@RequestMapping(value = "/v2.0/start", method = RequestMethod.POST)
	@ResponseBody
	public int start(@RequestBody List<Long> ids) {
		return this.preGroupService.start(ids);
	}
	/**
	 * 禁用
	 */
	@RequestMapping(value = "/v2.0/down", method = RequestMethod.POST)
	@ResponseBody
	public int down(@RequestBody List<Long> ids) {
		return	this.preGroupService.down(ids);
	}
}
