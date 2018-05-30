package cn.linkmore.ops.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.service.ShareService;
/**
 * 分享记录
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/share")
public class ShareController {

	@Resource
	private ShareService shareService;

	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return shareService.findPage(pageable);
	}
}
