package cn.linkmore.order.service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.entity.RechargeAmount;
import cn.linkmore.order.request.ReqRechargeAmount;
import cn.linkmore.order.response.ResRechargeAmount;

/**
 * 用户充值面额
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
public interface RechargeAmountService {

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void create(ReqRechargeAmount rechargeAmount);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public ViewPage getList(ViewPageable pageable);

	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public RechargeAmount queryDetail(Long id);

	/**
	 * @Description  更新状态
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void updateStatus(RechargeAmount amount);

	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public ResRechargeAmount queryDataById(Long id);

	/**
	 * @Description  更新数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void updateData(ReqRechargeAmount bean);

	/**
	 * @Description  更新选中状态
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void updateChecked(RechargeAmount amount);

	/**
	 * @Description  根据字段更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateColumnValue(String condition, String column, Object status);
	
}
