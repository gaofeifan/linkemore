package cn.linkmore.ops.account.service;


import cn.linkmore.account.request.ReqRechargeAmount;
import cn.linkmore.account.response.ResRechargeAmount;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 用户充值面额
 * @author   GFF
 * @Date     2018年5月31日
 * @Version  v2.0
 */
public interface RechargeAmountService {

	/**
	 * 新增
	 * @Description  
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
	 * @Description  根据id查询详情
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public ResRechargeAmount queryDetail(Long id);

	/**
	 * @Description  更新状态
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void updateStatus(ReqRechargeAmount amount);

	/**
	 * @Description  查询详情
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
	 * @Description  修改选中状态
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void updateChecked(ReqRechargeAmount amount);

	/**
	 * @Description 根据字段修改
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void updateColumnValue(String string, String string2, int i);



	
}
