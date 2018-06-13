package cn.linkmore.common.controller.feign;
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
import cn.linkmore.common.request.ReqAppVersion;
import cn.linkmore.common.request.ReqCheck;
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
@RequestMapping("/feign/version")
public class FeignBaseVersionController {
	
	@Resource
	private BeanVersionService beanVersionService;
	
	/**
	 * @Description  当前版本 
	 * @Author   GFF 
	 * @Version  v2.0
	 * @param  source 请求来源 1 Android 2 IOS
	 */
	@RequestMapping(value="/current/{source}",method = RequestMethod.GET)
	@ResponseBody
	public ResVersionBean current(@PathVariable("source")Integer source){
		int appType = 0;
		if(Constants.ClientSource.ANDROID.source == source){
			appType = 1;
		}else if(2 == source){
			appType = 2;
		}
		ResVersionBean app = this.beanVersionService.currentAppVersion(appType);
		return app;
	}
	
	/**
	 * @Description  上报用户版本
	 * @Author  	 GFF 
	 * @Version  	 v2.0
	 */
	@RequestMapping(value="/report",method = RequestMethod.POST)
	public void report(@RequestBody ReqVersion vrb){
		this.beanVersionService.report(vrb);
	}
	
	/**
	 * @Description  添加app版本
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/app",method = RequestMethod.POST)
	public void saveApp(@RequestBody ReqAppVersion version) {
		this.beanVersionService.saveApp(version);
	}
	
	/**
	 * @Description  更新app版本
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/update",method = RequestMethod.PUT)
	public void updateApp(@RequestBody ReqAppVersion version) {
		this.beanVersionService.updateApp(version);
	}
	
	/**
	 * @Description  删除app版本
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteAppById(@RequestBody List<Long> ids) {
		this.beanVersionService.deleteAppById(ids);
	}
	
	/**
	 * @Description  查询分页
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/page",method = RequestMethod.POST)
	public ViewPage findPage(@RequestBody ViewPageable pageable) {
		return this.beanVersionService.findPage(pageable);
	}
	
	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/check",method = RequestMethod.POST)
	public Boolean check(@RequestBody ReqCheck check) {
		Boolean flag = true ;
		Integer count = this.beanVersionService.check(check); 
		if(count>0){
            flag = false;
        }
        return flag;
	}
	
	/**
	 * @Description  查询用户版本分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/user_page",method = RequestMethod.POST)
	public ViewPage findUserPage(@RequestBody ViewPageable pageable) {
		return this.beanVersionService.findUserPage(pageable);
	}
}
