package cn.linkmore.account.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.service.CarBrandService;


/**
 * @Version 2.0
 * @author  GFF
 * @Date     2018年5月11日
 */
@RestController
@RequestMapping("car_brand")
public class CarBrandController {

	@Resource
	private CarBrandService carBrandService;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Object list(HttpServletRequest request) {
		Object obj = carBrandService.findList();
		return obj;
	}
	
/*	@RequestMapping(value = "/load", method = RequestMethod.POST)
	public Map<String,Object> load(){
		this.carBrandService.load();
		return msg;
	}
	*/
	
}
