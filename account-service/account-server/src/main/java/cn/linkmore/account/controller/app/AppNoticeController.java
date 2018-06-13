package cn.linkmore.account.controller.app;

import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import cn.linkmore.account.request.ReqNotice;
import cn.linkmore.account.request.ReqPageNotice;
import cn.linkmore.account.response.ResNotice;
import cn.linkmore.account.response.ResPage;
import cn.linkmore.account.service.NoticeService;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.util.ObjectUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 消息的Controller
 */
@Controller
@Api(value = "消息管理", produces = "application/json")
@RequestMapping("/app/notice")
public class AppNoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     *消息分页列表
     */
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "消息列表", notes = "消息列表", consumes = "application/json")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResPage> listNotice(@Validated @RequestBody ReqPageNotice bean , HttpServletRequest request) {
    	ResPage page = this.noticeService.page(bean);
    	return ResponseEntity.success(page,request);
    }

    /**
     * 阅读
     */
    @ApiOperation(value = "阅读", notes = "阅读", consumes = "application/json")
    @RequestMapping(value = "/read", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResNotice> read(@RequestParam("id")Long id, HttpServletRequest request) {
    	ResNotice notice = noticeService.read(id,request);
        return ResponseEntity.success(notice, request);
    }

    /**
     *删除
     */
    @ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Void> delete( Long nid, HttpServletRequest request) {
    	this.noticeService.delete(nid,request);
    	return ResponseEntity.success(null, request);
    }

}