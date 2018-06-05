package cn.linkmore.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.user.service.CarBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



/**
 * 车辆品牌数据
 * @Version 2.0
 * @author  GFF
 * @Date     2018年5月11日
 */
@Api(tags="Vehicle brands",description="车辆品牌")
@RestController
@RequestMapping("/vehicle-brands")
public class CarBrandController {

	@Resource
	private CarBrandService carBrandService;
	
	/**
	 * 查询车牌品牌数据list
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="查询车牌品牌数据list",notes="查询list", consumes = "application/json")
	@RequestMapping(value="/v2.0/list", method = RequestMethod.GET)
	public ResponseEntity<?> list(HttpServletRequest request) {
		Object object = carBrandService.list();
		ResponseEntity<Object> responseEntity = ResponseEntity.success(object, request);
		return responseEntity;
	}
	
}
