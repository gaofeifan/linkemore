package cn.linkmore.enterprise.service;

import javax.servlet.http.HttpServletRequest;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.enterprise.controller.staff.request.AssignStallRequestBean;
import cn.linkmore.enterprise.controller.staff.request.OrderOperateRequestBean;
import cn.linkmore.enterprise.controller.staff.request.SraffReqConStall;
import cn.linkmore.enterprise.controller.staff.request.SraffReqConStallSn;
import cn.linkmore.enterprise.controller.staff.request.StallOnLineRequest;
import cn.linkmore.enterprise.controller.staff.request.StallOperateRequestBean;
import cn.linkmore.enterprise.controller.staff.response.PrefectureResponseBean;


/**
 * 
 * @author   changlei
 * @Date     2018年9月10日
 * @Version  v2.0
 */
public interface StaffPrefectureService {
	/**
	 * 操作车位
	 * 
	 * @param bean
	 * @return
	 */
	Boolean  control(SraffReqConStall reqOperatStall,HttpServletRequest request);
	
	
	Boolean  controlSn(SraffReqConStallSn reqOperatStallSn,HttpServletRequest request);
	
	/**
	 * 释放车位
	 * 
	 * @param bean
	 * @return
	 */
	void releaseStall(Long stall_id, HttpServletRequest request);
	/**
	 * 强制释放车位
	 * 
	 * @param bean
	 * @return
	 */
	void forceReleaseStall(Long stall_id, HttpServletRequest request);
		
	/**
	 * 车位下线
	 * 
	 * @param bean
	 * @param au
	 */
	void offline(StallOperateRequestBean bean,HttpServletRequest request);

	/**
	 * 车位上线
	 * 
	 * @param bean
	 * @param au
	 */
	void online(StallOnLineRequest bean,HttpServletRequest request);
	
	
	/**
	 * 挂起订单
	 * @param oorb 订单操作请求Bean
	 * @param au 管理员
	 */
	void suspend(OrderOperateRequestBean oorb,HttpServletRequest request);
	

	/**
	 * 关闭订单
	 * @param oorb 订单操作请求Bean
	 * @param au 管理员
	 */
	void close(OrderOperateRequestBean oorb,HttpServletRequest request);
	
	
}
