package cn.linkmore.coupon.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.TemplateItemClient;
import cn.linkmore.coupon.client.TemplateItemEnClient;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplate;
import cn.linkmore.coupon.request.ReqTemplateItem;
import cn.linkmore.coupon.response.ResTemplate;

@Component
public class TemplateItemEnClientHystrix implements TemplateItemEnClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqTemplateItem record) {
		log.info("template item en client hystrix save ");
		return 0;
	}

	@Override
	public int update(ReqTemplateItem record) {
		log.info("template item en client hystrix update ");
		return 0;
	}

	@Override
	public Object selectBuDealNumber(String dealNumber) {
		log.info("template item en client hystrix selectBuDealNumber ");
		return null;
	}

	@Override
	public Object selectBuEnterpriseId(Long id) {
		log.info("template item en client hystrix selectBuEnterpriseId ");
		return null;
	}

	
}
