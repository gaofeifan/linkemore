package cn.linkmore.common.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.linkmore.common.client.CarBrandClient;

/**
 * @author   GFF
 * @Date     2018年5月21日
 * @Version  v2.0
 */
@Component
public class CarBrandClientHystrix implements CarBrandClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public Object list() {
		return null;
	}

	@Override
	public void load() {
		
	}

	
	
	
}
