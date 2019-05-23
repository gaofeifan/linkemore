package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.enterprise.request.ReqPreDetails;
import cn.linkmore.enterprise.request.ReqStaffPreOwnerStall;
import cn.linkmore.enterprise.response.ResEntRentedRecord;
import cn.linkmore.enterprise.response.ResOwnerStallDetails;
import cn.linkmore.enterprise.response.ResOwnerStallReportForms;
import cn.linkmore.enterprise.response.ResStaffOwnerUseStall;
import cn.linkmore.prefecture.client.EntRentedRecordClient;
@Component
public class EntRentedRecordClientHystrix implements EntRentedRecordClient {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public void updateDownTime(Long stallId) {
		log.info("void updateDownTime(Long stallId)) hystrix");
	}
	@Override
	public ResEntRentedRecord findLastPlateNumber(Long stallId) {
		log.info("ResEntRentedRecord findLastPlateNumber(Long stallId) ) hystrix");
		return null;
	}
	@Override
	public List<ResEntRentedRecord> findLastPlateNumberByPreId(Long preId) {
		log.info("List<ResEntRentedRecord> findLastPlateNumberByPreId(Long preId) hystrix");
		return null;
	}
	@Override
	public ResEntRentedRecord findByUserId(Long userId) {
		log.info("ResEntRentedRecord findByUserId(Long userId) hystrix");
		return null;
	}
	@Override
	public List<ResStaffOwnerUseStall> findPreUseNumber(ReqStaffPreOwnerStall reqStaffPreOwnerStall) {
		log.info("findPreUseNumber(ReqStaffPreOwnerStall reqStaffPreOwnerStall) hystrix");
		return null;
	}
	@Override
	public ResOwnerStallDetails findPreDetails(ReqPreDetails reqPreDetails) {
		log.info("ResOwnerStallDetails findPreDetails(ReqPreDetails reqPreDetails) hystrix");
		return null;
	}
	@Override
	public ResOwnerStallReportForms findOwnerStallReportForms(ReqPreDetails details) {
		log.info("ResOwnerStallReportForms findOwnerStallReportForms(ReqPreDetails details) hystrix");
		return null;
	}

	
	
}
