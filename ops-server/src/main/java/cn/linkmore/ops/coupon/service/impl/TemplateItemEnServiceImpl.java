package cn.linkmore.ops.coupon.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.coupon.client.TemplateEnClient;
import cn.linkmore.coupon.client.TemplateItemEnClient;
import cn.linkmore.coupon.request.ReqTemplateItem;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.ops.coupon.service.TemplateItemEnService;
import cn.linkmore.util.DateUtils;

@Service
public class TemplateItemEnServiceImpl implements TemplateItemEnService {

	@Autowired
	private TemplateItemEnClient templateItemEnClient;
	
	@Override
	public int update(ReqTemplateItem record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int save(ReqTemplateItem record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ResTemplateItem> findList(Long templdateId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResTemplateItem> selectBuDealNumber(String dealNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResTemplateItem> selectBuEnterpriseId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCouponStatus() {
		// TODO Auto-generated method stub
	}
	
}
