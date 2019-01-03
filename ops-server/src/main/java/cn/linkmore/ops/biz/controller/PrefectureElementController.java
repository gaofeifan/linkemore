package cn.linkmore.ops.biz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.PrefectureElementService;
import cn.linkmore.ops.biz.service.StallService;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPrefectureElement;
import cn.linkmore.security.response.ResPerson;

@Controller
@RequestMapping("/admin/biz/prefecture_ele")
public class PrefectureElementController {

	@Resource
	private PrefectureElementService prefectureElementService;
	
	@Resource
	private StallService stallService;
	

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		ResPerson person = (ResPerson)subject.getSession().getAttribute("person");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", "id");
		param.put("value", person.getId());
		return stallService.findTree(param);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return prefectureElementService.findPage(pageable);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(HttpServletRequest request, ReqPrefectureElement ele) {
		ViewMsg msg = null;
		try {
			this.prefectureElementService.save(ele);
			msg = new ViewMsg("保存成功", true);
		} catch (RuntimeException e) {
			msg = new ViewMsg("保存失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(HttpServletRequest request, ReqPrefectureElement ele) {
		ViewMsg msg = null;
		try {
			this.prefectureElementService.update(ele);
			msg = new ViewMsg("修改成功", true);
		} catch (RuntimeException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			msg = new ViewMsg("修改失败", false);
			return msg;
		}
		return msg;
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(HttpServletRequest request, String stallName, Long preId, Long id) {
		boolean flag = true;
		ReqCheck reqCheck = new ReqCheck();
		reqCheck.setProperty(stallName);
		reqCheck.setValue(preId.toString());
		reqCheck.setId(id);
		int check = this.stallService.check(reqCheck);
		if (check > 0) {
			flag = false;
		}
		return flag;
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
			this.prefectureElementService.delete(ids);
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}	

	/*@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResStallEntity detail(HttpServletRequest request, Long id) {
		return stallService.findById(id);
	}*/
}
