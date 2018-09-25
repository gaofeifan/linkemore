package cn.linkmore.common.controller.ent;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.controller.app.request.ReqVersion;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqWyAppVersion;
import cn.linkmore.common.response.ResVersionBean;
import cn.linkmore.common.response.ResWyAppVersion;
import cn.linkmore.common.service.WyVersionService;
import io.swagger.annotations.Api;

@RestController
@Api
@RequestMapping(value="/feign/ent/version")
public class WyAppVersionController {

	@Resource
	private WyVersionService wyVersionService;
	/**
	 * @Description  当前版本 
	 * @Author   GFF 
	 * @Version  v2.0
	 * @param  source 请求来源 1 Android 2 IOS
	 */
	@RequestMapping(value="/current/{source}",method = RequestMethod.GET)
	@ResponseBody
	public ResWyAppVersion current(@PathVariable("source")Integer source){
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
	@RequestMapping(value="/report",method = RequestMethod.POST)
	public void report(@RequestBody ReqVersion vrb){
		this.wyVersionService.report(vrb,null);
	}
	
	@RequestMapping(value="/list-page",method = RequestMethod.POST)
	@ResponseBody
	public ViewPage findPage(@RequestBody ViewPageable pageable) {
		return this.wyVersionService.findPage(pageable);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqWyAppVersion copyObject) {
		this.wyVersionService.saveApp(copyObject);
	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqWyAppVersion copyObject) {
		this.wyVersionService.updateApp(copyObject);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids) {
		this.wyVersionService.deleteAppById(ids);
	}
	
	@RequestMapping(value="/check",method = RequestMethod.PUT)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck check) {
		Integer integer = this.wyVersionService.check(check);
		if(integer > 0) {
			return false;
		}
		return true;
	}
	
}
