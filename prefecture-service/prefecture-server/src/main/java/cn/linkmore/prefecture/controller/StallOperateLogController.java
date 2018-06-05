package cn.linkmore.prefecture.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqStallOperateLogExcel;
import cn.linkmore.prefecture.response.ResStallOperateLog;
import cn.linkmore.prefecture.service.StallOperateLogService;

/**
 * Controller - 车位上下线
 * @author jiaohanbin
 * @version 2.0
 */
@Controller
@RequestMapping("/stall_operate")
public class StallOperateLogController {
	
	@Autowired
	private StallOperateLogService stallOperateService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.stallOperateService.findPage(pageable);
	}
	/**
	 * 详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStallOperateLog> detail(@RequestBody Long id){
		return this.stallOperateService.findListById(id);
	}
	/**
	 * 导出
	 */
	@RequestMapping(value = "/v2.0/export", method = RequestMethod.POST)
	public List<ResStallOperateLog> export(@RequestBody ReqStallOperateLogExcel bean){
		List<ResStallOperateLog> list = this.stallOperateService.exportList(bean);
		return list;
	}
}
