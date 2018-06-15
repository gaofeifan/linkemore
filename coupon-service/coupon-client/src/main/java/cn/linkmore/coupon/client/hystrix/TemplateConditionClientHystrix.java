package cn.linkmore.coupon.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.TemplateConditionClient;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplateCondition;
import cn.linkmore.coupon.response.ResTemplateCondition;

@Component
public class TemplateConditionClientHystrix implements TemplateConditionClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public int save(ReqTemplateCondition record) {
		log.info("coupon service temp_condition save() hystrix");
		return 0;
	}

	@Override
	public int update(ReqTemplateCondition record) {
		log.info("coupon service temp_condition update() hystrix");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("coupon service temp_condition delete() hystrix");
		return 0;
	}

	@Override
	public int setDefault(Long id) {
		log.info("coupon service temp_condition setDefault() hystrix");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("coupon service temp_condition check() hystrix");
		return null;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("coupon service temp_condition list() hystrix");
		return null;
	}

	@Override
	public List<ResTemplateCondition> conditionList(Long tempId) {
		log.info("coupon service temp_condition conditionList() hystrix");
		return null;
	}

	@Override
	public ResTemplateCondition detail(Long id) {
		log.info("coupon service temp_condition detail() hystrix");
		return null;
	}
	
}
