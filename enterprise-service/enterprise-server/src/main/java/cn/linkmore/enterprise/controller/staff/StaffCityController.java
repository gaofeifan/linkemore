package cn.linkmore.enterprise.controller.staff;

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
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqCity;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.enterprise.service.CityService;
import cn.linkmore.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation; 

/**
 * Controller - 城市信息
 * @author liwenlongq
 * @version 2.0
 *
 */ 
@RestController
@Api(value="Staff-city",description="【管理版】城市信息")
@RequestMapping("/staff/citys")
public class StaffCityController{
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CityService cityService;
	  
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody 
	@ApiOperation(value="/citys",notes="查询城市列表", consumes = "application/json")
	public List<cn.linkmore.enterprise.controller.staff.response.ResCity> findStaffCity(HttpServletRequest request) {
		return this.cityService.findStaffCity(request);
	}

}
