package cn.linkmore.coupon.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.dao.cluster.BizSubjectClusterMapper;
import cn.linkmore.coupon.dao.cluster.PreSubjectClusterMapper;
import cn.linkmore.coupon.dao.master.BizSubjectMasterMapper;
import cn.linkmore.coupon.dao.master.PreSubjectMasterMapper;
import cn.linkmore.coupon.entity.PreSubject;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqPreSubject;
import cn.linkmore.coupon.response.ResPreSubjectBean;
import cn.linkmore.coupon.service.PreSubjectService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

@Service
public class PreSubjectServiceImpl implements PreSubjectService {
	@Autowired
	private PreSubjectClusterMapper preSubjectClusterMapper;
	@Autowired
	private BizSubjectClusterMapper bizSubjectClusterMapper;
	
	@Autowired
	private PreSubjectMasterMapper preSubjectMasterMapper;
	@Autowired
	private BizSubjectMasterMapper bizSubjectMasterMapper;


	
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
		Integer count = this.preSubjectClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResPreSubjectBean> list = this.preSubjectClusterMapper.findPage(param);
		
		for(ResPreSubjectBean bean :list){
			if(bean.getOneselfType()!=null){
	        	if(bean.getOneselfType()==1){
	        		bean.setOneselfReceive(bean.getOneselfMin() +"-"+ bean.getOneselfMax() +"元");
	        	}else{
	        		bean.setOneselfReceive(bean.getOneselfFixe() +"元");
	        	}
	        }
			
			if(bean.getOtherType()!=null){
				if(bean.getOtherType()==1){
	        		bean.setOtherReceive(bean.getOtherMin() +"-"+bean.getOtherMax()+"元");
	        	}else{
	        		bean.setOtherReceive(bean.getOtherFixe()+"元");
	        	}
			}
			
			bean.setPayOneReceive(bean.getDenomainOneMin() + " - " + bean.getDenomainOneMax() + "元");
	        bean.setPayTwoReceive(bean.getDenomainTwoMin() + " - " + bean.getDenomainTwoMax() + "元");
	        bean.setPayThreeReceive(bean.getDenomainThreeMin() + " - " + bean.getDenomainThreeMax() + "元");
		}
		
		return new ViewPage(count,pageable.getPageSize(),list);
	}

	@Override
	public int save(ReqPreSubject record) {
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		record.setStatus(1);
		PreSubject preSubject = ObjectUtils.copyObject(record, new PreSubject());
		return this.preSubjectMasterMapper.save(preSubject);
	}
	
	@Override
	public int update(ReqPreSubject record) {
		record.setUpdateTime(new Date());
		record.setStatus(1);
		PreSubject preSubject = ObjectUtils.copyObject(record, new PreSubject());
		return this.preSubjectMasterMapper.update(preSubject);
	}

	@Override
	public int delete(Long id) {
		PreSubject preSubject = this.preSubjectClusterMapper.findById(id);
		preSubject.setStatus(0);
		preSubject.setUpdateTime(new Date());
		return this.preSubjectMasterMapper.update(preSubject); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.preSubjectClusterMapper.check(param); 
	}

}