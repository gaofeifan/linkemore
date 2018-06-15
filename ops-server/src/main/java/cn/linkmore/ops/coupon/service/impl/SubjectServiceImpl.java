package cn.linkmore.ops.coupon.service.impl;

import java.util.ArrayList;
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
import cn.linkmore.coupon.client.SubjectClient;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqSubject;
import cn.linkmore.coupon.response.ResSubject;
import cn.linkmore.coupon.response.ResSubjectBean;
import cn.linkmore.coupon.response.ResTemplate;
import cn.linkmore.ops.coupon.service.SubjectService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;


@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	private SubjectClient subjectClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) { 
		return this.subjectClient.list(pageable);
	}

	@Override
	public int save(ReqSubject record) {
		return this.subjectClient.save(record);
	}
	
	@Override
	public int update(ReqSubject record) {
		return this.subjectClient.update(record);
	}

	@Override
	public int delete(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		return this.subjectClient.delete(ids); 
	}
	
	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.subjectClient.check(reqCheck); 
	}

	@Override
	public ResSubject findById(Long id) {
		return this.subjectClient.detail(id);
	}

	@Override
	public int start(Long id) {
		return this.subjectClient.start(id);
	}

	@Override
	public int stop(Long id) {
		return this.subjectClient.down(id);
	}

	@Override
	public List<ResTemplate> subjectList() {
		return this.subjectClient.subjectList();
	}

}