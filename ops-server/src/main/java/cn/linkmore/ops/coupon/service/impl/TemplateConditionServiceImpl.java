package cn.linkmore.ops.coupon.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.TemplateConditionClient;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplateCondition;
import cn.linkmore.coupon.response.ResTemplateCondition;
import cn.linkmore.ops.coupon.service.TemplateConditionService;

/**
 * 优惠券条件
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class TemplateConditionServiceImpl implements TemplateConditionService {
	@Autowired
	private TemplateConditionClient templateConditionClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.templateConditionClient.list(pageable);
	}

	@Override
	public int save(ReqTemplateCondition record) {
		return this.templateConditionClient.save(record);
	}
	
	@Override
	public int update(ReqTemplateCondition reqTemplateCondition) {
		return this.templateConditionClient.update(reqTemplateCondition);
	}

	@Override
	public int delete(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		return this.templateConditionClient.delete(ids); 
	}
	
	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.templateConditionClient.check(reqCheck); 
	}
	
	@Override
	public List<ResTemplateCondition> findConditionList(Long tempId) {
		List<ResTemplateCondition> list = this.templateConditionClient.conditionList(tempId);
		return list;
	}

	@Override
	public int setDefault(Long id) {
		return this.templateConditionClient.setDefault(id);
	}

	@Override
	public ResTemplateCondition detail(Long id) {
		return this.templateConditionClient.detail(id);
	}
}
