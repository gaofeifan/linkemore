package cn.linkmore.coupon.service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.request.ReqSendRecord;

public interface SendRecordService {

	/**
	 * 保存信息
	 * @param record
	 */
	int save(ReqSendRecord record);

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  商家版保存发放用户
	 * @Author GFF 
	 * @Date 2018年3月28日
	 * @Return   void
	 */
	int saveBusiness(ReqSendRecord record);
	
	void timingForSend();

}
