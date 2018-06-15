package cn.linkmore.coupon.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.coupon.dao.cluster.TemplateItemClusterMapper;
import cn.linkmore.coupon.dao.master.TemplateItemMasterMapper;
import cn.linkmore.coupon.entity.TemplateItem;
import cn.linkmore.coupon.request.ReqTemplateItem;
import cn.linkmore.coupon.response.ResTemplateItem;
import cn.linkmore.coupon.service.TemplateItemService;
import cn.linkmore.util.ObjectUtils;
/**
 * 优惠券项
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class TemplateItemServiceImpl implements TemplateItemService {
	
	@Autowired
	private TemplateItemClusterMapper templateItemClusterMapper;
	@Autowired
	private TemplateItemMasterMapper templateItemMasterMapper;
	
	@Override
	public int save(ReqTemplateItem reqTempItem) { 
		TemplateItem tempItem = ObjectUtils.copyObject(reqTempItem, new TemplateItem());
		return this.templateItemMasterMapper.save(tempItem);
	}
	
	@Override
	public int update(ReqTemplateItem reqTempItem) {
		TemplateItem tempItem = ObjectUtils.copyObject(reqTempItem, new TemplateItem());
		return this.templateItemMasterMapper.update(tempItem);
	}

	@Override
	public List<ResTemplateItem> findList(Long templdateId) {
		List<ResTemplateItem> items =  templateItemClusterMapper.findList(templdateId);
		return items;
	}
}
