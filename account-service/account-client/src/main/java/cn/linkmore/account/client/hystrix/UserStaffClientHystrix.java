package cn.linkmore.account.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import cn.linkmore.account.client.UserStaffClient;
import cn.linkmore.account.request.ReqCheck;
import cn.linkmore.account.request.ReqUserStaff;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * @author   GFF
 * @Date     2018年5月22日
 * @Version  v2.0
 */
@Component
public class UserStaffClientHystrix  implements UserStaffClient{
	
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResUserStaff findById(@PathVariable("id")Long id) {
		log.info("account service ResUserStaff findById(Long id) hystrix");
		return null;
	}

	@Override
	public void save(ReqUserStaff record) {
		log.info("account service void save(ReqUserStaff record) hystrix");
	}

	@Override
	public void update(ReqUserStaff record) {
		log.info("account service void update(ReqUserStaff record) hystrix");
	}

	@Override
	public Boolean check(ReqCheck check) {
		log.info("account service Boolean check(ReqCheck check) hystrix");
		return null;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		log.info("account service ViewPage list(ViewPageable pageable) hystrix");
		return null;
	}

	@Override
	public ResUserStaff findByMobile(String mobile) {
		log.info("account service ResUserStaff findByMobile(String mobile) hystrix");
		return null;
	}


	
	
}

