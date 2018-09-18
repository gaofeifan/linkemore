package cn.linkmore.prefecture.client.hystrix;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.prefecture.client.StaffAdminUserPackClient;

/**
 * @author   GFF
 * @Date     2018年9月17日
 * @Version  v2.0
 */
@Component
public class AdminUserPackClientHystrix implements StaffAdminUserPackClient {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public HashMap<String, Object> findByAdminId(Long adminid) {
		log.info("=============Hystrix==========HashMap<String, Object> findByAdminId(Long adminid)");
		return null;
	}
	
	
}
