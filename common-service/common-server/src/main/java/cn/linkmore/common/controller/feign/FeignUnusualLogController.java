package cn.linkmore.common.controller.feign;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.request.ReqUnusualLog;
import cn.linkmore.common.service.UnusualLogService;
import cn.linkmore.util.ObjectUtils;

/**
 * app异常日志上报
 * @Version 2.0
 * @author  GFF
 * @Date     2018年5月11日
 */
@RestController
@RequestMapping("/feign/app_log")
public class FeignUnusualLogController {
	
	@Resource
	private UnusualLogService unusualLogService;
	
	/**
	 * 新增异常日志上报
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void insert(@RequestBody ReqUnusualLog unusualLog) {
		this.unusualLogService.insert(ObjectUtils.copyObject(unusualLog, new cn.linkmore.common.controller.app.request.ReqUnusualLog()));
	}
	/**
	 * @Description  查询分页管理版
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/list-page",method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findPage(@RequestBody ViewPageable pageable) {
		return this.unusualLogService.findPage(pageable);
	}

}
