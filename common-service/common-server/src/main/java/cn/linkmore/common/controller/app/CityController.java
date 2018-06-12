package cn.linkmore.common.controller.app;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.common.controller.app.response.ResCity;
import cn.linkmore.common.service.CityService;
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
@Validated
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	@ApiOperation(value = "列表", notes = "城市列表[<br/>latitude经度<br/>longitude纬度]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ResCity>> list(
			@Range(min=73, max=137,message="经度请确保在中国范围内")
			@NotBlank(message="经度不能为空") 
			@RequestParam(value="longitude",required=true)String longitude,
			@Range(min=3, max=54,message="纬度请确保在中国范围内")
			@NotBlank(message="纬度不能为空")
			@RequestParam(value="latitude",required=true)String latitude, 
			HttpServletRequest request) {
		List<ResCity> list = this.cityService.list(longitude,latitude);
		return ResponseEntity.success(list, request);
	} 
}
