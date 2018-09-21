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
import cn.linkmore.prefecture.dao.cluster.StrategyTimeClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyTimeDetailClusterMapper;
import cn.linkmore.prefecture.dao.master.StrategyTimeDetailMasterMapper;
import cn.linkmore.prefecture.dao.master.StrategyTimeMasterMapper;
import cn.linkmore.prefecture.entity.StrategyTime;
import cn.linkmore.prefecture.entity.StrategyTimeDetail;
import cn.linkmore.prefecture.request.ReqStrategyTime;
import cn.linkmore.prefecture.request.ReqStrategyTimeDetail;
import cn.linkmore.prefecture.response.ResStrategyTime;
import cn.linkmore.prefecture.response.ResStrategyTimeDetail;
import cn.linkmore.prefecture.service.StrategyTimeService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
@Service
public class StrategyTimeServiceImpl implements StrategyTimeService {

	@Autowired
	private StrategyTimeMasterMapper strategyTimeMasterMapper;
	
	@Autowired
	private StrategyTimeClusterMapper strategyTimeClusterMapper;
	
	@Autowired
	private StrategyTimeDetailMasterMapper strategyTimeDetailMasterMapper;	
	
	@Autowired
	private StrategyTimeDetailClusterMapper strategyTimeDetailClusterMapper;	
	
	
	@Override
	public int save(ReqStrategyTime reqStrategyTime) {
		StrategyTime strategyTime = new StrategyTime();
		strategyTime = ObjectUtils.copyObject(reqStrategyTime, strategyTime);
		int count=strategyTimeMasterMapper.save(strategyTime);
		if(reqStrategyTime.getStrategyTimeDetail()!=null) {
			for (ReqStrategyTimeDetail reqStrategyTimeDetail : reqStrategyTime.getStrategyTimeDetail()) {
				StrategyTimeDetail strategyTimeDetail = new StrategyTimeDetail();
				strategyTimeDetail = ObjectUtils.copyObject(reqStrategyTimeDetail, strategyTimeDetail);
				strategyTimeDetail.setStrategyTimeId(strategyTime.getId());
				strategyTimeDetailMasterMapper.insert(strategyTimeDetail);
			}
		}
		return count;
	}

	@Override
	public int update(ReqStrategyTime reqStrategyTime) {
		StrategyTime strategyTime = new StrategyTime();
		strategyTime = ObjectUtils.copyObject(reqStrategyTime, strategyTime);
		
		int count=strategyTimeMasterMapper.update(strategyTime);
		List<Long> ids=new ArrayList<Long>();
		ids.add(reqStrategyTime.getId());
		strategyTimeDetailMasterMapper.delete(ids);
		
		if(reqStrategyTime.getStrategyTimeDetail()!=null) {
			for (ReqStrategyTimeDetail reqStrategyTimeDetail : reqStrategyTime.getStrategyTimeDetail()) {
				StrategyTimeDetail strategyTimeDetail = new StrategyTimeDetail();
				strategyTimeDetail = ObjectUtils.copyObject(reqStrategyTimeDetail, strategyTimeDetail);
				strategyTimeDetail.setStrategyTimeId(strategyTime.getId());
				strategyTimeDetailMasterMapper.insert(strategyTimeDetail);
			}
		}
		return count;
	}

	@Override
	public int delete(List<Long> ids) {
		strategyTimeDetailMasterMapper.delete(ids);
		return strategyTimeMasterMapper.delete(ids);
	}

	@Override
	public int updateStatus(Map<String, Object> map) {
		return strategyTimeMasterMapper.updateStatus(map);
	}

	@Override
	public ResStrategyTime selectByPrimaryKey(Long id) {
		ResStrategyTime resStrategyTime = new ResStrategyTime();
		StrategyTime strategyTime=strategyTimeClusterMapper.selectByPrimaryKey(id);
		resStrategyTime = ObjectUtils.copyObject(strategyTime, resStrategyTime);
		List<StrategyTimeDetail> listStrategyTimeDetail = strategyTimeDetailClusterMapper.findList(id);
		List<ResStrategyTimeDetail> listResStrategyTimeDetail=new ArrayList<ResStrategyTimeDetail>();
		for (StrategyTimeDetail strategyTimeDetail:listStrategyTimeDetail) {
			ResStrategyTimeDetail resStrategyTimeDetail = new ResStrategyTimeDetail();
			resStrategyTimeDetail = ObjectUtils.copyObject(strategyTimeDetail, resStrategyTimeDetail);
			listResStrategyTimeDetail.add(resStrategyTimeDetail);
		}
		resStrategyTime.setStrategyTimeDetail(listResStrategyTimeDetail);
		return resStrategyTime;
	}

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
		Integer count = this.strategyTimeClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResStrategyTime> list = this.strategyTimeClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list);
	}

	@Override
	public List<ResStrategyTime> findList(Map<String, Object> param) {
		return this.strategyTimeClusterMapper.findList(param);

	}

}
