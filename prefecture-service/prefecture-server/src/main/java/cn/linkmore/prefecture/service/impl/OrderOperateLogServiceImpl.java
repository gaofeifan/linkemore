package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.OrderOperateLogClusterMapper;
import cn.linkmore.prefecture.dao.master.OrderOperateLogMasterMapper;
import cn.linkmore.prefecture.entity.OrderOperateLog;
import cn.linkmore.prefecture.request.ReqOrderOperateLog;
import cn.linkmore.prefecture.request.ReqOrderOperateLogExcel;
import cn.linkmore.prefecture.response.ResOrderOperateLog;
import cn.linkmore.prefecture.response.ResOrderOperateLogEntity;
import cn.linkmore.prefecture.service.OrderOperateLogService;
import cn.linkmore.security.client.PersonClient;
import cn.linkmore.security.response.ResPerson;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 - 车位订单操作记录
 * @author jiaohanbin
 *
 */
@Service
public class OrderOperateLogServiceImpl implements OrderOperateLogService {
	@Autowired
	private OrderOperateLogClusterMapper operateLogClusterMapper;
	
	@Autowired
	private OrderOperateLogMasterMapper operateLogMasterMapper;
	
	@Autowired
	private PersonClient personClient;
	
	/*
	 * 列表
	 */
	@Override
	public ViewPage findPage(ViewPageable pageable) {
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
		Integer count = this.operateLogClusterMapper.count(param);
		List<ResOrderOperateLogEntity> list = new ArrayList<ResOrderOperateLogEntity>();
		if(count > 0){
			param.put("start", pageable.getStart());
			param.put("pageSize", pageable.getPageSize());
			list = this.operateLogClusterMapper.findPage(param);
			for (ResOrderOperateLogEntity bean : list) {
				//来源（1后台，2app）
				Integer source = bean.getSource();
				Long operatorId = bean.getOperatorId();
				if(source==1){
					ResPerson person = personClient.findById(operatorId);
					if(person!=null){
						bean.setOperator(person.getRealname());
					}else{
						bean.setOperator("");
					}
				}
			}
		}
		return new ViewPage(count,pageable.getPageSize(),list);
	}
	
	@Override
	public List<ResOrderOperateLogEntity> findListById(Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("start", 0);
		param.put("pageSize", 1);
		List<ResOrderOperateLogEntity> list = this.operateLogClusterMapper.findPage(param);
		for (ResOrderOperateLogEntity bean : list) {
			//来源（1后台，2app）
			Integer source = bean.getSource();
			Long operatorId = bean.getOperatorId();
			if(source==1){
				ResPerson person = personClient.findById(operatorId);
				if(person!=null){
					bean.setOperator(person.getRealname());
				}else{
					bean.setOperator("");
				}
			}
		}
		return list;
	}
	
	@Override
	public int delete(List<Long> ids) {
		return this.operateLogMasterMapper.delete(ids);
	}

	@Override
	public ResOrderOperateLog find(Long id) {
		return this.operateLogClusterMapper.findById(id);
	}

	@Override
	public int save(ReqOrderOperateLog reqOperateLog) {
		OrderOperateLog operateLog = new OrderOperateLog();
		operateLog = ObjectUtils.copyObject(reqOperateLog, operateLog);
		return this.operateLogMasterMapper.save(operateLog);
	}

	@Override
	public int update(ReqOrderOperateLog reqOperateLog) {
		OrderOperateLog operateLog = new OrderOperateLog();
		operateLog = ObjectUtils.copyObject(reqOperateLog, operateLog);
		return this.operateLogMasterMapper.update(operateLog);
	}

	/*
	 * 导出
	 */
	@Override
	public List<ResOrderOperateLogEntity> exportList(
			ReqOrderOperateLogExcel excelBean) {
		List<ResOrderOperateLogEntity> list = this.operateLogClusterMapper.findExcelList(excelBean);
		for (ResOrderOperateLogEntity bean : list) {
			//来源（1后台，2app）
			Integer source = bean.getSource();
			Long operatorId = bean.getOperatorId();
			if(source==1){
				ResPerson person = personClient.findById(operatorId);
				if(person!=null){
					bean.setOperator(person.getRealname());
				}else{
					bean.setOperator("");
				}
			}
		}
		return list;
	}

}
