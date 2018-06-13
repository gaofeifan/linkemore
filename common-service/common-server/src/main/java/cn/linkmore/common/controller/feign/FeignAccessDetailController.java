package cn.linkmore.common.controller.feign;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.common.request.ReqAccessDetail;
import cn.linkmore.common.service.AccessDetailService;

/**
 * @Description  日志记录
 * @author  GFF
 * @Date     2018年5月15日
 */
@RestController
@RequestMapping("/feign/access_detail")
public class FeignAccessDetailController {
	
	@Resource
	private AccessDetailService accessDetailService;
	
	/**
	 * @Description  app接口访问详情日志
	 * @Author   GFF 
	 * @Date       2018年5月15日
	 */
	@RequestMapping(value="/v2.0/app_save",method=RequestMethod.POST)
	public void appSave(@RequestBody ReqAccessDetail accessDetail) {
		this.accessDetailService.appSave(accessDetail);
	}
	
	/**
	 * @Description  小程序接口访问详情日志
	 * @Author   GFF 
	 * @Date     2018年5月15日
	 */
	@RequestMapping(value="/v2.0/mini_save",method=RequestMethod.POST)
	public void miniSave(@RequestBody ReqAccessDetail accessDetail) {
		this.accessDetailService.miniSave(accessDetail);
	}

}
