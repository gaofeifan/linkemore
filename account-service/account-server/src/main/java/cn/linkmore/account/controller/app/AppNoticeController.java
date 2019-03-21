package cn.linkmore.account.controller.app;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.request.ReqPageNotice;
import cn.linkmore.account.response.ResNotice;
import cn.linkmore.account.response.ResPage;
import cn.linkmore.account.service.NoticeService;
import cn.linkmore.bean.common.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 消息的Controller
 */
@Controller
@Api(tags = "Notice",description="消息管理", produces = "application/json")
@RequestMapping("/app/notice")
@Validated
public class AppNoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     *消息分页列表
     */
    @SuppressWarnings("rawtypes")
	@ApiOperation(value = "消息列表", notes = "消息列表", consumes = "application/json")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResPage> listNotice(@ApiParam(value = "起始请从0开始每页10条记录",required = true)@RequestParam("start") @NotNull(message = "数据不能为空") Long start , HttpServletRequest request) {
    	ResPage page = this.noticeService.page(start,request);
    	return ResponseEntity.success(page,request);
    }

    /**
     * 阅读
     */
    @ApiOperation(value = "阅读", notes = "阅读", consumes = "application/json")
    @RequestMapping(value = "/read", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ResNotice> read(@NotNull(message = "数据不能为空") @RequestParam("id")Long id, HttpServletRequest request) {
    	ResNotice notice = noticeService.read(id,request);
        return ResponseEntity.success(notice, request);
    }

    /**
     * 修改阅读状态为已读状态 
     */
    @ApiOperation(value = "修改阅读状态为已读状态 ", notes = "修改阅读状态", consumes = "application/json")
    @RequestMapping(value = "/update-read", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> updateRead(HttpServletRequest request) {
    	noticeService.updateRead(request);
        return ResponseEntity.success("更新成功", request);
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
