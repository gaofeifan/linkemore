package cn.linkmore.third.client.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.third.client.DockingClient;
import cn.linkmore.third.request.ReqOrder;

@Component
public class DockingClientHystrix implements DockingClient {

	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public void order(@RequestBody ReqOrder ro) {
		log.info("docking client order failure hystrix");
	}

}
