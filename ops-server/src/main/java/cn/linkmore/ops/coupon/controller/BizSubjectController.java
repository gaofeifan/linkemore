package cn.linkmore.ops.coupon.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import cn.linkmore.coupon.request.ReqBizSubject;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.response.ResBizSubjectBean;
import cn.linkmore.coupon.response.ResCombo;
import cn.linkmore.coupon.response.ResValuePack;
import cn.linkmore.ops.coupon.service.BizSubjectService;

@Controller
@RequestMapping("/admin/coupon_subject")
public class BizSubjectController {

	@Autowired
	private BizSubjectService bizSubjectService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqBizSubject record) {
		ViewMsg msg = null;
		try {
			this.bizSubjectService.save(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResBizSubjectBean detail(@RequestBody Long id) {
		ResBizSubjectBean subject = this.bizSubjectService.findById(id);
		return subject;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqBizSubject record) {
		ViewMsg msg = null;
		try {
			this.bizSubjectService.update(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
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
			this.bizSubjectService.delete(ids.get(0));
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(ReqCheck reqCheck) {
		return this.bizSubjectService.check(reqCheck);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.bizSubjectService.findPage(pageable);
	}
	
	/*
	 * 上线
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody Long id) {
		ViewMsg msg = null;
		try {
			this.bizSubjectService.start(id);
			msg = new ViewMsg("启用成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("启用失败", false);
		}
		return msg;
	}

	/*
	 * 下线
	 */
	@RequestMapping(value = "/stop", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg down(@RequestBody Long id) {
		ViewMsg msg = null;
		try {
			this.bizSubjectService.stop(id);
			msg = new ViewMsg("暂停成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("暂停失败", false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/combo_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResCombo> comboList(HttpServletRequest request,Long comboType){
		return this.bizSubjectService.comboList(comboType);
	} 
	
	@RequestMapping(value = "/pack_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResValuePack> packList(HttpServletRequest request,Long comboType){
		return this.bizSubjectService.packList(comboType);
	} 

}
