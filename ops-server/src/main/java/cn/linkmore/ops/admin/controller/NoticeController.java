package cn.linkmore.ops.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.request.ReqCreateNotice;
import cn.linkmore.ops.admin.response.ResNotice;
import cn.linkmore.ops.admin.service.NoticeService;
import io.swagger.annotations.Api;

@Api(value = "通知类消息", produces = "application/json")
@RestController
@RequestMapping(value = "/admin/admin/notice")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	/**
	 * 获取列表
	 */
	@RequestMapping(value = "/select_list", method = RequestMethod.POST)
	public ViewPage selectList(HttpServletRequest request,ViewPageable pageable){
		ViewPage selectList = this.noticeService.selectList(pageable); 
		return selectList;
	}
	
	/**
	 * 添加
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ViewMsg save( ReqCreateNotice noticeBean,HttpServletRequest request){
		ViewMsg msg = null;
		try {
			noticeService.saveNotice(noticeBean);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(RuntimeException r){
			msg = new ViewMsg(r.getMessage(), false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
	}
	
	/**
	 * 详情
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<ResNotice> detail(@PathVariable("id")Long id){
		ResNotice bean = noticeService.selectById(id);
		return ResponseEntity.ok(bean);
	}
	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ViewMsg update(ReqCreateNotice noticeBean,HttpServletRequest request){
		ViewMsg msg = null;
		try {
			noticeService.updateNotice(noticeBean);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(RuntimeException r){
			msg = new ViewMsg(r.getMessage(), false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
	}
	/**
	 * 推送
	 */
	@RequestMapping(value = "/push", method = RequestMethod.POST)
	public ViewMsg detail(@RequestBody List<Long> ids,HttpServletRequest request){
		ViewMsg msg = null;
		try {
			noticeService.pushNotice(ids);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(RuntimeException r){
			msg = new ViewMsg(r.getMessage(), false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
	}
}

