package cn.linkmore.enterprise.service;

import javax.servlet.http.HttpServletRequest;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.enterprise.controller.staff.request.AssignStallRequestBean;
import cn.linkmore.enterprise.controller.staff.request.OrderOperateRequestBean;
import cn.linkmore.enterprise.controller.staff.request.SraffReqConStall;
import cn.linkmore.enterprise.controller.staff.request.StallOperateRequestBean;
import cn.linkmore.enterprise.controller.staff.response.PrefectureResponseBean;


/**
 * 
 * @author   changlei
 * @Date     2018年9月10日
 * @Version  v2.0
 */
public interface StaffPrefectureService {

	void  control(SraffReqConStall reqOperatStall,HttpServletRequest request);
	
	ResponseEntity<PrefectureResponseBean> releaseStall(Long stall_id, HttpServletRequest request);

	ResponseEntity<PrefectureResponseBean> forceReleaseStall(Long stall_id, HttpServletRequest request);
	
	/**
	 * 指定车位
	 * 
	 * @param bean
	 * @return
	 */
	String assign(AssignStallRequestBean bean,HttpServletRequest request);
	
	/**
	 * 删除指定车位
	 * 
	 * @param bean
	 * @return
	 */
	void assignDel(AssignStallRequestBean bean,HttpServletRequest request);
	
	/**
	 * 车位下线
	 * 
	 * @param bean
	 * @param au
	 */
	PrefectureResponseBean offline(StallOperateRequestBean bean,HttpServletRequest request);

	/**
	 * 车位上线
	 * 
	 * @param bean
	 * @param au
	 */
	PrefectureResponseBean online(StallOperateRequestBean bean,HttpServletRequest request);
	
	
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
