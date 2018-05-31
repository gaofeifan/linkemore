package cn.linkmore.ops.security.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.exception.DataException;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.request.ReqPageElement;
import cn.linkmore.ops.security.service.PageElementService;
import io.swagger.annotations.Api;

/**
 * Controller - 页面元素
 * 
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Api(tags = "PageElement", description = "页面元素")
@RestController
@RequestMapping("/admin/security/element")
public class PageElementController {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PageElementService elementService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg save(ReqPageElement record){
		ViewMsg msg = null;
		try { 
			this.elementService.save(record);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
		 
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg update(ReqPageElement record){
		ViewMsg msg = null;
		try {
			this.elementService.update(record);
			msg = new ViewMsg("保存成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("保存失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ViewMsg delete(@RequestBody List<Long> ids){ 
		ViewMsg msg = null;
		try {
			this.elementService.delete(ids);
			msg = new ViewMsg("删除成功",true);
		}catch(DataException e) {
			msg = new ViewMsg(e.getMessage(),false);
		}catch(Exception e) {
			e.printStackTrace();
			msg = new ViewMsg("删除失败",false);
		}
		return msg;
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public Boolean check(ReqCheck reqCheck){
		Boolean flag = this.elementService.check(reqCheck); 
        return flag;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public ViewPage list(HttpServletRequest request,ViewPageable pageable){ 
		return this.elementService.findPage(pageable); 
	} 
	
	
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public Tree tree(HttpServletRequest request){ 
		return this.elementService.findTree();
	}
	
	@RequestMapping(value = "/map", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> map(HttpServletRequest reqeust){
		return this.elementService.map();
	}
}
