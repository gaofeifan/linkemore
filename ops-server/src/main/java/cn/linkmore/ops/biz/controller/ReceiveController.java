package cn.linkmore.ops.biz.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.ReceiveService;
/**
 * 领券记录
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/admin/biz_receive")
public class ReceiveController {

	@Resource
	private ReceiveService receiveService;


	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request, ViewPageable pageable) {
		return receiveService.findPage(pageable);
	}
	
	@RequestMapping(value = "/detailList", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage detailList(HttpServletRequest request, ViewPageable pageable) {
		return receiveService.findDetailPage(pageable);
	}
}
