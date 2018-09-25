package cn.linkmore.ops.biz.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.request.ReqUnusualLog;
import cn.linkmore.ops.biz.service.UnusualLogService;
import cn.linkmore.util.ObjectUtils;

/**
 * 异常日志上报
 * @Version 2.0
 * @author  GFF
 * @Date     2018年5月11日
 */
@RestController
@RequestMapping("/admin/common/unusual")
public class StaffUnusualLogController {
	
	@Resource
	private UnusualLogService unusualLogService;
	
	/**
	 * @Description  查询分页管理版
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/list",method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findPage( ViewPageable pageable) {
		return this.unusualLogService.findPage(pageable);
	}

}
