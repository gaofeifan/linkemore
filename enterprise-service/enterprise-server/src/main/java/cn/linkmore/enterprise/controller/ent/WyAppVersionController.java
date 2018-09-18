package cn.linkmore.enterprise.controller.ent;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.Constants;
import cn.linkmore.common.response.ResWyAppVersion;
import cn.linkmore.enterprise.service.WyVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(tags="version",description="【物业版】版本管理")
@RequestMapping(value="/ent/version")
public class WyAppVersionController {

	@Resource
	private WyVersionService wyVersionService;
	/**
	 * @Description  当前版本 
	 * @Author   GFF 
	 * @Version  v2.0
	 * @param  source 请求来源 1 Android 2 IOS
	 */
	@RequestMapping(value="/current",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询当前版本", notes = "来源必填 1 android 2 ios", consumes = "application/json")
	public ResWyAppVersion current(@ApiParam(value="来源" ,required=true) @NotNull(message="来源不能为空") @RequestParam("source")Integer source){
		int appType = 0;
		if(Constants.ClientSource.ANDROID.source == source){
			appType = 1;
		}else if(2 == source){
			appType = 2;
		}
		ResWyAppVersion app = this.wyVersionService.currentAppVersion(appType,null);
		return app;
	}
	
	/**
	 * @Description  上报用户版本
	 * @Author  	 GFF 
	 * @Version  	 v2.0
	 */
	/*@RequestMapping(value="/report",method = RequestMethod.POST)
	public void report(@RequestBody ReqVersion vrb){
		this.beanVersionService.report(vrb);
	}*/
	
}
