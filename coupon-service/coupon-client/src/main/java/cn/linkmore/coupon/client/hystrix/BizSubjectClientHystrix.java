package cn.linkmore.coupon.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.BizSubjectClient;
import cn.linkmore.coupon.request.ReqBizSubject;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.response.ResBizSubjectBean;
import cn.linkmore.coupon.response.ResCombo;
import cn.linkmore.coupon.response.ResValuePack;

@Component
public class BizSubjectClientHystrix implements BizSubjectClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());	
	
	@Override
	public int save(ReqBizSubject record) {
		log.info("coupon service biz_subject save() hystrix");
		return 0;
	}

	@Override
	public ResBizSubjectBean detail(Long id) {
		log.info("coupon service biz_subject detail() hystrix");
		return null;
	}

	@Override
	public int update(ReqBizSubject record) {
		log.info("coupon service biz_subject update() hystrix");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("coupon service biz_subject delete() hystrix");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("coupon service biz_subject check() hystrix");
		return false;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("coupon service biz_subject list() hystrix");
		return null;
	}

	@Override
	public int start(Long id) {
		log.info("coupon service biz_subject start() hystrix");
		return 0;
	}

	@Override
	public int down(Long id) {
		log.info("coupon service biz_subject down() hystrix");
		return 0;
	}

	@Override
	public List<ResCombo> comboList(Long comboType) {
		log.info("coupon service biz_subject comboList() hystrix");
		return null;
	}

	@Override
	public List<ResValuePack> packList(Long comboType) {
		log.info("coupon service biz_subject packList() hystrix");
		return null;
	}
	
}
