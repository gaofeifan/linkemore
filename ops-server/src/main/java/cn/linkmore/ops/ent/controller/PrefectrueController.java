package cn.linkmore.ops.ent.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqAddEntPreture;
import cn.linkmore.enterprise.response.ResEntPrefecture;
import cn.linkmore.ops.ent.service.PrefectrueService;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.security.response.ResPerson;
@RequestMapping(value = "/admin/ent/prefectrue")
@Controller
public class PrefectrueController {
	
	@Resource
	private PrefectrueService prefectrueService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.prefectrueService.findPage(pageable);
	}
	@RequestMapping(value = "/all", method = RequestMethod.POST)
	@ResponseBody
	public List<ResEntPrefecture> findAll(HttpServletRequest request,Long entId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("entId", entId);
		return this.prefectrueService.findAll(map);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqAddEntPreture auth,HttpServletRequest request) {
		ViewMsg msg = null;
		try {
			
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person"); 
			auth.setOperatorId(person.getId());
			auth.setOperatorName(person.getUsername());
			this.prefectrueService.save(auth);
			msg = new ViewMsg("保存成功", true);
		} catch (BusinessException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqAddEntPreture auth) {
		ViewMsg msg = null;
		try {
			this.prefectrueService.update(auth);
			msg = new ViewMsg("保存成功", true);
		} catch (BusinessException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids) {
		ViewMsg msg = null;
		try {
			this.prefectrueService.delete(ids);
			msg = new ViewMsg("刪除成功", true);
		} catch (BusinessException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}
	
	/**
	 * @Description  查询未被创建的车区
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/find-ent-pre", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreList> findNotCreateEntPre(){
		return this.prefectrueService.findNotCreateEntPre();
	}
}