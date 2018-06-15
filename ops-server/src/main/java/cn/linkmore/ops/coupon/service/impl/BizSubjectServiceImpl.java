package cn.linkmore.ops.coupon.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.BizSubjectClient;
import cn.linkmore.coupon.request.ReqBizSubject;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.response.ResBizSubjectBean;
import cn.linkmore.coupon.response.ResCombo;
import cn.linkmore.coupon.response.ResValuePack;
import cn.linkmore.ops.coupon.service.BizSubjectService;

@Service
public class BizSubjectServiceImpl implements BizSubjectService {
	@Autowired
	private BizSubjectClient bizSubjectClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.bizSubjectClient.list(pageable);
	}

	@Override
	public int save(ReqBizSubject record) {
		return this.bizSubjectClient.save(record);
	}
	
	@Override
	public int update(ReqBizSubject record) {
		return this.bizSubjectClient.update(record);
	}

	@Override
	public int delete(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		return this.bizSubjectClient.delete(ids);
	}
	
	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.bizSubjectClient.check(reqCheck);
	}

	@Override
	public ResBizSubjectBean findById(Long id) {
		return this.bizSubjectClient.detail(id);
	}

	@Override
	public int start(Long id) {
		return this.bizSubjectClient.start(id);
	}

	@Override
	public int stop(Long id) {
		return this.bizSubjectClient.down(id);
	}

	@Override
	public List<ResBizSubjectBean> selectSubjectListByType(Long type) {
		return null;
	}

	@Override
	public List<ResCombo> comboList(Long comboType) {
		return bizSubjectClient.comboList(comboType);
	}

	@Override
	public List<ResValuePack> packList(Long comboType) {
		return bizSubjectClient.packList(comboType);
	}

}