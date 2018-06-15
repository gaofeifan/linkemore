package cn.linkmore.coupon.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.PreSubjectClient;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqPreSubject;
import cn.linkmore.coupon.response.ResBizSubjectBean;

@Component
public class PreSubjectClientHystrix implements PreSubjectClient {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqPreSubject record) {
		log.info("coupon service pre_subject save() hystrix");
		return 0;
	}

	@Override
	public int update(ReqPreSubject record) {
		log.info("coupon service pre_subject update() hystrix");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("coupon service pre_subject delete() hystrix");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("coupon service pre_subject check() hystrix");
		return null;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("coupon service pre_subject list() hystrix");
		return null;
	}

	@Override
	public List<ResBizSubjectBean> subjectList(Long type) {
		log.info("coupon service pre_subject subjectList() hystrix");
		return null;
	}
	
}
