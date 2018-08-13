package cn.linkmore.prefecture.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.prefecture.client.EntBrandUserClient;
/**
 * 远程调用实现 - 企业品牌用户
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class EntBrandUserClientHystrix implements EntBrandUserClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Boolean checkExist(Long userId, String plateNo) {
		return null;
	}
}

