package cn.linkmore.prefecture.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.enterprise.response.ResEntStaff;
import cn.linkmore.prefecture.client.EntStaffClient;
/**
 * @author   GFF
 * @Date     2018年9月4日
 * @Version  v2.0
 */
@Component
public class EntStaffClientHystrix implements EntStaffClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public ResEntStaff findById(Long id) {
		log.info("ResEntStaff service findById(Long id) hystrix");
		return null;
	}

}
