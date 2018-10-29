package cn.linkmore.prefecture.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import cn.linkmore.prefecture.dao.cluster.StrategyDateClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyDateDetailClusterMapper;
import cn.linkmore.prefecture.dao.master.StrategyDateDetailMasterMapper;
import cn.linkmore.prefecture.dao.master.StrategyDateMasterMapper;
import cn.linkmore.prefecture.entity.StrategyDate;
import cn.linkmore.prefecture.entity.StrategyDateDetail;
import cn.linkmore.prefecture.request.ReqStrategyDate;
import cn.linkmore.prefecture.request.ReqStrategyDateDetail;
import cn.linkmore.prefecture.response.ResStrategyDate;
import cn.linkmore.prefecture.response.ResStrategyDateDetail;
import cn.linkmore.prefecture.service.StrategyDateService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

@Service
public class StrategyDateServiceImpl implements StrategyDateService {

	@Autowired
	private StrategyDateMasterMapper strategyDateMasterMapper;
	
	@Autowired
	private StrategyDateClusterMapper strategyDateClusterMapper;
	
	@Autowired
	private StrategyDateDetailMasterMapper strategyDateDetailMasterMapper;	
	
	@Autowired
	private StrategyDateDetailClusterMapper strategyDateDetailClusterMapper;	
	
	
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	
	@Override
	public int insert(ReqStrategyDate record) {
		StrategyDate strategyDate = new StrategyDate();
		//strategyDate = ObjectUtils.copyObject(record, strategyDate);
		
		strategyDate.setName(record.getName());
		strategyDate.setDetail(record.getDetail());
		strategyDate.setDatetype(record.getDatetype());
		strategyDate.setCreateTime(record.getCreateTime());
		strategyDate.setCreateUserId(record.getCreateUserId());
		strategyDate.setCreateUserName(record.getCreateUserName());
		strategyDate.setUpdateTime(record.getUpdateTime());
		strategyDate.setUpdateUserId(record.getUpdateUserId());
		strategyDate.setUpdateUserName(record.getUpdateUserName());
		strategyDate.setStatus(record.getStatus());
		if(record.getDatetype()==2) {
			try {
				strategyDate.setStartDate(sdf.parse(record.getStartDate()));
				strategyDate.setStopDate(sdf.parse(record.getStopDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		int count=strategyDateMasterMapper.insert(strategyDate);

		if(record.getStrategyDateDetail()!=null) {
			for (ReqStrategyDateDetail reqStrategyDateDetail : record.getStrategyDateDetail()) {
				StrategyDateDetail strategyDateDetail = new StrategyDateDetail();
				strategyDateDetail = ObjectUtils.copyObject(reqStrategyDateDetail, strategyDateDetail);
				strategyDateDetail.setStrategyDateId(strategyDate.getId());
				strategyDateDetailMasterMapper.insert(strategyDateDetail);
			}
		}
		return count;
		
	}
	
	@Override
	public int delete(List<Long> ids) {
		strategyDateDetailMasterMapper.delete(ids);
		return strategyDateMasterMapper.delete(ids);
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return strategyDateMasterMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int updateStatus(Map<String, Object> map) {
		return strategyDateMasterMapper.updateStatus(map);
	}
	
	@Override
	public int updateByPrimaryKey(ReqStrategyDate record) {
		StrategyDate strategyDate = new StrategyDate();
		//strategyDate = ObjectUtils.copyObject(record, strategyDate);
		strategyDate.setId(record.getId());
		strategyDate.setName(record.getName());
		strategyDate.setDetail(record.getDetail());
		strategyDate.setDatetype(record.getDatetype());
		strategyDate.setCreateTime(record.getCreateTime());
		strategyDate.setCreateUserId(record.getCreateUserId());
		strategyDate.setCreateUserName(record.getCreateUserName());
		strategyDate.setUpdateTime(record.getUpdateTime());
		strategyDate.setUpdateUserId(record.getUpdateUserId());
		strategyDate.setUpdateUserName(record.getUpdateUserName());
		strategyDate.setStatus(record.getStatus());
		if(record.getDatetype()==2) {
			try {
				strategyDate.setStartDate(sdf.parse(record.getStartDate()));
				strategyDate.setStopDate(sdf.parse(record.getStopDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		int count=strategyDateMasterMapper.updateByPrimaryKey(strategyDate);
		
		List<Long> ids=new ArrayList<Long>();
		ids.add(record.getId());
		strategyDateDetailMasterMapper.delete(ids);
		if(record.getStrategyDateDetail()!=null) {
			for (ReqStrategyDateDetail reqStrategyDateDetail : record.getStrategyDateDetail()) {
				StrategyDateDetail strategyDateDetail = new StrategyDateDetail();
				strategyDateDetail = ObjectUtils.copyObject(reqStrategyDateDetail, strategyDateDetail);
				strategyDateDetail.setStrategyDateId(strategyDate.getId());
				strategyDateDetailMasterMapper.insert(strategyDateDetail);
			}
		}

		return count;
	}


	@Override
	public ResStrategyDate selectByPrimaryKey(Long id) {
		ResStrategyDate resStrategyDate = new ResStrategyDate();
		//strategyDate = ObjectUtils.copyObject(record, strategyDate);
		StrategyDate strategyDate=strategyDateClusterMapper.selectByPrimaryKey(id);
		resStrategyDate = ObjectUtils.copyObject(strategyDate, resStrategyDate);
		List<StrategyDateDetail> listStrategyDateDetail = strategyDateDetailClusterMapper.findList(id);
		List<ResStrategyDateDetail> listResStrategyDateDetail=new ArrayList<ResStrategyDateDetail>();
		for (StrategyDateDetail strategyDateDetail:listStrategyDateDetail) {
			ResStrategyDateDetail resStrategyDateDetail = new ResStrategyDateDetail();
			resStrategyDateDetail = ObjectUtils.copyObject(strategyDateDetail, resStrategyDateDetail);
			listResStrategyDateDetail.add(resStrategyDateDetail);
		}
		resStrategyDate.setStrategyDateDetail(listResStrategyDateDetail);
		return resStrategyDate;
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
		Integer count = this.strategyDateClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<StrategyDate> list = this.strategyDateClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list);
	}

	@Override
	public List<ResStrategyDate> findList(Map<String, Object> param) {
		return this.strategyDateClusterMapper.findList(param);
	}


}
