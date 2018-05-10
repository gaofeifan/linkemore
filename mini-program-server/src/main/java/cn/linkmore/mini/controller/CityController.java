package cn.linkmore.mini.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * Controller - 城市信息
 * @author liwenlong
 * @version 2.0
 */

@Api(tags = "CityController",description="城市接口")
@RestController
@RequestMapping("/common/citys")
public class CityController {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
//	@Autowired
//	private CityClient cityClient;
	@ApiOperation("根据ID获取城市信息")
	@RequestMapping(method=RequestMethod.GET)
	public String list() { 
//		log.info("get city list ");
//		return this.cityClient.list(new Integer(1), new Integer(12));
		return null;
	}
	
//	@ApiOperation("根据ID获取城市信息")
//	@RequestMapping(method=RequestMethod.GET)
//	public List<ResCity> list() { 
////		log.info("get city list ");
////		return this.cityClient.list(new Integer(1), new Integer(12));
//	}
//	
//	@ApiOperation("根据ID获取城市信息")
//	@RequestMapping(value="{id}",method=RequestMethod.GET)
//	public ResCity getById(@PathVariable("id") Long id) { 
//		log.info("get city by id:{}",id);
//		return this.cityClient.find(id);
//	}
//	
//	@ApiOperation("根据ID获取城市信息")
//	@RequestMapping(value="{code}",method=RequestMethod.GET)
//	public ResCity getByCode(@PathVariable("code") String code) { 
//		log.info("get city by code:{}",code);
//		return null;
//	}
	
}
