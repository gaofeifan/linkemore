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
import cn.linkmore.coupon.dao.cluster.SubjectClusterMapper;
import cn.linkmore.coupon.dao.cluster.TemplateClusterMapper;
import cn.linkmore.coupon.dao.master.SubjectMasterMapper;
import cn.linkmore.coupon.entity.Subject;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqSubject;
import cn.linkmore.coupon.response.ResSubject;
import cn.linkmore.coupon.response.ResSubjectBean;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.coupon.service.SubjectService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;


@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	private SubjectClusterMapper subjectClusterMapper;
	
	@Autowired
	private SubjectMasterMapper subjectMasterMapper;
	
	@Autowired
	private TemplateClusterMapper templateClusterMapper;
	
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
		Integer count = this.subjectClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResSubjectBean> list = this.subjectClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list);
	}

	@Override
	public int save(ReqSubject record) {
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		record.setStatus(0);
		if(record.getTemplateId() != null){
			ResTemplate temp = this.templateClusterMapper.findById(record.getTemplateId());
			record.setTotalAmount(temp.getTotalAmount());
		}
		Subject subject = ObjectUtils.copyObject(record, new Subject());
		return this.subjectMasterMapper.save(subject);
	}
	
	@Override
	public int update(ReqSubject record) {
		record.setUpdateTime(new Date());
		if(record.getTemplateId() != null){
			ResTemplate temp = this.templateClusterMapper.findById(record.getTemplateId());
			record.setTotalAmount(temp.getTotalAmount());
		}
		Subject subject = ObjectUtils.copyObject(record, new Subject());
		return this.subjectMasterMapper.update(subject);
	}

	@Override
	public int delete(Long id) {
		return this.subjectMasterMapper.delete(id); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.subjectClusterMapper.check(param); 
	}

	@Override
	public ResSubject findById(Long id) {
		return this.subjectClusterMapper.findById(id);
	}

	@Override
	public int start(Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		param.put("status", 1);
		param.put("updateTime", new Date());
		return this.subjectMasterMapper.startOrStop(param);
	}

	@Override
	public int stop(Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("id", id);
		param.put("status", 0);
		param.put("updateTime", new Date());
		return this.subjectMasterMapper.startOrStop(param);
	}
	
	public List<ResSubject> findSubjectList(){
		return this.subjectClusterMapper.findSubjectList();
	}
}