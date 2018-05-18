package cn.linkmore.third.client.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.third.client.LocateClient;
import cn.linkmore.third.response.ResLocate;

@Component
public class LocateClientHystrix implements LocateClient {

	@Override
	public ResLocate get(@RequestParam(value="longitude",required=true)String longitude,@RequestParam(value="latitude",required=true)String latitude) { 
		return null;
	}

}
