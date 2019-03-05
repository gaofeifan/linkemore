package cn.linkmore.prefecture.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.enterprise.response.ResFixedPlate;
import cn.linkmore.prefecture.client.FixedPlateClient;

@Component
public class FixedPlateClientHystrix implements FixedPlateClient{
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResFixedPlate findPlateNosByStallId(Long stallId) {
		log.info("List<String> findPlateNosByStallId stallId={}",stallId);
		return null;
	}
}
