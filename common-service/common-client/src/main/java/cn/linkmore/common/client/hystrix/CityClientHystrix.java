package cn.linkmore.common.client.hystrix;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.CityClient;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqCity;
import cn.linkmore.common.response.ResCity;

@Component
public class CityClientHystrix implements CityClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public ResCity getById(@PathVariable("id") Long id) { 
		log.info("common service citys find(Long id) hystrix");
		return new ResCity();
	}

	@Override
	public List<ResCity> list(@RequestParam("start") Integer start, @RequestParam("size") Integer size) { 
		log.info("common service citys find list(int start,int size) hystrix");
		return new ArrayList<ResCity>();
	} 
	 
	  
	@Override
	public void save(@RequestBody ReqCity reqCity) {
		log.info("common service citys save( ReqCity reqCity) hystrix");
	}
	 
	@Override
	public void update(@RequestBody ReqCity reqCity) {
		log.info("common service citys update( ReqCity reqCity) hystrix");
	}; 
	
	@Override
	public void delete(@PathVariable("id") Long id) {
		log.info("common service citys delete(Long id) hystrix");
	}

	@Override
	public ResCity getByCode(String code) {
		log.info("common service citys getByCode(Long code) hystrix");
		return new ResCity();
	}

	@Override
	public void deleteIds(@RequestBody List<Long> ids) {
		log.info("common service citys deleteIds(List<Long> ids) hystrix");
	}

	@Override
	public Boolean check(@RequestBody ReqCheck check) {
		log.info("common service citys check(ReqCheck check) hystrix");
		return null;
	}

	@Override
	public ViewPage findPage(@RequestBody ViewPageable pageable) {
		log.info("common service citys findPage(ViewPageable pageable) hystrix");
		return null;
	};
	
	

}
