package cn.linkmore.mini.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.common.client.CityClient;
import cn.linkmore.common.response.ResCity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * Controller - 城市信息
 * @author liwenlong
 * @version 2.0
 */

@Api(value = "城市信息相关接口",tags="城市信息相关接口")
@RestController
@RequestMapping("/common/citys")
public class CityController {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CityClient cityClient;
	
	@ApiOperation(value="根据ID获取城市信息", notes="获取城市信息")
	@RequestMapping(method=RequestMethod.GET)
	public List<ResCity> list() { 
		log.info("get city list ");
		return this.cityClient.list(0, 12);
	}
	
	@ApiOperation(value="根据ID获取城市信息", notes="获取城市信息")
	@RequestMapping(value="{id}",method=RequestMethod.GET)
	public ResCity getById(@PathVariable("id") Long id) { 
		log.info("get city by id:{}",id);
		return this.cityClient.find(id);
	}
	
	@ApiOperation(value="根据ID获取城市信息", notes="获取城市信息")
	@RequestMapping(value="{code}",method=RequestMethod.GET)
	public ResCity getByCode(@PathVariable("code") String code) { 
		log.info("get city by code:{}",code);
		return null;
	}
	
}
