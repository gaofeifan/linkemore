package cn.linkmore.ops.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.request.ReqCheck;
import cn.linkmore.ops.admin.request.ReqTargetSetting;
import cn.linkmore.ops.admin.response.ResPreList;
import cn.linkmore.ops.admin.service.TargetSettingService;
import cn.linkmore.security.response.ResPerson;
/**
 * Controller - 车区目标设置
 * @author liwenlong
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/admin/admin/pre_target_setting")
public class TargetSettingController { 
	@Autowired
	private TargetSettingService targetSettingService;
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqTargetSetting preTargetSetting){
		ViewMsg msg = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			ResPerson person = (ResPerson)subject.getSession().getAttribute("person");
			preTargetSetting.setOperatorId(person.getId());
			preTargetSetting.setOperatorName(person.getRealname());
			this.targetSettingService.save(preTargetSetting);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
		 
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqTargetSetting preTargetSetting){
		ViewMsg msg = null;
		try {
			this.targetSettingService.update(preTargetSetting);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids){ 
		ViewMsg msg = null;
		try {
			this.targetSettingService.delete(ids);
			msg = new ViewMsg("删除成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(ReqCheck reqCheck){
        return this.targetSettingService.check(reqCheck);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){
		return this.targetSettingService.findPage(pageable); 
	} 
	
	@RequestMapping(value = "/prefecture_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResPreList> prefectureList(HttpServletRequest request){
		return this.targetSettingService.findPrefectureList();
	} 
}
