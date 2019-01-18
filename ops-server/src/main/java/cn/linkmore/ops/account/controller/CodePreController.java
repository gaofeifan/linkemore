package cn.linkmore.ops.account.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.CodePreService;
import cn.linkmore.prefecture.request.ReqDep;

/**
 * Controller - 车场帐号
 * 
 * @author c.l
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/admin/account/code-pre")
public class CodePreController {

	@Autowired
	CodePreService codePreService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(ViewPageable pageable, HttpServletRequest request) throws IOException {
		log.info("查询列表");
		return codePreService.findPage(pageable);
	}

	@RequestMapping(value = "/record", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage record(ViewPageable pageable, HttpServletRequest request) throws IOException {
		log.info("查询列表");
		return codePreService.findRecordPage(pageable);
	}

	@RequestMapping(value = "/payList", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> payList(@RequestBody String orderNo, HttpServletRequest request)
			throws IOException {
		log.info("payList" + orderNo);
		return codePreService.payList(orderNo);
	}

	@RequestMapping(value = "/selectAll", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> selectAll() throws IOException {
		log.info("selectAll");
		return codePreService.selectAll();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqDep reqDep, HttpServletRequest request) throws IOException {
		ViewMsg msg = null;
		try {
			codePreService.save(reqDep);
			msg = new ViewMsg("操作成功", true);
		} catch (RuntimeException e) {
			msg = new ViewMsg("操作失败", false);
		} catch (Exception e) {
			msg = new ViewMsg("系统异常", false);
		}
		return msg;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody String preId) throws IOException {
		ViewMsg msg = null;
		try {
			codePreService.delete(preId);
			msg = new ViewMsg("操作成功", true);
		} catch (RuntimeException e) {
			msg = new ViewMsg("操作失败", false);
		} catch (Exception e) {
			msg = new ViewMsg("系统异常", false);
		}
		return msg;
	}

	@RequestMapping(value = "/down", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> down(@RequestBody String orderNo, HttpServletRequest request) throws IOException {
		return codePreService.down(orderNo);
	}

}
