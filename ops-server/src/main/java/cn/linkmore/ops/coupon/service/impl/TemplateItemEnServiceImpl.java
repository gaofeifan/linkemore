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
		this.templateItemEnClient.update(record);
		return 0;
	}

	@Override
	public int save(ReqTemplateItem record) {
		this.templateItemEnClient.save(record);
		return 0;
	}

	@Override
	public List<ResTemplateItem> findList(Long templdateId) {
//		List<ResTemplateItem> list = this.templateItemEnClient.selectBuEnterpriseId(templdateId);
		return null;
	}

	@Override
	public List<ResTemplateItem> selectBuDealNumber(String dealNumber) {
		List<ResTemplateItem> list = this.templateItemEnClient.selectBuDealNumber(dealNumber);
		return list;
	}

	@Override
	public List<ResTemplateItem> selectBuEnterpriseId(Long id) {
		List<ResTemplateItem> list = this.templateItemEnClient.selectBuEnterpriseId(id);
		return list;
	}

	@Override
	public void updateCouponStatus() {
		this.updateCouponStatus();
	}
	
}
