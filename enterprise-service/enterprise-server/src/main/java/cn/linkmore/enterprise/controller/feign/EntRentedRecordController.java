package cn.linkmore.enterprise.controller.feign;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.linkmore.enterprise.entity.EntRentedRecord;
import cn.linkmore.enterprise.request.ReqPreDetails;
import cn.linkmore.enterprise.request.ReqStaffPreOwnerStall;
import cn.linkmore.enterprise.response.ResEntRentedRecord;
import cn.linkmore.enterprise.response.ResOwnerStallDetails;
import cn.linkmore.enterprise.response.ResOwnerStallReportForms;
import cn.linkmore.enterprise.response.ResStaffOwnerUseStall;
import cn.linkmore.enterprise.service.EntRenedRecordService;
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
	
	@RequestMapping(value = "/find-user-id", method = RequestMethod.GET)
	@ResponseBody
	public ResEntRentedRecord findByUserId(@RequestParam("userId") Long userId) {
		EntRentedRecord record = this.entRenedRecordService.findByUserId(userId);
		if(record != null) {
			return ObjectUtils.copyObject(record, new ResEntRentedRecord());
		}
		return null;
	}
	
	@RequestMapping(value = "/last-plate-number-by-pre", method = RequestMethod.GET)
	@ResponseBody
	public List<ResEntRentedRecord> findLastPlateNumberByPreId(@RequestParam("preId") Long preId){
		return this.entRenedRecordService.findLastPlateNumberByPreId(preId);
	}
	
	@RequestMapping(value = "/pre-user-number", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStaffOwnerUseStall> findPreUseNumber(@RequestBody ReqStaffPreOwnerStall reqStaffPreOwnerStall){
		return this.entRenedRecordService.findPreUseNumber(reqStaffPreOwnerStall);
	}
	
	@RequestMapping(value = "/pre-owner-details", method = RequestMethod.POST)
	@ResponseBody
	public ResOwnerStallDetails findPreDetails(@RequestBody ReqPreDetails reqPreDetails) {
		return this.entRenedRecordService.findPreDetails(reqPreDetails);
	}

	@RequestMapping(value = "/owner-stall-report-forms", method = RequestMethod.POST)
	@ResponseBody
	public ResOwnerStallReportForms findOwnerStallReportForms(@RequestBody ReqPreDetails details) {
		return this.entRenedRecordService.findOwnerStallReportForms(details);
	}
}
