package cn.linkmore.third.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.third.response.ResLocate;
import cn.linkmore.third.service.LocateService;

@RestController
@RequestMapping("/feign/locate")
public class LocateController {
	
	@Autowired
	private LocateService locateService;
	
	@RequestMapping(value = "/v2.0", method=RequestMethod.GET)
	@ResponseBody
	public ResLocate get(@RequestParam(value="longitude",required=true)String longitude,@RequestParam(value="latitude",required=true)String latitude) {
		return this.locateService.getInfo(longitude,latitude);
	}
	
	@RequestMapping(value = "/v2.0/distance", method=RequestMethod.GET)
	@ResponseBody
	public String distance(String oriLng,String oriLat,String desLng,String desLat) {
		return this.locateService.distance( oriLng, oriLat, desLng, desLat);
	}
}
