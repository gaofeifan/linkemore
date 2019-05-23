package cn.linkmore.enterprise.service;

import java.util.List;
import java.util.Map;

import cn.linkmore.enterprise.entity.EntRentedRecord;
import cn.linkmore.enterprise.request.ReqPreDetails;
import cn.linkmore.enterprise.request.ReqStaffPreOwnerStall;
import cn.linkmore.enterprise.response.ResEntRentedRecord;
import cn.linkmore.enterprise.response.ResOwnerStallDetails;
import cn.linkmore.enterprise.response.ResOwnerStallReportForms;
import cn.linkmore.enterprise.response.ResStaffOwnerUseStall;

public interface EntRenedRecordService {

	/**
	 * @Description  更新最后的操作时间
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateDownTime(Long stallId);

	/**
	 * @Description  查询使用状态下的车牌号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	EntRentedRecord findLastPlateNumber(Long stallId);

	/**
	 * @Description  根据车区查询车位最后的车牌号
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResEntRentedRecord> findLastPlateNumberByPreId(Long preId);
	/**
	 * 查询当前用户使用长租车位记录
	 * @param userId
	 * @return
	 */
	EntRentedRecord findByUserId(Long userId);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResStaffOwnerUseStall> findPreUseNumber(ReqStaffPreOwnerStall reqStaffPreOwnerStall);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResOwnerStallDetails findPreDetails(ReqPreDetails reqPreDetails);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResOwnerStallReportForms findOwnerStallReportForms(ReqPreDetails details);

}
