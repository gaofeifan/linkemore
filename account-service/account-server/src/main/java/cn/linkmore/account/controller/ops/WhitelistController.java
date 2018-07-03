package cn.linkmore.account.controller.ops;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.linkmore.account.request.ReqCheck;
import cn.linkmore.account.request.ReqWhitelist;
import cn.linkmore.account.service.WhitelistService;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
/**
 * Controller - 权限模块 - 类记录
 * @author   GFF
 * @Date     2018年6月22日
 * @Version  v2.0
 */
@Controller
@RequestMapping("/whitelist")
public class WhitelistController {
	 
	@Autowired
	private WhitelistService whitelistService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void save(@RequestBody ReqWhitelist record){
		this.whitelistService.save(record);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public void update(@RequestBody ReqWhitelist record){
		this.whitelistService.update(record);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@RequestBody List<Long> ids){ 
		this.whitelistService.delete(ids);
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(@RequestBody ReqCheck check){
		Boolean flag = true ;
		Integer count = this.whitelistService.check(check.getProperty(), check.getValue(), check.getId()); 
		if(count>0){
            flag = false;
        }
        return flag;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list( @RequestBody ViewPageable pageable){
		return this.whitelistService.findPage(pageable); 
	}  
}
