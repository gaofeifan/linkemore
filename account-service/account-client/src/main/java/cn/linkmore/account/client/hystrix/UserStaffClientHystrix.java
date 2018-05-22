package cn.linkmore.account.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import cn.linkmore.account.client.UserStaffClient;
import cn.linkmore.account.response.ResUserStaff;

/**
 * @author   GFF
 * @Date     2018年5月22日
 * @Version  v2.0
 */
@Component
public class UserStaffClientHystrix  implements UserStaffClient{
	
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResUserStaff selectById(@PathVariable("id")Long id) {
		log.info("account service userstaff selectById(Long id) hystrix");
		return null;
	}


	
	
}

