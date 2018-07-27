package cn.linkmore.ops.biz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import cn.linkmore.enterprise.request.ReqEntBrandStall;
import cn.linkmore.ops.biz.service.EntBrandStallService;
import cn.linkmore.ops.biz.service.EnterpriseService;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.response.ResStall;
/**
 * 企业品牌车区
 * @author   jiaohanbin
 * @Date     2018年6月25日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/biz/ent-brand-stall")
public class EntBrandStallController {
	@Resource
	private EnterpriseService enterpriseService;
	
	@Resource
	private EntBrandStallService entBrandStallService;
	
	@Resource 
	private StallClient stallClient;

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree(HttpServletRequest request) {
		return entBrandStallService.findTree();
	}
	
	@RequestMapping(value = "/stall-list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> stallList(@RequestBody Long preId) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("preId", preId);
		param.put("brand", 0);
		return stallClient.findStallList(param);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqEntBrandStall record) {
		ViewMsg msg = null;
		try {
			this.entBrandStallService.save(record);
			msg = new ViewMsg("保存成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败", false);
		}
		return msg;

	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqEntBrandStall record) {
		ViewMsg msg = null;
		try {
			this.entBrandStallService.update(record);
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
			this.entBrandStallService.delete(ids);
			msg = new ViewMsg("删除成功", true);
		} catch (DataException e) {
			msg = new ViewMsg(e.getMessage(), false);
		} catch (Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败", false);
		}
		return msg;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.entBrandStallService.findPage(pageable);
	}
}
