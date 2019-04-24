package cn.linkmore.enterprise.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.entity.EntRentedRecord;
import cn.linkmore.enterprise.request.ReqRentedRecord;
import cn.linkmore.enterprise.response.ResRentedRecord;

/**
 * 长租用户使用接口
 * @author   GFF
 * @Date     2018年7月30日
 * @Version  v2.0
 */
public interface RentedRecordService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findList(ViewPageable pageable);

	List<ResRentedRecord> exportList(ReqRentedRecord bean);

	List<EntRentedRecord> findLastByStallIds(List<Long> stalls);

	List<EntRentedRecord> findParkingRecord(List<Long> collect, Integer pageNo, Long stallId);

	void updateRecordBatch(List<EntRentedRecord> chengsRecord);

}
