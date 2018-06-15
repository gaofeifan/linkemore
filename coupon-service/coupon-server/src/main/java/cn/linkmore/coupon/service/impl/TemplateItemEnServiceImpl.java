package cn.linkmore.coupon.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.coupon.dao.cluster.TemplateItemEnClusterMapper;
import cn.linkmore.coupon.dao.master.CouponMasterMapper;
import cn.linkmore.coupon.dao.master.TemplateItemEnMasterMapper;
import cn.linkmore.coupon.entity.TemplateItem;
import cn.linkmore.coupon.request.ReqTemplateItem;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.coupon.service.TemplateItemEnService;
import cn.linkmore.util.DateUtils;
import cn.linkmore.util.ObjectUtils;

@Service
public class TemplateItemEnServiceImpl implements TemplateItemEnService {
	
	@Autowired
	private TemplateItemEnMasterMapper templateItemEnMasterMapper;
	
	@Autowired
	private TemplateItemEnClusterMapper templateItemEnClusterMapper;
	
	@Autowired
	private CouponMasterMapper couponMasterMapper;
	@Override
	public int save(ReqTemplateItem record) {
		TemplateItem tempItem = ObjectUtils.copyObject(record, new TemplateItem());
		return this.templateItemEnMasterMapper.save(tempItem);
	}
	
	@Override
	public int update(ReqTemplateItem record) {
		TemplateItem tempItem = ObjectUtils.copyObject(record, new TemplateItem());
		return this.templateItemEnMasterMapper.update(tempItem);
	}

	@Override
	public List<ResTemplateItem> findList(Long templdateId) {
		List<ResTemplateItem> items =  templateItemEnClusterMapper.findList(templdateId);
		return items;
	}

	@Override
	public List<ResTemplateItem> selectBuDealNumber(String dealNumber) {
		return this.templateItemEnClusterMapper.selectBuDealNumber(dealNumber);
	}

	@Override
	public List<ResTemplateItem> selectBuEnterpriseId(Long id) {
		return this.templateItemEnClusterMapper.selectBuEnterpriseId(id);
	}

	@Override
	public void updateCouponStatus() {
		Date date = DateUtils.getPast2Date(+1);
		this.couponMasterMapper.updateCouponStatus(date);
	}
	
}
