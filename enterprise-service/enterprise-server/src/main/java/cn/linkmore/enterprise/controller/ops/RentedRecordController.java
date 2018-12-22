package cn.linkmore.enterprise.controller.ops;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqRentedRecord;
import cn.linkmore.enterprise.response.ResRentedRecord;
import cn.linkmore.enterprise.service.RentedRecordService;

/**
 * 长租用户记录
 * @author   GFF
 * @Date     2018年7月30日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/rented-record")
public class RentedRecordController {

	@Resource
	private RentedRecordService rentedRecordService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findList(@RequestBody ViewPageable pageable) {
		return this.rentedRecordService.findList(pageable);
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	public List<ResRentedRecord> exportList(@RequestBody ReqRentedRecord bean){
		return this.rentedRecordService.exportList(bean);
	}
}
