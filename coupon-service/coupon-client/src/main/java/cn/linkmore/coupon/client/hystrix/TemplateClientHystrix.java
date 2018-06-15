package cn.linkmore.coupon.client.hystrix;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.TemplateClient;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplate;
import cn.linkmore.coupon.request.ReqTemplateCondition;
import cn.linkmore.coupon.response.ResTemplate;

@Component
public class TemplateClientHystrix implements TemplateClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public int save(ReqTemplate record) {
		log.info("coupon service temp save() hystrix");
		return 0;
	}

	@Override
	public ResTemplate detail(Long id) {
		log.info("coupon service temp detail() hystrix");
		return null;
	}

	@Override
	public int update(ReqTemplate record) {
		log.info("coupon service temp update() hystrix");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("coupon service temp delete() hystrix");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("coupon service temp save() hystrix");
		return null;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("coupon service temp list() hystrix");
		return null;
	}

	@Override
	public int start(Long id) {
		log.info("coupon service temp start() hystrix");
		return 0;
	}

	@Override
	public int down(Long id) {
		log.info("coupon service temp down() hystrix");
		return 0;
	}

	@Override
	public void download(Long id, HttpServletResponse response) {
		log.info("coupon service temp download() hystrix");
	}
	
}
