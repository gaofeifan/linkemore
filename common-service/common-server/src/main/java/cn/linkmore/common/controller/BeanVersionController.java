package cn.linkmore.common.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.security.Token;
import cn.linkmore.common.response.ResVersionBean;
import cn.linkmore.common.service.BeanVersionService;

@RestController
@RequestMapping("version")
public class BeanVersionController {
	
	@Resource
	private BeanVersionService beanVersionService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResVersionBean current(HttpServletRequest request){
		Integer appType = 0;
		String os = request.getHeader("os");
		if(Token.OS_ANDROID.equals(os)){
			appType=1;
		}else if(Token.OS_IOS.equals(os)){
			appType = 2;
		}
		ResVersionBean app = this.beanVersionService.currentAppVersion(appType);
		return app;
	}
	
	
	/*@RequestMapping(method = RequestMethod.GET)
	public void report(HttpServletRequest request,@RequestBody ReqVersion vrb){
		this.beanVersionService.report(vrb);
	}*/
}
