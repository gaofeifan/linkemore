package cn.linkmore.common.client.hystrix;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.common.client.CityClient;
import cn.linkmore.common.response.ResCity;

@Component
public class CityClientHystrix implements CityClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public ResCity find(@PathVariable("id") Long id) { 
		log.info("common service citys find 404:{}",new Date().getTime());
		return new ResCity();
	}

	@Override
	public List<ResCity> list(@RequestParam("start") int start, @RequestParam("size") int size) { 
		log.info("common service citys list 404:{}",new Date().getTime());
		return new ArrayList<ResCity>();
	}

}
