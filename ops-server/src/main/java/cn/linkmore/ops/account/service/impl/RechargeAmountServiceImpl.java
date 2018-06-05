package cn.linkmore.ops.account.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.account.client.RechargeAmountClient;
import cn.linkmore.account.request.ReqRechargeAmount;
import cn.linkmore.account.request.RequpdateColumnValue;
import cn.linkmore.account.response.ResRechargeAmount;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.RechargeAmountService;

/**
 * 充值面额的实现
 * @author zhaoyufei
 * 
 */
@Service("rechargeAmountService")
public class RechargeAmountServiceImpl implements RechargeAmountService {

	@Resource
	private RechargeAmountClient rechargeAmountClient;
	
	/**
	 * 新增储值面额
	 */
	@Override
	public void create(ReqRechargeAmount rechargeAmount) {
		rechargeAmountClient.save(rechargeAmount);
	}
	/**
	 * 获取面额列表
	 */
	@Override
	public ViewPage getList(ViewPageable pageable) {
		return this.rechargeAmountClient.getList(pageable);
	}

	private List<ResRechargeAmount> getReAmountList(List<?> rows) {
		List<ResRechargeAmount> list = new ArrayList<>();
		for (Object obj: rows ) {
	        Map<?, ?> row = (Map<?, ?>) obj;
	        ResRechargeAmount bean = new ResRechargeAmount();
	        bean.setId(Long.parseLong(row.get("id").toString()));
	        if(row.get("status")!=null){
	        	bean.setStatus(Integer.valueOf(row.get("status").toString()));
	        }
	        if(row.get("checked")!=null){
	        	bean.setChecked(Boolean.parseBoolean(row.get("checked").toString()));
	        }
	        if(row.get("order_index")!=null){
	        	bean.setOrderIndex(Integer.valueOf(row.get("order_index").toString()));
	        }
	        if(row.get("gift")!=null){
	        	bean.setGift(Double.valueOf(row.get("gift").toString()));
	        }
	        if(row.get("payment")!=null){
	        	bean.setPayment(Double.valueOf(row.get("payment").toString()));
	        }
	        list.add(bean);
		}
		return list;
	}
	/**
	 * 根据id获取面额的信息
	 * @throws Exception 
	 */
	@Override
	public ResRechargeAmount queryDetail(Long id){
		return this.rechargeAmountClient.findById(id);
	}
	/**
	 * 更新状态
	 */
	@Override
	public void updateStatus(ReqRechargeAmount amount) {
		rechargeAmountClient.updateById(amount);
	}
	/**
	 * 根据id 数据回显
	 */
	@Override
	public ResRechargeAmount queryDataById(Long id) {
		return this.rechargeAmountClient.findById(id);
	}
	/**
	 * 修改数据
	 */
	@Override
	public void updateData(ReqRechargeAmount bean) {
		this.rechargeAmountClient.updateByIdSelective(bean);
	}
	/**
	 * 选中
	 */
	@Override
	public void updateChecked(ReqRechargeAmount amount) {
		//查询状态为选中的,除去 此id的
		this.rechargeAmountClient.updateById(amount);
	}
	@Override
	public void updateColumnValue(String pro, String v, int i) {
		RequpdateColumnValue value = new RequpdateColumnValue();
		value.setProperties(pro);
		value.setValue(v);
		value.setStatus(i);
		this.rechargeAmountClient.updateColumnValue(value);
	}
	

}
