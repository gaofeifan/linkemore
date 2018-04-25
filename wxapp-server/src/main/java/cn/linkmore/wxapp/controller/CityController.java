package cn.linkmore.wxapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.common.client.CityClient;
import cn.linkmore.common.response.ResCity;

@RestController
@RequestMapping("/common/citys")
public class CityController {
	@Autowired
	private CityClient cityClient;
	
	@RequestMapping(value="{id}",method=RequestMethod.GET)
	public ResCity get(@PathVariable("id") Long id) { 
		ResCity res = cityClient.find(12L);
		return res;
	}
	@RequestMapping(value="list",method=RequestMethod.GET)
	public List<ResCity> list(@RequestParam("start") int start, @RequestParam("size") int size) {
		List<ResCity> res = this.cityClient.list(start, size);
		return res;
	}
	
}
