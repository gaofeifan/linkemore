package cn.linkmore.prefecture.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqOrderOperateLogExcel;
import cn.linkmore.prefecture.response.ResOrderOperateLogEntity;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.service.OrderOperateLogService;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.prefecture.service.StallService;

/**
 * Controller - 车位订单操作记录
 * @author jiaohanbin
 */
@Controller
@RequestMapping("/order_operate")
public class OrderOperateLogController {
	
	@Autowired
	private OrderOperateLogService orderOperateService;
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.orderOperateService.findPage(pageable);
	}
	/**
	 * 详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrderOperateLogEntity> detail(@RequestParam("id") Long id){
		return this.orderOperateService.findListById(id);
	}
	/**
	 * 导出
	 */
	@RequestMapping(value = "/v2.0/export", method = RequestMethod.POST)
	@ResponseBody
	public List<ResOrderOperateLogEntity> export(@RequestBody ReqOrderOperateLogExcel bean){
		List<ResOrderOperateLogEntity> list = this.orderOperateService.exportList(bean);
		return list;
	}
	
}
