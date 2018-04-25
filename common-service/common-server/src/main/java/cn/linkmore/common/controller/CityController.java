package cn.linkmore.common.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.common.request.ReqCity;
import cn.linkmore.common.response.ResCity;

@RestController
@RequestMapping("/common/citys")
public class CityController {
	
	@RequestMapping(value="{id}",method=RequestMethod.GET)
	public ResCity get(@PathVariable("id") Long id) {
		ResCity rc = new ResCity();
		rc.setId(12L);
		rc.setName("北京");
		rc.setCode("00100");
		rc.setCreateTime(new Date());
		return rc;
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public List<ResCity> list(@RequestParam("start") int start, @RequestParam("size") int size) {
		List<ResCity> rcs = new ArrayList<ResCity>();
		ResCity rc = new ResCity();
		rc.setId(12L);
		rc.setName("北京");
		rc.setCode("00100");
		rc.setCreateTime(new Date());
		rcs.add(rc);
		rc = new ResCity();
		rc.setId(13L);
		rc.setName("杭州");
		rc.setCode("10100");
		rc.setCreateTime(new Date());
		rcs.add(rc);
		rc = new ResCity();
		rc.setId(14L);
		rc.setName("广州");
		rc.setCode("20100");
		rc.setCreateTime(new Date());
		rcs.add(rc);
		return rcs;
	}
	 
	@RequestMapping(method=RequestMethod.POST)
	public void save(@RequestBody ReqCity reqCity) {
		 
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public void update(@RequestBody ReqCity reqCity) {
		 
	}
}
