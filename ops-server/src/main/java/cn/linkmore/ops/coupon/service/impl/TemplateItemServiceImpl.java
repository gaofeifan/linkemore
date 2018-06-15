package cn.linkmore.ops.coupon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.coupon.client.TemplateItemClient;
import cn.linkmore.coupon.request.ReqTemplateItem;
import cn.linkmore.ops.coupon.service.TemplateItemService;
/**
 * 优惠券项
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class TemplateItemServiceImpl implements TemplateItemService {
	
	@Autowired
	private TemplateItemClient templateItemClient;
	
	@Override
	public int save(ReqTemplateItem reqTempItem) {
		return this.templateItemClient.save(reqTempItem);
	}
	
	@Override
	public int update(ReqTemplateItem reqTempItem) {
		return this.templateItemClient.update(reqTempItem);
	}
}
