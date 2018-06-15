package cn.linkmore.ops.coupon.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.PreSubjectClient;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqPreSubject;
import cn.linkmore.coupon.response.ResBizSubjectBean;
import cn.linkmore.ops.coupon.service.PreSubjectService;

@Service
public class PreSubjectServiceImpl implements PreSubjectService {
	@Autowired
	private PreSubjectClient preSubjectClient;
	
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.preSubjectClient.list(pageable);
	}

	@Override
	public int save(ReqPreSubject record) {
		return this.preSubjectClient.save(record);
	}
	
	@Override
	public int update(ReqPreSubject record) {
		return this.preSubjectClient.update(record);
	}

	@Override
	public int delete(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		return preSubjectClient.delete(ids);
	}
	
	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.preSubjectClient.check(reqCheck);
	}

	@Override
	public List<ResBizSubjectBean> subjectList(Long type) {
		return this.preSubjectClient.subjectList(type);
	}

}