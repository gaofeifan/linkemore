package cn.linkmore.coupon.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.client.ThemeClient;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTheme;
import cn.linkmore.coupon.response.ResEnterpriseBean;

@Component
public class ThemeClientHystrix implements ThemeClient {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public int save(ReqTheme record) {
		log.info("coupon service theme save() hystrix");
		return 0;
	}

	@Override
	public int update(ReqTheme record) {
		log.info("coupon service theme update() hystrix");
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		log.info("coupon service theme delete() hystrix");
		return 0;
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		log.info("coupon service theme check() hystrix");
		return null;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("coupon service theme list() hystrix");
		return null;
	}

	@Override
	public List<ResEnterpriseBean> enterpriseList() {
		log.info("coupon service theme enterpriseList() hystrix");
		return null;
	}
	
}
