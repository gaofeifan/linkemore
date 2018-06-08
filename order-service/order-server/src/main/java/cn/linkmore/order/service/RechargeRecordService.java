package cn.linkmore.order.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.request.ReqRechargeRecordExcel;
import cn.linkmore.order.response.ResRechargeRecordExcel;

/**
 * Service接口 - 储值记录
 * @author liwenlong
 * @version 1.0
 *
 */
public interface RechargeRecordService {

	/**
	 * 分页查询 
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	/**
	 * 导出数据列表
	 * @param bean
	 * @return
	 */
	List<ResRechargeRecordExcel> exportList(ReqRechargeRecordExcel bean);

}
