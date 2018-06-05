package cn.linkmore.ops.biz.controller;

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
import cn.linkmore.ops.base.service.BaseVersionService;
import cn.linkmore.ops.biz.service.BaseUserVersionService;
import cn.linkmore.ops.request.ReqBaseVersion;


/**
 * 版本管理
 * @author   GFF
 * @Date     2018年5月28日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/biz/user_version")
public class BaseUserVersionController {
	
	@Autowired
	private BaseUserVersionService userVersionService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return this.userVersionService.findPage(pageable);
	}
}
