package cn.linkmore.prefecture.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.enterprise.request.ReqPreDetails;
import cn.linkmore.enterprise.request.ReqStaffPreOwnerStall;
import cn.linkmore.enterprise.response.ResEntRentedRecord;
import cn.linkmore.enterprise.response.ResOwnerStallDetails;
import cn.linkmore.enterprise.response.ResOwnerStallReportForms;
import cn.linkmore.enterprise.response.ResStaffOwnerUseStall;
import cn.linkmore.feign.FeignConfiguration;
import cn.linkmore.prefecture.client.hystrix.EntRentedRecordClientHystrix;
@FeignClient(value = "enterprise-server", path = "/feign/ent-rented", fallback=EntRentedRecordClientHystrix.class,configuration = FeignConfiguration.class)
public interface EntRentedRecordClient {
	
	@RequestMapping(value = "/find-user-id", method = RequestMethod.GET)
	@ResponseBody
	
	public ResEntRentedRecord findByUserId(@RequestParam("userId") Long userId);

	@RequestMapping(value = "/down-time", method = RequestMethod.GET)
	@ResponseBody
	public void updateDownTime(@RequestParam("stallId") Long stallId);
	
	@RequestMapping(value = "/last-plate-number", method = RequestMethod.GET)
	@ResponseBody
	public ResEntRentedRecord findLastPlateNumber(@RequestParam("stallId") Long stallId);

	@RequestMapping(value = "/last-plate-number-by-pre", method = RequestMethod.GET)
	@ResponseBody
	public List<ResEntRentedRecord> findLastPlateNumberByPreId(@RequestParam("preId") Long preId);

	@RequestMapping(value = "/pre-user-number", method = RequestMethod.POST)
	@ResponseBody
	public List<ResStaffOwnerUseStall> findPreUseNumber(@RequestBody ReqStaffPreOwnerStall reqStaffPreOwnerStall);

	@RequestMapping(value = "/pre-owner-details", method = RequestMethod.POST)
	@ResponseBody
	public ResOwnerStallDetails findPreDetails(@RequestBody ReqPreDetails reqPreDetails);

	@RequestMapping(value = "/owner-stall-report-forms", method = RequestMethod.POST)
	@ResponseBody
	public ResOwnerStallReportForms findOwnerStallReportForms(@RequestBody ReqPreDetails details);

}
