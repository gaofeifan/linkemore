package cn.linkmore.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.dao.cluster.RechargeAmountClusterMapper;
import cn.linkmore.order.dao.master.RechargeAmountMasterMapper;
import cn.linkmore.order.entity.RechargeAmount;
import cn.linkmore.order.request.ReqRechargeAmount;
import cn.linkmore.order.request.ReqUpdateSql;
import cn.linkmore.order.request.RequpdateColumnValue;
import cn.linkmore.order.response.ResRechargeAmount;
import cn.linkmore.order.service.RechargeAmountService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.StringUtil;

/**
 * 充值面额的实现
 * @author GFF
 * 
 */
@Service()
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class RechargeAmountServiceImpl implements RechargeAmountService {

	@Resource
	private RechargeAmountClusterMapper rechargeAmountMapper;
	@Resource
	private RechargeAmountMasterMapper rechargeAmountMasterMapper;
	/**
	 * 新增储值面额
	 */
	@Override
	public void create(cn.linkmore.order.request.ReqRechargeAmount rechargeAmount) {
		if(rechargeAmount.getChecked()){
			this.updateColumnValue(" checked = true and id !=  "+rechargeAmount.getId(), "checked", false);
		}
		rechargeAmountMasterMapper.save(rechargeAmount);
	}
	/**
	 * 获取面额列表
	 */
	@Override
	public ViewPage getList(ViewPageable pageable) {
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<RechargeAmount> list = this.rechargeAmountMapper.findPage(param);
		Integer count = this.rechargeAmountMapper.count(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
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
	public RechargeAmount queryDetail(Long id){
		RechargeAmount one = rechargeAmountMapper.findById(id);
		if(one == null){
			throw new RuntimeException("信息不存在");
		}
		return one;
	}
	/**
	 * 更新状态
	 */
	@Override
	public void updateStatus(RechargeAmount amount) {
		rechargeAmountMasterMapper.update(amount);
	}
	/**
	 * 根据id 数据回显
	 */
	@Override
	public ResRechargeAmount queryDataById(Long id) {
		RechargeAmount one = rechargeAmountMapper.findById(id);
		if(one == null){
			throw new RuntimeException("信息不存在");
		}
		return ObjectUtils.copyObject(one,new ResRechargeAmount());
	}
	/**
	 * 修改数据
	 */
	@Override
	public void updateData(ReqRechargeAmount bean) {
		if(bean.getChecked()){
			this.updateColumnValue(" checked = true and id !=  "+bean.getId(), "checked", false);
		}
		RechargeAmount copyObject = ObjectUtils.copyObject(bean, new RechargeAmount());
		this.rechargeAmountMasterMapper.update(copyObject);
	}
	/**
	 * 选中
	 */
	@Override
	public void updateChecked(RechargeAmount amount) {
		//查询状态为选中的,除去 此id的
		if(amount.getChecked()){
			this.updateColumnValue(" checked = true and id !=  "+amount.getId(), "checked", false);
		}
		amount.setChecked(true);
		this.rechargeAmountMasterMapper.update(amount);
	}


	@Override
	public void updateColumnValue(String condition, String column, Object status) {
		ReqUpdateSql sql = new ReqUpdateSql();
		sql.setColumnValue(status);
		sql.setColumn(column);
		sql.setSql(condition);
		this.rechargeAmountMasterMapper.updateColumnValue( sql );
	}
}
