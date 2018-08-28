package cn.linkmore.prefecture.controller.ops;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqStallOperateLog;
import cn.linkmore.prefecture.request.ReqStallOperateLogExcel;
import cn.linkmore.prefecture.response.ResStallOperateLog;
import cn.linkmore.prefecture.service.StallOperateLogService;

/**
 * Controller - 车位上下线
 * @author jiaohanbin
 * @version 2.0
 */
@Controller
@RequestMapping("/ops/stall_operate")
public class StallOperateLogController {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
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
	@ResponseBody
	public List<ResStallOperateLog> export(@RequestBody ReqStallOperateLogExcel bean){
		List<ResStallOperateLog> list = this.stallOperateService.exportList(bean);
		log.info("stall operate log export list-------------"+ list.size());
		return list;
	}
	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqStallOperateLog sol) {
		this.stallOperateService.save(sol);
	}
	
	@RequestMapping(value = "/v2.0/stall", method = RequestMethod.GET)
	@ResponseBody
	public ResStallOperateLog findByStallId(@RequestParam("stallId") Long stallId) {
		return this.stallOperateService.findByStallId(stallId);
	}
}
