package cn.linkmore.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.user.response.ResCity;
import cn.linkmore.user.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
/**
 * Controller - 城市
 * @author liwenlong
 * @version 2.0
 *
 */
@Api(tags="City",description="城市")
@RestController
@RequestMapping("/citys")
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	@ApiOperation(value = "列表", notes = "城市列表[<br/>经度<br/>longitude纬度]", consumes = "application/json")
	@RequestMapping(value = "/v2.0", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ResCity>> list(@RequestParam(value="longitude",required=true)String longitude,@RequestParam(value="latitude",required=true)String latitude, HttpServletRequest request) {
		List<ResCity> list = this.cityService.list(longitude,latitude);
		return ResponseEntity.success(list, request);
	} 
}
