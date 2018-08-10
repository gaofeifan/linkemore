package cn.linkmore.ops.biz.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.FeeStrategyService;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStrategyBase;


/**
 * @Description - 计费策略
 * @Author jiaohanbin
 * @Version v1.0.0
 */
@Controller
@RequestMapping("/admin/biz/fee_strategy")
public class FeeStrategyController {

	@Resource
	private FeeStrategyService feeStrategyService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqStrategyBase record) {
		ViewMsg msg = null;
		try {
			this.feeStrategyService.save(record);
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
	public ViewMsg update(ReqStrategyBase record) {
		ViewMsg msg = null;
		try {
			this.feeStrategyService.update(record);
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
			this.feeStrategyService.delete(ids);
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
		return this.feeStrategyService.check(reqCheck);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.feeStrategyService.findPage(pageable);
	}
}
