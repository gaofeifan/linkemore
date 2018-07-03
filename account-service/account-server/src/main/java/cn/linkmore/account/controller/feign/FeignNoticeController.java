package cn.linkmore.account.controller.feign;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.request.ReqCreateNotice;
import cn.linkmore.account.request.ReqNotice;
import cn.linkmore.account.response.ResNotice;
import cn.linkmore.account.response.ResNoticeBean;
import cn.linkmore.account.response.ResPage;
import cn.linkmore.account.service.NoticeService;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 消息的Controller
 */
@Controller
@RequestMapping("/feign/notice")
public class FeignNoticeController{

	@Resource
	private NoticeService noticeService;

	/**
	 * 消息分页列表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ResPage listNotice(@RequestParam("start") Long start,HttpServletRequest request) {
		ResPage resPage = noticeService.page(start,request);
		return resPage;
	}

	/**
     *阅读
     */
/*    @RequestMapping(value = "/read", method = RequestMethod.POST)
    @ResponseBody
    public ResNotice read(@RequestBody ReqNotice notice) {
      return this.noticeService.read(notice);
    }*/

	/**
	 * 删除
	 */
/*	@RequestMapping( method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody ReqNotice notice) {
		noticeService.delete(notice);
	}
*/	/**
	 * 获取列表
	 */
	@RequestMapping(value = "/select-list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage selectList(@RequestBody ViewPageable pageable){
		return this.noticeService.selectList(pageable); 
	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(@RequestBody ReqCreateNotice noticeBean){
		noticeService.saveNotice(noticeBean);
	}
	
	/**
	 * 详情
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResNoticeBean detail(@PathVariable("id")Long id){
		ResNoticeBean bean = noticeService.selectById(id);
		return bean;
	}
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public void update(@RequestBody ReqCreateNotice noticeBean){
		noticeService.updateNotice(noticeBean);
	}
	
	/**
	 * 推送
	 */
	@RequestMapping(value = "/push", method = RequestMethod.POST)
	public void push(@RequestBody List<Long> ids){
		noticeService.pushNotice(ids);
	}

}
