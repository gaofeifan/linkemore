package cn.linkmore.common.controller.feign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.entity.City;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqCity;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.common.service.CityService;
import cn.linkmore.util.JsonUtil; 

/**
 * Controller - 城市信息
 * @author liwenlongq
 * @version 2.0
 *
 */ 
@RestController
@RequestMapping("/citys")
public class FeignCityController{
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CityService cityService;
	  
	@RequestMapping(value="{id}",method=RequestMethod.GET)
	@ResponseBody 
	public ResCity getById(@PathVariable("id") Long id) {
		return this.cityService.find(id);
	}
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody 
	public List<ResCity> list(@RequestParam("start") Integer start, @RequestParam("size") Integer size) { 
		log.info("common citys list");
		return this.cityService.findList(start,size);
	} 
	 
	@RequestMapping(method=RequestMethod.POST)
	public void save(@RequestBody ReqCity reqCity) {
		City city = new City();
		city.setName(reqCity.getName());
		city.setAdcode(reqCity.getCode());
		this.cityService.save(city);
	}
	 
	@RequestMapping(method=RequestMethod.PUT)
	public void update(@RequestBody ReqCity reqCity) {
		log.info("update city :{}",JsonUtil.toJson(reqCity)); 
		City city = new City();
		city.setName(reqCity.getName());
		city.setAdcode(reqCity.getCode());
		city.setId(reqCity.getId());
		this.cityService.update(city);
	}
	 
	@RequestMapping(value="{id}",method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		log.info("delete city  with id:{}",id); 
		this.cityService.delete(id);
	}
	
	/**
	 * 根据行政编号查询对应的城市信息
	 * @param code 行政编号
	 * @return
	 */
	@RequestMapping(value="/code",method=RequestMethod.GET)
	@ResponseBody 
	public ResCity getByCode(@RequestParam("code") String code) {
		return this.cityService.find(code);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public ViewMsg deleteIds(@RequestBody List<Long> ids){ 
		ViewMsg msg = null;
		try {
			this.cityService.deleteIds(ids);
			msg = new ViewMsg("删除成功",true);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck check) {
		Boolean flag = true ;
		Integer count = this.cityService.check(check.getProperty(), check.getValue(), check.getId()); 
		if(count>0){
            flag = false;
        }
        return flag;
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(@RequestBody ViewPageable pageable){
		return this.cityService.findPage(pageable);
	} 
	
	@RequestMapping(value = "/select_list", method = RequestMethod.POST)
	@ResponseBody
	public List<ResCity> findSelectList(){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", "city_name");
		param.put("direction", "asc");
		List<ResCity> list = this.cityService.findList(param);
		return list;
	} 
}
