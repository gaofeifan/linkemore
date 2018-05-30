package cn.linkmore.account.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.request.ReqFeedBack;
import cn.linkmore.account.response.ResFeedBack;
import cn.linkmore.account.service.FeedbackService;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 问题反馈
 * @author   GFF
 * @Date     2018年5月16日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/feedback")
public class FeedBackController {
	@Resource
	private FeedbackService feedbackService;


	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable) {
		return this.feedbackService.findPage(pageable);
	}

	@RequestMapping(value = "/v2.0/export", method = RequestMethod.POST)
	@ResponseBody
	public List<ResFeedBack> exportList(@RequestBody ReqFeedBack bean) {
		return this.feedbackService.exportList(bean);
	}
}
