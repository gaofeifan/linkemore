package cn.linkmore.common.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.security.Token;
import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.response.ResVersionBean;
import cn.linkmore.common.service.BeanVersionService;

/**
 * 版本管理
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
@RestController
@RequestMapping("/version")
public class BaseVersionController {
	
	@Resource
	private BeanVersionService beanVersionService;
	
	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/current/{requestSource}",method = RequestMethod.GET)
	@ResponseBody
	public ResVersionBean current(@PathVariable("requestSource")Short requestSource){
		int appType = 0;
		if(Token.OS_ANDROID == requestSource){
			appType = 1;
		}else if(Token.OS_IOS == requestSource){
			appType = 2;
		}
		ResVersionBean app = this.beanVersionService.currentAppVersion(appType);
		return app;
	}
	
	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/report",method = RequestMethod.GET)
	public void report(@RequestBody ReqVersion vrb){
		this.beanVersionService.report(vrb);
	}
}
