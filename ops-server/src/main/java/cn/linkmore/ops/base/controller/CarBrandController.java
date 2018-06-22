package cn.linkmore.ops.base.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.ops.base.service.CarBrandService;

/**
 * 车辆品牌数据获取
 * https://market.aliyun.com/products/57002002/cmapi011811.html
 * 阿里云市场 数据接口地址
 * @author  GFF
 * @Date     2018年3月15日
 *
 */
@RestController
@RequestMapping(value = "/carBrand")
public class CarBrandController {
	
	@Resource
	private CarBrandService carBrandService;
	
    private  Logger log = LoggerFactory.getLogger(getClass());
    

	/**
     *  获取所有车辆一级二级三级品牌
     *  大众
     *  	一汽大众
     *  		速腾
     *  		迈腾
     *  	上汽大众
     *  		凌度
     *  		帕萨特
     *  
     *  发送HTTP请求，为保证数据正常，休眠了3秒， 完成请求需要大概10分钟
	 * @Author   GFF 
	 * @Date       2018年3月15日
	 * @Param    CarBrandController
	 * @Return   Map<String,Object>
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Map<String,Object> list(){
		return this.carBrandService.load();
	}
	
}

