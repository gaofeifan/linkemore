package cn.linkmore.common.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.common.entity.City;
import cn.linkmore.common.request.ReqCity;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.common.service.CityService;
import cn.linkmore.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 城市信息
 * @author liwenlong
 * @version 2.0
 *
 */
 
@Api(tags = "CityController",description="城市接口")
@RestController
@RequestMapping("/common/citys")
public class CityController {
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CityService cityService;
	 
	@ApiOperation(value="根据ID获取 信息",notes="主键ID是必输项，并且必须是数字")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "Long", name = "id", value = "信息id", required = true) })
	@RequestMapping(value="{id}",method=RequestMethod.GET)
	public ResCity getById(@PathVariable("id") Long id) {
		return this.cityService.find(id);
	}
	
	@ApiOperation("查询城市列表")
	@RequestMapping(method=RequestMethod.GET)
	public List<ResCity> list(@RequestParam("start") Integer start, @RequestParam("size") Integer size) { 
		return this.cityService.findList(start,size);
	}
	
	@ApiOperation("保存城市信息")
	@RequestMapping(method=RequestMethod.POST)
	public void save(@RequestBody ReqCity reqCity) {
		City city = new City();
		city.setCityName(reqCity.getName());
		city.setAdcode(reqCity.getCode());
		this.cityService.save(city);
	}
	
	@ApiOperation("更新城市信息")
	@RequestMapping(method=RequestMethod.PUT)
	public void update(@RequestBody ReqCity reqCity) {
		log.info("update city :{}",JsonUtil.toJson(reqCity)); 
		City city = new City();
		city.setCityName(reqCity.getName());
		city.setAdcode(reqCity.getCode());
		city.setId(reqCity.getId());
		this.cityService.save(city);
	}
	
	@ApiOperation("删除城市信息")
	@RequestMapping(value="{id}",method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		log.info("delete city  with id:{}",id); 
		this.cityService.delete(id);
	}
}
