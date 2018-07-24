package cn.linkmore.prefecture.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.prefecture.client.FeignEnterpriseClient;
/**
 * 远程调用实现 - 锁操作日志信息
 * @author jiaohanbin
 * @version 2.0
 *
 */ 
@Component
public class FeignEnterpriseClientHystrix implements FeignEnterpriseClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResEnterprise findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}

