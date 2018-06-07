package cn.linkmore.order.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.request.ReqRechargeRecordExcel;
import cn.linkmore.order.response.ResRechargeRecordExcel;
import cn.linkmore.order.service.RechargeRecordService;

/**
 * 储值记录
 * 
 * @author GFF
 * @Date 2018年5月18日
 * @Version v2.0
 */

@RestController
@RequestMapping("/record")
public class RechargeRecordController {

	@Resource
	private RechargeRecordService rechargeRecordService;
	/**
	 * @Description 分页查询
	 * @Author GFF
	 * @Version v2.0
	 */
	@RequestMapping(value="/v2.0/page",method=RequestMethod.POST)
	public ViewPage findPage(@RequestBody ViewPageable pageable) {
		return this.rechargeRecordService.findPage(pageable);
	}

	/**
	 * @Description 查询导出数据
	 * @Author GFF
	 * @Version v2.0
	 */
	@RequestMapping(value="/v2.0/export",method=RequestMethod.POST)
	@ResponseBody
	public List<ResRechargeRecordExcel> findExportList(@RequestBody ReqRechargeRecordExcel bean){
		return this.findExportList(bean);
	}

}
