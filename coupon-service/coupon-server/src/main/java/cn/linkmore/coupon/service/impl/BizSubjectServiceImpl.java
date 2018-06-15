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
import cn.linkmore.coupon.dao.cluster.ComboClusterMapper;
import cn.linkmore.coupon.dao.master.BizSubjectMasterMapper;
import cn.linkmore.coupon.entity.BizSubject;
import cn.linkmore.coupon.request.ReqBizSubject;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.response.ResBizSubjectBean;
import cn.linkmore.coupon.response.ResCombo;
import cn.linkmore.coupon.service.BizSubjectService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

@Service
public class BizSubjectServiceImpl implements BizSubjectService {
	@Autowired
	private BizSubjectClusterMapper bizSubjectClusterMapper;
	
	@Autowired
	private BizSubjectMasterMapper bizSubjectMasterMapper;
	
	@Autowired
	private ComboClusterMapper comboClusterMapper;
	
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
		Integer count = this.bizSubjectClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResBizSubjectBean> list = this.bizSubjectClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list);
	}

	@Override
	public int save(ReqBizSubject record) {
		/*Person person = (Person)SecurityUtils.getSubject().getSession().getAttribute("person");
		record.setOperatorId(person.getId());*/
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		record.setStatus(0);
		if(record.getComboId() != null){
			ResCombo combo = this.comboClusterMapper.findById(record.getComboId());
			record.setTotalAmount(combo.getTotalAmount());
		}
		BizSubject bizSubject = ObjectUtils.copyObject(record, new BizSubject());
		return this.bizSubjectMasterMapper.save(bizSubject);
	}
	
	@Override
	public int update(ReqBizSubject record) {
		record.setUpdateTime(new Date());
		if(record.getComboId() != null){
			ResCombo combo = this.comboClusterMapper.findById(record.getComboId());
			record.setTotalAmount(combo.getTotalAmount());
		}
		BizSubject bizSubject = ObjectUtils.copyObject(record, new BizSubject());
		return this.bizSubjectMasterMapper.update(bizSubject);
	}

	@Override
	public int delete(Long id) {
		return this.bizSubjectMasterMapper.delete(id); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.bizSubjectClusterMapper.check(param); 
	}

	@Override
	public ResBizSubjectBean findById(Long id) {
		return this.bizSubjectClusterMapper.findById(id);
	}

	@Override
	public int start(Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		param.put("status", 1);
		param.put("updateTime", new Date());
		return this.bizSubjectMasterMapper.startOrStop(param);
	}

	@Override
	public int stop(Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		param.put("status", 0);
		param.put("updateTime", new Date());
		return this.bizSubjectMasterMapper.startOrStop(param);
	}

	@Override
	public List<ResBizSubjectBean> selectSubjectListByType(Long type) {
		return this.bizSubjectClusterMapper.selectSubjectListByType(type);
	}

}