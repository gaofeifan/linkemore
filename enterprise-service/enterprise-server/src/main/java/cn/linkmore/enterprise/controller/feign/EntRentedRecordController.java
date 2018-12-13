package cn.linkmore.enterprise.controller.feign;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.enterprise.entity.EntRentedRecord;
import cn.linkmore.enterprise.response.ResEntRentedRecord;
import cn.linkmore.enterprise.service.EntRenedRecordService;
import cn.linkmore.util.BeanUtils;
import cn.linkmore.util.ObjectUtils;

@Controller
@RequestMapping("/feign/ent-rented")
public class EntRentedRecordController {

	@Resource
	private EntRenedRecordService entRenedRecordService;
	
	@RequestMapping(value = "/down-time", method = RequestMethod.GET)
	@ResponseBody
	public void updateDownTime(@RequestParam("stallId") Long stallId) {
		this.entRenedRecordService.updateDownTime(stallId);
	}
	
	@RequestMapping(value = "/last-plate-number", method = RequestMethod.GET)
	@ResponseBody
	public ResEntRentedRecord findLastPlateNumber(@RequestParam("stallId") Long stallId) {
		EntRentedRecord record = this.entRenedRecordService.findLastPlateNumber(stallId);
		return ObjectUtils.copyObject(record, new ResEntRentedRecord());
	}
	
	@RequestMapping(value = "/last-plate-number-by-pre", method = RequestMethod.GET)
	@ResponseBody
	public List<ResEntRentedRecord> findLastPlateNumberByPreId(@RequestParam("preId") Long preId){
		return this.entRenedRecordService.findLastPlateNumberByPreId(preId);
		
	}
}
