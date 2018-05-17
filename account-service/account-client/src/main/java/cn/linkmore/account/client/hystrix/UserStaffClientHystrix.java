package cn.linkmore.account.client.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import cn.linkmore.account.client.UserStaffClient;
import cn.linkmore.account.response.ResUserStaff;

@Component
public class UserStaffClientHystrix  implements UserStaffClient{
	
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<ResUserStaff> list(@PathVariable("id") Long id) {
		return null;
	}

	
	
}

