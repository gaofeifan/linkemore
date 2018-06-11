package cn.linkmore.account.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.request.ReqNotice;
import cn.linkmore.account.request.ReqPageNotice;
import cn.linkmore.account.response.ResNotice;
import cn.linkmore.account.response.ResPage;
import cn.linkmore.account.service.NoticeService;

/**
 * 消息的Controller
 */
@Controller
@RequestMapping("notice")
public class NoticeController{

	@Resource
	private NoticeService noticeService;

	/**
	 * 消息分页列表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ResPage listNotice(@RequestBody ReqPageNotice bean) {
		ResPage resPage = noticeService.page(bean);
		return resPage;
	}

	/**
     *阅读
     */
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    @ResponseBody
    public ResNotice read(@RequestBody ReqNotice notice) {
      return this.noticeService.read(notice);
    }

	/**
	 * 删除
	 */
	@RequestMapping( method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody ReqNotice notice) {
		noticeService.delete(notice);
	}

}
