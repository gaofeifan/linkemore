package cn.linkmore.third.client.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import cn.linkmore.third.client.LocateClient;
import cn.linkmore.third.request.ReqLocate;
import cn.linkmore.third.response.ResLocate;

@Component
public class LocateClientHystrix implements LocateClient {

	@Override
	public ResLocate get(@RequestBody ReqLocate req) { 
		return null;
	}

}
