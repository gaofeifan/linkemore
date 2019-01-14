package cn.linkmore.ops.biz.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.response.ResUserGroup;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.request.ReqBaseAppVersionGroup;
import cn.linkmore.ops.biz.service.BaseUserVersionGroupService;

@Controller
@RequestMapping("/admin/biz/user_version_group")
public class BaseUserVersionGroupController extends BaseController{
	
	@Autowired
	private BaseUserVersionGroupService baseUserVersionGroupService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.baseUserVersionGroupService.findPage(pageable);
	}
	
	/**
	 * 新增
	 * @param reqStrategyGroup
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqBaseAppVersionGroup reqBaseAppVersionGroup) {
		ViewMsg msg = null;
		try {
			reqBaseAppVersionGroup.setCreateTime(new Date());
			reqBaseAppVersionGroup.setCreateUserId(getPerson().getId());
			reqBaseAppVersionGroup.setCreateUserName(getPerson().getUsername());
			this.baseUserVersionGroupService.save(reqBaseAppVersionGroup);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.baseUserVersionGroupService.delete(ids);
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/group_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResUserGroup> userGroupList(@RequestParam Map<String, Object> map) {
		return baseUserVersionGroupService.userGroupList(map);
	}
	
}
