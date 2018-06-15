package cn.linkmore.coupon.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.SubjectClient;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqSubject;
import cn.linkmore.coupon.response.ResSubject;
import cn.linkmore.coupon.response.ResTemplate;

@Component
public class SubjectClientHystrix implements SubjectClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public int save(ReqSubject record) {
		log.info("coupon service subject save() hystrix");
		return 0;
	}

	@Override
	public ResSubject detail(Long id) {
		log.info("coupon service subject detail() hystrix");
		return null;
	}

	@Override
	public int update(ReqSubject record) {
		log.info("coupon service subject update() hystrix");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("coupon service subject delete() hystrix");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("coupon service subject check() hystrix");
		return null;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("coupon service subject list() hystrix");
		return null;
	}

	@Override
	public int start(Long id) {
		log.info("coupon service subject start() hystrix");
		return 0;
	}

	@Override
	public int down(Long id) {
		log.info("coupon service subject down() hystrix");
		return 0;
	}

	@Override
	public List<ResTemplate> subjectList() {
		log.info("coupon service subject subjectList() hystrix");
		return null;
	}
	
}
